package org.example.parcial2;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.parcial2.controller.TechSolutionsController;
import org.example.parcial2.util.SceneManager;

public class HelloApplication extends Application {

    private final TechSolutionsController controller = new TechSolutionsController();  // única instancia
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("TechSolutions");
        SceneManager.cambiarEscena("Inicio.fxml", this);
    }

    public TechSolutionsController getController() { return controller; }

    public Stage getStage() { return stage; }

    public static void main(String[] args) {
        launch();
    }
}