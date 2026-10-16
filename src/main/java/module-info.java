module org.example.parcial2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.parcial2 to javafx.fxml;
    opens org.example.parcial2.viewController to javafx.fxml;
    opens org.example.parcial2.model to javafx.fxml;
    opens org.example.parcial2.controller to javafx.fxml;

    exports org.example.parcial2;
    exports org.example.parcial2.viewController;
    exports org.example.parcial2.model;
    exports org.example.parcial2.controller;
    exports org.example.parcial2.util;
}