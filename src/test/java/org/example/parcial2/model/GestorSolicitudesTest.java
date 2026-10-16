package org.example.parcial2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestorSolicitudesTest {

    /**
     * Método para crear un builder de prueba con los datos obligatorios
     * @param codigo código de la solicitud
     * @return builder listo para construir
     */
    private SolicitudServicio.Builder crearBuilder(String codigo) {
        return new SolicitudServicio.Builder()
                .conCodigo(codigo)
                .conCliente(new Cliente("1031", "David", "david@correo.com"))
                .agregarElemento(new MantenimientoPreventivo("S-001", "Preventivo", "Revisión", 80000));
    }

    /**
     * Método para probar el registro de una solicitud válida
     */
    @Test
    @DisplayName("registrar guarda la solicitud y se puede buscar por código")
    void registrar_solicitudValida_laGuardaYSePuedeBuscar() {
        // Arrange
        GestorSolicitudes gestor = new GestorSolicitudes();

        // Act
        SolicitudServicio solicitud = gestor.registrar(crearBuilder("SOL-001"));

        // Assert
        assertSame(solicitud, gestor.buscarPorCodigo("SOL-001"));
        assertEquals(1, gestor.consultarSolicitudes().size());
    }

    /**
     * Método para probar que el código de la solicitud es único
     */
    @Test
    @DisplayName("registrar con código repetido lanza IllegalArgumentException")
    void registrar_codigoRepetido_lanzaIllegalArgumentException() {
        // Arrange
        GestorSolicitudes gestor = new GestorSolicitudes();
        gestor.registrar(crearBuilder("SOL-001"));

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> gestor.registrar(crearBuilder("SOL-001")));
        assertEquals(1, gestor.consultarSolicitudes().size());
    }

    /**
     * Método para probar que no se registra una solicitud sin la información esencial
     */
    @Test
    @DisplayName("registrar una solicitud sin cliente lanza IllegalStateException y no la guarda")
    void registrar_solicitudSinCliente_lanzaExcepcionYNoLaGuarda() {
        // Arrange
        GestorSolicitudes gestor = new GestorSolicitudes();
        SolicitudServicio.Builder incompleto = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .agregarElemento(new MantenimientoPreventivo("S-001", "Preventivo", "Revisión", 80000));

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> gestor.registrar(incompleto));
        assertEquals(0, gestor.consultarSolicitudes().size());
    }

    /**
     * Método para probar la búsqueda de una solicitud inexistente
     */
    @Test
    @DisplayName("buscarPorCodigo con un código inexistente retorna null")
    void buscarPorCodigo_codigoInexistente_retornaNull() {
        // Arrange
        GestorSolicitudes gestor = new GestorSolicitudes();

        // Act
        SolicitudServicio solicitud = gestor.buscarPorCodigo("SOL-999");

        // Assert
        assertNull(solicitud);
    }
}