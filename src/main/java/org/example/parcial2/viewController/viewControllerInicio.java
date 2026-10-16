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
        SceneManager.cambiarEscena("view/servicios.fxml", app);
    }

    @FXML
    private void onClientesClick() {
        SceneManager.cambiarEscena("view/clientes.fxml", app);
    }

    @FXML
    private void onPaquetesClick() {
        SceneManager.cambiarEscena("view/paquetes.fxml", app);
    }

    @FXML
    private void onSolicitudesClick() {
        SceneManager.cambiarEscena("view/solicitudes.fxml", app);
    }

    @FXML
    private void onTecnicosClick() {
        SceneManager.cambiarEscena("view/tecnicos.fxml", app);
    }
}