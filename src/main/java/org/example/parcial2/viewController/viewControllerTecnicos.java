package org.example.parcial2.viewController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.example.parcial2.HelloApplication;
import org.example.parcial2.controller.TechSolutionsController;
import org.example.parcial2.model.*;
import org.example.parcial2.util.IAppControlable;
import org.example.parcial2.util.SceneManager;

public class viewControllerTecnicos implements IAppControlable {

    @FXML private ComboBox<String> cmbEspecialidadConsulta;
    @FXML private Label lblTecnicoId;
    @FXML private Label lblTecnicoDisponibilidad;
    @FXML private Label lblTecnicoDesplazamiento;
    @FXML private Label lblMensajeTecnico;

    @FXML private ComboBox<SolicitudServicio> cmbSolicitud;
    @FXML private Label lblCostoElementos;
    @FXML private Label lblCostoDesplazamiento;
    @FXML private Label lblCostoTotal;
    @FXML private Label lblEstado;
    @FXML private Button btnConfirmar;
    @FXML private Label lblMensaje;

    private HelloApplication app;
    private TechSolutionsController controller;

    @FXML
    private void initialize() {
        cmbEspecialidadConsulta.setItems(FXCollections.observableArrayList(
                "Mantenimiento", "Redes", "Recuperacion de informacion", "Seguridad informatica"));

        cmbSolicitud.setConverter(new StringConverter<>() {
            @Override
            public String toString(SolicitudServicio s) {
                return s == null ? "" : s.getCodigo() + " - " + s.getCliente().getNombre();
            }

            @Override
            public SolicitudServicio fromString(String s) {
                return null;
            }
        });
    }

    @Override
    public void setApp(HelloApplication app) {
        this.app = app;
        this.controller = app.getController();
        actualizarSolicitudes();
    }

    @FXML
    private void onConsultarTecnicoClick() {
        String especialidad = cmbEspecialidadConsulta.getValue();
        if (especialidad == null) {
            mostrarMensajeTecnico("Seleccione una especialidad", true);
            return;
        }
        try {
            Tecnico tecnico = controller.consultarTecnico(especialidad);
            if (tecnico == null) {
                limpiarTecnico();
                mostrarMensajeTecnico("No se encontró un técnico para esa especialidad", true);
                return;
            }
            lblTecnicoId.setText(tecnico.getIdTecnico());
            lblTecnicoDisponibilidad.setText(tecnico.isDisponible() ? "Disponible" : "No disponible");
            lblTecnicoDesplazamiento.setText(moneda(tecnico.getCostoDesplazamiento()));
            mostrarMensajeTecnico(tecnico.isDisponible()
                    ? "Técnico disponible"
                    : "No hay técnicos disponibles para esa especialidad", !tecnico.isDisponible());
        } catch (IllegalArgumentException | IllegalStateException e) {
            limpiarTecnico();
            mostrarMensajeTecnico(e.getMessage(), true);
        }
    }

    @FXML
    private void onSolicitudSeleccionada() {
        mostrarCosto(cmbSolicitud.getValue());
    }

    @FXML
    private void onConfirmarClick() {
        SolicitudServicio solicitud = cmbSolicitud.getValue();
        if (solicitud == null) {
            mostrarMensaje("Seleccione una solicitud", true);
            return;
        }
        if (solicitud.isConfirmada()) {
            mostrarMensaje("La solicitud ya está confirmada", true);
            return;
        }
        try {
            solicitud.confirmar();
            mostrarCosto(solicitud);
            mostrarMensaje("Solicitud " + solicitud.getCodigo() + " confirmada", false);
        } catch (IllegalStateException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    private void onVolverClick() {
        SceneManager.cambiarEscena("view/Inicio.fxml", app);
    }

    private void actualizarSolicitudes() {
        cmbSolicitud.setItems(FXCollections.observableArrayList(controller.consultarSolicitudes()));
    }

    private void mostrarCosto(SolicitudServicio solicitud) {
        if (solicitud == null) {
            lblCostoElementos.setText("0");
            lblCostoDesplazamiento.setText("0");
            lblCostoTotal.setText("0");
            lblEstado.setText("-");
            btnConfirmar.setDisable(false);
            return;
        }
        double elementos = 0;
        for (ElementoServicio e : solicitud.getElementos()) {
            elementos += e.calcularCosto();
        }
        double desplazamiento = solicitud.getTecnico() == null
                ? 0 : solicitud.getTecnico().getCostoDesplazamiento();

        lblCostoElementos.setText(moneda(elementos));
        lblCostoDesplazamiento.setText(moneda(desplazamiento));
        lblCostoTotal.setText(moneda(solicitud.calcularCostoTotal()));
        lblEstado.setText(solicitud.isConfirmada() ? "Confirmada" : "Pendiente");
        btnConfirmar.setDisable(solicitud.isConfirmada());
    }

    private void limpiarTecnico() {
        lblTecnicoId.setText("-");
        lblTecnicoDisponibilidad.setText("-");
        lblTecnicoDesplazamiento.setText("-");
    }

    private void mostrarMensajeTecnico(String texto, boolean error) {
        lblMensajeTecnico.setText(texto);
        lblMensajeTecnico.setStyle(error ? "-fx-text-fill: #B42318;" : "-fx-text-fill: #0F766E;");
        lblMensajeTecnico.setVisible(true);
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