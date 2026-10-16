package org.example.parcial2.viewController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parcial2.HelloApplication;
import org.example.parcial2.controller.TechSolutionsController;
import org.example.parcial2.model.*;
import org.example.parcial2.util.IAppControlable;
import org.example.parcial2.util.SceneManager;

public class viewControllerPaquetes implements IAppControlable {

    // Nuevo paquete
    @FXML private TextField txtCodigoPaquete;
    @FXML private TextField txtNombrePaquete;

    @FXML private ListView<PaqueteTecnologico> lstPaquetes;
    @FXML private ListView<ElementoServicio> lstDisponibles;

    // Detalle del paquete seleccionado
    @FXML private Label lblPaqueteSeleccionado;
    @FXML private TreeView<String> treeComponentes;
    @FXML private Label lblCostoTotal;

    @FXML private Label lblMensaje;

    private HelloApplication app;
    private TechSolutionsController controller;

    @FXML
    private void initialize() {
        lstPaquetes.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PaqueteTecnologico p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getNombre() + " (" + p.getCodigo() + ")");
            }
        });

        lstDisponibles.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ElementoServicio e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? null
                        : e.getNombre() + " — " + moneda(e.calcularCosto()));
            }
        });

        lstPaquetes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, actual) -> mostrarDetalle(actual));

        treeComponentes.setShowRoot(true);
    }

    @Override
    public void setApp(HelloApplication app) {
        this.app = app;
        this.controller = app.getController();
        actualizarListas();
    }


    @FXML
    private void onCrearPaqueteClick() {
        try {
            controller.crearPaquete(txtCodigoPaquete.getText(), txtNombrePaquete.getText());

            txtCodigoPaquete.clear();
            txtNombrePaquete.clear();
            actualizarListas();
            mostrarMensaje("Paquete creado correctamente", false);
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    private void onAgregarElementoClick() {
        PaqueteTecnologico paquete = lstPaquetes.getSelectionModel().getSelectedItem();
        ElementoServicio elemento = lstDisponibles.getSelectionModel().getSelectedItem();

        if (paquete == null) {
            mostrarMensaje("Seleccione un paquete de la lista", true);
            return;
        }
        if (elemento == null) {
            mostrarMensaje("Seleccione un elemento disponible para agregar", true);
            return;
        }
        try {
            controller.agregarElementoAPaquete(paquete, elemento);
            actualizarListas();
            lstPaquetes.getSelectionModel().select(paquete);
            mostrarDetalle(paquete);
            mostrarMensaje("Elemento agregado al paquete", false);
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    private void onVolverClick() {
        SceneManager.cambiarEscena("view/Inicio.fxml", app);
    }


    private void actualizarListas() {
        lstPaquetes.setItems(FXCollections.observableArrayList(controller.consultarPaquetes()));
        lstDisponibles.setItems(FXCollections.observableArrayList(controller.consultarElementosContratables()));
    }

    private void mostrarDetalle(PaqueteTecnologico paquete) {
        if (paquete == null) {
            lblPaqueteSeleccionado.setText("Selecciona un paquete");
            treeComponentes.setRoot(null);
            lblCostoTotal.setText("0");
            return;
        }
        lblPaqueteSeleccionado.setText("Paquete: " + paquete.getNombre());
        treeComponentes.setRoot(construirArbol(paquete));
        lblCostoTotal.setText(moneda(paquete.calcularCosto()));
    }


    private TreeItem<String> construirArbol(ElementoServicio elemento) {
        TreeItem<String> nodo = new TreeItem<>(
                elemento.getNombre() + " (" + moneda(elemento.calcularCosto()) + ")");
        nodo.setExpanded(true);

        if (elemento instanceof PaqueteTecnologico paquete) {
            for (ElementoServicio hijo : paquete.getElementos()) {
                nodo.getChildren().add(construirArbol(hijo));
            }
        }
        return nodo;
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