package org.example.parcial2.viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parcial2.HelloApplication;
import org.example.parcial2.controller.TechSolutionsController;
import org.example.parcial2.model.*;
import org.example.parcial2.util.IAppControlable;
import org.example.parcial2.util.SceneManager;

import java.util.LinkedHashMap;

public class viewControllerServicios implements IAppControlable {

    // Tabla
    @FXML private TableView<ServicioTecnologico> tblServicios;
    @FXML private TableColumn<ServicioTecnologico, String> colCodigo, colNombre, colDescripcion, colCostoBase;

    // Crear servicio
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtCodigo, txtNombre, txtDescripcion;

    // Personalizar
    @FXML private Label lblServicioSeleccionado;
    @FXML private CheckBox chkPrioritaria, chkSoporte, chkDomicilio;
    @FXML private Label lblCostoBase, lblIncrementos, lblCostoFinal, lblMensaje;

    private HelloApplication app;
    private TechSolutionsController controller;

    // texto que ve el usuario -> fábrica que crea ese tipo
    private final LinkedHashMap<String, FactoryServicio> fabricas = new LinkedHashMap<>();

    @FXML
    private void initialize() {
        fabricas.put("Mantenimiento preventivo", new FactoryMantenimientoPreventivo());
        fabricas.put("Mantenimiento correctivo", new FactoryMantenimientoCorrectivo());
        fabricas.put("Instalación de redes", new FactoryInstalacionRedes());
        fabricas.put("Recuperación de información", new FactoryRecuperacionInformacion());
        cmbTipo.setItems(FXCollections.observableArrayList(fabricas.keySet()));

        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));
        colCostoBase.setCellValueFactory(c -> new SimpleStringProperty(moneda(c.getValue().calcularCosto())));

        // Al seleccionar un servicio de la tabla, se reinicia la personalización
        tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> {
            chkPrioritaria.setSelected(false);
            chkSoporte.setSelected(false);
            chkDomicilio.setSelected(false);
            if (actual == null) {
                lblServicioSeleccionado.setText("Selecciona un servicio de la tabla");
                mostrarCostos(0, 0);
            } else {
                lblServicioSeleccionado.setText(actual.getNombre() + " (" + actual.getCodigo() + ")");
                mostrarCostos(actual.calcularCosto(), actual.calcularCosto());
            }
        });
    }

    @Override
    public void setApp(HelloApplication app) {
        this.app = app;
        this.controller = app.getController();
        actualizarTabla();
    }

    // ---------- Crear servicio ----------

    @FXML
    private void onCrearServicioClick() {
        String tipo = cmbTipo.getValue();
        if (tipo == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione un tipo de servicio").show();
            return;
        }
        try {
            controller.crearServicio(fabricas.get(tipo),
                    txtCodigo.getText(),
                    txtNombre.getText(),
                    txtDescripcion.getText());
            txtCodigo.clear();
            txtNombre.clear();
            txtDescripcion.clear();
            cmbTipo.setValue(null);
            actualizarTabla();
            mostrarMensaje("Servicio creado correctamente", false);
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    // ---------- Personalizar ----------

    @FXML
    private void onCaracteristicaClick() {
        ServicioTecnologico base = tblServicios.getSelectionModel().getSelectedItem();
        if (base == null) {
            new Alert(Alert.AlertType.WARNING, "Primero seleccione un servicio de la tabla").show();
            chkPrioritaria.setSelected(false);
            chkSoporte.setSelected(false);
            chkDomicilio.setSelected(false);
            return;
        }
        ElementoServicio personalizado = armarPersonalizado(base);
        mostrarCostos(base.calcularCosto(), personalizado.calcularCosto());
    }

    @FXML
    private void onGuardarPersonalizadoClick() {
        ServicioTecnologico base = tblServicios.getSelectionModel().getSelectedItem();
        if (base == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione un servicio de la tabla").show();
            return;
        }
        if (!chkPrioritaria.isSelected() && !chkSoporte.isSelected() && !chkDomicilio.isSelected()) {
            new Alert(Alert.AlertType.WARNING, "Marque al menos una característica").show();
            return;
        }
        ElementoServicio personalizado = armarPersonalizado(base);
        controller.guardarServicioPersonalizado(personalizado);
        mostrarMensaje("Servicio personalizado guardado: " + base.getNombre()
                + " — costo final " + moneda(personalizado.calcularCosto()), false);
    }


    @FXML
    private void onVolverClick() {
        SceneManager.cambiarEscena("Inicio.fxml", app);   // nombre real de tu FXML de Inicio
    }


    private ElementoServicio armarPersonalizado(ServicioTecnologico base) {
        return controller.personalizarServicio(base,
                chkPrioritaria.isSelected(),
                chkSoporte.isSelected(),
                chkDomicilio.isSelected());
    }

    private void mostrarCostos(double costoBase, double costoFinal) {
        lblCostoBase.setText(moneda(costoBase));
        lblIncrementos.setText("+" + moneda(costoFinal - costoBase));
        lblCostoFinal.setText(moneda(costoFinal));
    }

    private void actualizarTabla() {
        tblServicios.setItems(FXCollections.observableArrayList(controller.consultarServicios()));
    }

    private void mostrarMensaje(String texto, boolean error) {
        lblMensaje.setText(texto);
        lblMensaje.setStyle(error ? "-fx-text-fill: #B42318;" : "-fx-text-fill: #0F766E;");
        lblMensaje.setVisible(true);
    }

    private String moneda(double valor) {
        return String.format("$%,.0f", valor);
    }
}