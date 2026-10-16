package org.example.parcial2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestorClientesTest {

    /**
     * Método para probar el registro de un cliente con datos válidos
     */
    @Test
    @DisplayName("registrarCliente con datos válidos agrega y devuelve el cliente")
    void registrarCliente_datosValidos_agregaYDevuelveElCliente() {
        // Arrange
        GestorClientes gestor = new GestorClientes();

        // Act
        Cliente cliente = gestor.registrarCliente("1031", "David", "david@correo.com");

        // Assert
        assertEquals("David", cliente.getNombre());
        assertSame(cliente, gestor.buscarCliente("1031"));
        assertEquals(1, gestor.consultarClientes().size());
    }

    /**
     * Método para probar que no se repite el documento
     */
    @Test
    @DisplayName("registrarCliente con documento repetido lanza IllegalArgumentException")
    void registrarCliente_documentoRepetido_lanzaIllegalArgumentException() {
        // Arrange
        GestorClientes gestor = new GestorClientes();
        gestor.registrarCliente("1031", "David", "david@correo.com");

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarCliente("1031", "Otro", "otro@correo.com"));
    }

    /**
     * Método para probar que el documento es obligatorio
     */
    @Test
    @DisplayName("registrarCliente con documento vacío lanza IllegalArgumentException")
    void registrarCliente_documentoVacio_lanzaIllegalArgumentException() {
        // Arrange
        GestorClientes gestor = new GestorClientes();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarCliente(" ", "David", "david@correo.com"));
    }

    /**
     * Método para probar que el nombre es obligatorio
     */
    @Test
    @DisplayName("registrarCliente con nombre nulo lanza IllegalArgumentException")
    void registrarCliente_nombreNulo_lanzaIllegalArgumentException() {
        // Arrange
        GestorClientes gestor = new GestorClientes();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarCliente("1031", null, "david@correo.com"));
    }

    /**
     * Método para probar que el correo es obligatorio
     */
    @Test
    @DisplayName("registrarCliente con correo nulo lanza IllegalArgumentException")
    void registrarCliente_correoNulo_lanzaIllegalArgumentException() {
        // Arrange
        GestorClientes gestor = new GestorClientes();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> gestor.registrarCliente("1031", "David", null));
    }

    /**
     * Método para probar la búsqueda de un cliente que no existe
     */
    @Test
    @DisplayName("buscarCliente con un documento inexistente retorna null")
    void buscarCliente_documentoInexistente_retornaNull() {
        // Arrange
        GestorClientes gestor = new GestorClientes();

        // Act
        Cliente cliente = gestor.buscarCliente("9999");

        // Assert
        assertNull(cliente);
    }

    /**
     * Método para probar que consultarClientes devuelve una copia
     */
    @Test
    @DisplayName("consultarClientes devuelve una copia que no modifica el gestor")
    void consultarClientes_modificarLaCopia_noAfectaAlGestor() {
        // Arrange
        GestorClientes gestor = new GestorClientes();
        gestor.registrarCliente("1031", "David", "david@correo.com");

        // Act
        List<Cliente> copia = gestor.consultarClientes();
        copia.clear();

        // Assert
        assertEquals(1, gestor.consultarClientes().size());
    }
}
