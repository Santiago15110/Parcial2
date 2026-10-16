package org.example.parcial2.viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.example.parcial2.HelloApplication;
import org.example.parcial2.controller.TechSolutionsController;
import org.example.parcial2.model.*;
import org.example.parcial2.util.IAppControlable;
import org.example.parcial2.util.SceneManager;

import java.time.format.DateTimeFormatter;

public class viewControllerSolicitudes implements IAppControlable {

    // Datos de la solicitud
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private TextField txtCodigoSolicitud;
    @FXML private ComboBox<String> cmbPrioridad;
    @FXML private DatePicker dpFecha;
    @FXML private TextArea txtObservaciones;

    // Elementos contratados
    @FXML private ComboBox<ElementoServicio> cmbElemento;
    @FXML private TableView<ElementoServicio> tblElementos;
    @FXML private TableColumn<ElementoServicio, String> colElemento, colCostoElemento;
    @FXML private Label lblTotalElementos;

    // Solicitudes registradas
    @FXML private TableView<SolicitudServicio> tblSolicitudes;
    @FXML private TableColumn<SolicitudServicio, String> colSolCodigo, colSolCliente, colSolFecha, colSolTotal;

    @FXML private Label lblMensaje;

    private HelloApplication app;
    private TechSolutionsController controller;

    // Elementos que se están armando para la solicitud (estado de la pantalla)
    private final ObservableList<ElementoServicio> elementosSeleccionados = FXCollections.observableArrayList();
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private void initialize() {
        cmbCliente.setConverter(new StringConverter<>() {
            @Override public String toString(Cliente c) {
                return c == null ? "" : c.getNombre() + " (" + c.getDocumento() + ")";
            }
            @Override public Cliente fromString(String s) { return null; }
        });

        cmbElemento.setConverter(new StringConverter<>() {
            @Override public String toString(ElementoServicio e) {
                return e == null ? "" : textoElemento(e) + " — " + moneda(e.calcularCosto());
            }
            @Override public ElementoServicio fromString(String s) { return null; }
        });

        cmbPrioridad.setItems(FXCollections.observableArrayList("Baja", "Media", "Alta"));
        cmbPrioridad.setPromptText("Sin prioridad");

        tblElementos.setItems(elementosSeleccionados);
        colElemento.setCellValueFactory(c -> new SimpleStringProperty(textoElemento(c.getValue())));
        colCostoElemento.setCellValueFactory(c -> new SimpleStringProperty(moneda(c.getValue().calcularCosto())));

        colSolCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colSolCliente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCliente().getNombre()));
        colSolFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaCreacion().format(FORMATO_FECHA)));
        colSolTotal.setCellValueFactory(c -> new SimpleStringProperty(moneda(c.getValue().calcularCostoTotal())));
    }

    @Override
    public void setApp(HelloApplication app) {
        this.app = app;
        this.controller = app.getController();
        cmbCliente.setItems(FXCollections.observableArrayList(controller.consultarClientes()));
        cmbElemento.setItems(FXCollections.observableArrayList(controller.consultarElementosContratables()));
        actualizarTablaSolicitudes();
    }

    // ---------- Eventos ----------

    @FXML
    private void onAgregarElementoClick() {
        ElementoServicio elemento = cmbElemento.getValue();
        if (elemento == null) {
            mostrarMensaje("Seleccione un servicio, personalizado o paquete", true);
            return;
        }
        if (elementosSeleccionados.contains(elemento)) {
            mostrarMensaje("Ese elemento ya fue agregado a la solicitud", true);
            return;
        }
        elementosSeleccionados.add(elemento);
        cmbElemento.setValue(null);
        actualizarTotalElementos();
        ocultarMensaje();
    }

    @FXML
    private void onQuitarElementoClick() {
        ElementoServicio elemento = tblElementos.getSelectionModel().getSelectedItem();
        if (elemento == null) {
            mostrarMensaje("Seleccione un elemento de la tabla para quitarlo", true);
            return;
        }
        elementosSeleccionados.remove(elemento);
        actualizarTotalElementos();
        ocultarMensaje();
    }

    @FXML
    private void onRegistrarClick() {
        try {
            SolicitudServicio.Builder builder = new SolicitudServicio.Builder()
                    .conCodigo(txtCodigoSolicitud.getText())
                    .conCliente(cmbCliente.getValue())
                    .conFechaProgramada(dpFecha.getValue())
                    .conObservaciones(txtObservaciones.getText())
                    .conPrioridad(cmbPrioridad.getValue());

            for (ElementoServicio e : elementosSeleccionados) {
                builder.agregarElemento(e);
            }

            SolicitudServicio solicitud = controller.registrarSolicitud(builder);

            limpiarFormulario();
            actualizarTablaSolicitudes();
            mostrarMensaje("Solicitud " + solicitud.getCodigo() + " registrada. Total: "
                    + moneda(solicitud.calcularCostoTotal()), false);
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    private void onVolverClick() {
        SceneManager.cambiarEscena("view/inicio.fxml", app);   // nombre real de tu FXML de Inicio
    }

    // ---------- Auxiliares ----------

    private void actualizarTablaSolicitudes() {
        tblSolicitudes.setItems(FXCollections.observableArrayList(controller.consultarSolicitudes()));
    }

    private void actualizarTotalElementos() {
        double total = 0;
        for (ElementoServicio e : elementosSeleccionados) {
            total += e.calcularCosto();
        }
        lblTotalElementos.setText(moneda(total));
    }

    private void limpiarFormulario() {
        txtCodigoSolicitud.clear();
        cmbCliente.setValue(null);
        cmbPrioridad.setValue(null);
        dpFecha.setValue(null);
        txtObservaciones.clear();
        cmbElemento.setValue(null);
        elementosSeleccionados.clear();
        actualizarTotalElementos();
    }

    private String textoElemento(ElementoServicio e) {
        return e.getNombre() + " — " + e.getDescripcion();
    }

    private void mostrarMensaje(String texto, boolean error) {
        lblMensaje.setText(texto);
        lblMensaje.setStyle(error ? "-fx-text-fill: #B42318;" : "-fx-text-fill: #0F766E;");
        lblMensaje.setVisible(true);
    }

    private void ocultarMensaje() {
        lblMensaje.setVisible(false);
    }

    private String moneda(double valor) {
        return String.format("$%,.0f", valor);
    }
}