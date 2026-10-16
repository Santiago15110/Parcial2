package org.example.parcial2.viewController;

import javafx.fxml.FXML;
import org.example.parcial2.HelloApplication;
import org.example.parcial2.util.IAppControlable;
import org.example.parcial2.util.SceneManager;

public class viewControllerInicio implements IAppControlable {

    private HelloApplication app;

    @Override
    public void setApp(HelloApplication app) {
        this.app = app;
    }

    @FXML
    private void onServiciosClick() {
        SceneManager.cambiarEscena("Servicios.fxml", app);
    }

    @FXML
    private void onClientesClick() {
        SceneManager.cambiarEscena("Clientes.fxml", app);
    }

    @FXML
    private void onPaquetesClick() {
        SceneManager.cambiarEscena("Paquetes.fxml", app);
    }

    @FXML
    private void onSolicitudesClick() {
        SceneManager.cambiarEscena("Solicitudes.fxml", app);
    }

    @FXML
    private void onTecnicosClick() {
        SceneManager.cambiarEscena("Tecnicos.fxml", app);
    }
}