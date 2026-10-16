package org.example.parcial2.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.parcial2.HelloApplication;

import java.io.IOException;

public class SceneManager {

    private SceneManager() { }

    public static void cambiarEscena(String rutaFXML, HelloApplication app) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(rutaFXML));
            Parent raiz = loader.load();

            Object viewController = loader.getController();
            if (viewController instanceof IAppControlable controlable) {
                controlable.setApp(app);      // le entrega la app (y con ella el controller compartido)
            }

            Stage stage = app.getStage();
            stage.setScene(new Scene(raiz));
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "No se pudo cargar la pantalla: " + rutaFXML).show();
        }
    }
}