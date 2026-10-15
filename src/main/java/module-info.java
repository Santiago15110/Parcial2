module org.example.parcial2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.parcial2 to javafx.fxml;
    exports org.example.parcial2;
}