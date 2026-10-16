package org.example.parcial2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolicitudServicioTest {

    /**
     * Método para crear un cliente de prueba
     * @return cliente de prueba
     */
    private Cliente crearCliente() {
        return new Cliente("1031", "David", "david@correo.com");
    }

    /**
     * Método para crear un servicio de prueba con un costo conocido
     * @param costo costo base del servicio
     * @return servicio de prueba
     */
    private ServicioTecnologico crearServicio(double costo) {
        return new MantenimientoPreventivo("S-001", "Preventivo", "Revisión", costo);
    }

    /**
     * Método para probar que el código es obligatorio
     */
    @Test
    @DisplayName("build sin código lanza IllegalStateException")
    void build_sinCodigo_lanzaIllegalStateException() {
        // Arrange
        SolicitudServicio.Builder builder = new SolicitudServicio.Builder()
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000));

        // Act + Assert
        assertThrows(IllegalStateException.class, builder::build);
    }

    /**
     * Método para probar que el cliente es obligatorio
     */
    @Test
    @DisplayName("build sin cliente lanza IllegalStateException")
    void build_sinCliente_lanzaIllegalStateException() {
        // Arrange
        SolicitudServicio.Builder builder = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .agregarElemento(crearServicio(80000));

        // Act + Assert
        assertThrows(IllegalStateException.class, builder::build);
    }

    /**
     * Método para probar que se necesita al menos un elemento
     */
    @Test
    @DisplayName("build sin elementos lanza IllegalStateException")
    void build_sinElementos_lanzaIllegalStateException() {
        // Arrange
        SolicitudServicio.Builder builder = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente());

        // Act + Assert
        assertThrows(IllegalStateException.class, builder::build);
    }

    /**
     * Método para probar la creación con solo los datos obligatorios
     */
    @Test
    @DisplayName("build con datos obligatorios crea la solicitud sin datos opcionales")
    void build_soloDatosObligatorios_creaLaSolicitud() {
        // Arrange
        SolicitudServicio.Builder builder = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000));

        // Act
        SolicitudServicio solicitud = builder.build();

        // Assert
        assertEquals("SOL-001", solicitud.getCodigo());
        assertEquals(LocalDate.now(), solicitud.getFechaCreacion());
        assertNull(solicitud.getFechaProgramada());
        assertNull(solicitud.getObservaciones());
        assertNull(solicitud.getPrioridad());
        assertNull(solicitud.getTecnico());
        assertFalse(solicitud.isConfirmada());
    }

    /**
     * Método para probar que los datos opcionales se conservan
     */
    @Test
    @DisplayName("build conserva fecha programada, observaciones y prioridad")
    void build_conDatosOpcionales_losConserva() {
        // Arrange
        LocalDate fecha = LocalDate.now().plusDays(3);
        SolicitudServicio.Builder builder = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .conFechaProgramada(fecha)
                .conObservaciones("Equipo en oficina")
                .conPrioridad("Alta");

        // Act
        SolicitudServicio solicitud = builder.build();

        // Assert
        assertEquals(fecha, solicitud.getFechaProgramada());
        assertEquals("Equipo en oficina", solicitud.getObservaciones());
        assertEquals("Alta", solicitud.getPrioridad());
    }

    /**
     * Método para probar el costo total sin atención presencial
     */
    @Test
    @DisplayName("calcularCostoTotal sin técnico suma solo los elementos")
    void calcularCostoTotal_sinTecnico_sumaLosElementos() {
        // Arrange
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .agregarElemento(crearServicio(120000))
                .build();

        // Act
        double total = solicitud.calcularCostoTotal();

        // Assert
        assertEquals(200000, total, 0.001);
        assertEquals(200000, solicitud.getCostoTotal(), 0.001);
    }

    /**
     * Método para probar que el desplazamiento se cobra una sola vez
     */
    @Test
    @DisplayName("calcularCostoTotal con técnico suma el desplazamiento una sola vez")
    void calcularCostoTotal_conTecnico_sumaElDesplazamientoUnaVez() {
        // Arrange
        Tecnico tecnico = new Tecnico("TEC-105", true, 30000);
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .agregarElemento(crearServicio(120000))
                .conTecnico(tecnico)
                .build();

        // Act
        double total = solicitud.calcularCostoTotal();
        double totalOtraVez = solicitud.calcularCostoTotal();

        // Assert
        assertEquals(230000, total, 0.001);
        assertEquals(230000, totalOtraVez, 0.001);
    }

    /**
     * Método para probar el costo con paquetes, características y desplazamiento
     */
    @Test
    @DisplayName("calcularCostoTotal incluye paquetes, características complementarias y desplazamiento")
    void calcularCostoTotal_paqueteCaracteristicasYTecnico_sumaTodo() {
        // Arrange
        PaqueteTecnologico mantenimiento = new PaqueteTecnologico("P-1", "Mantenimiento");
        mantenimiento.agregar(new MantenimientoPreventivo("S-001", "Preventivo", "Revisión", 80000));
        mantenimiento.agregar(new MantenimientoCorrectivo("S-002", "Correctivo", "Reparación", 120000));
        ElementoServicio correctivoPrioritario = new AtencionPrioritaria(
                new MantenimientoCorrectivo("S-002", "Correctivo", "Reparación", 120000));
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(mantenimiento)
                .agregarElemento(correctivoPrioritario)
                .conTecnico(new Tecnico("TEC-105", true, 30000))
                .build();

        // Act
        double total = solicitud.calcularCostoTotal();

        // Assert
        assertEquals(385000, total, 0.001);
    }

    /**
     * Método para probar la confirmación de una solicitud no presencial
     */
    @Test
    @DisplayName("confirmar sin técnico confirma la solicitud")
    void confirmar_sinTecnico_confirmaLaSolicitud() {
        // Arrange
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .build();

        // Act
        solicitud.confirmar();

        // Assert
        assertTrue(solicitud.isConfirmada());
    }

    /**
     * Método para probar la confirmación con técnico disponible
     */
    @Test
    @DisplayName("confirmar con técnico disponible confirma la solicitud")
    void confirmar_tecnicoDisponible_confirmaLaSolicitud() {
        // Arrange
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .conTecnico(new Tecnico("TEC-105", true, 30000))
                .build();

        // Act
        solicitud.confirmar();

        // Assert
        assertTrue(solicitud.isConfirmada());
    }

    /**
     * Método para probar que no se confirma sin técnico disponible
     */
    @Test
    @DisplayName("confirmar con técnico no disponible lanza IllegalStateException y no confirma")
    void confirmar_tecnicoNoDisponible_lanzaExcepcionYNoConfirma() {
        // Arrange
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .conTecnico(new Tecnico("SIN_TECNICO", false, 0))
                .build();

        // Act
        assertThrows(IllegalStateException.class, solicitud::confirmar);

        // Assert
        assertFalse(solicitud.isConfirmada());
    }

    /**
     * Método para probar que getElementos devuelve una copia
     */
    @Test
    @DisplayName("getElementos devuelve una copia que no modifica la solicitud")
    void getElementos_modificarLaCopia_noAfectaALaSolicitud() {
        // Arrange
        SolicitudServicio solicitud = new SolicitudServicio.Builder()
                .conCodigo("SOL-001")
                .conCliente(crearCliente())
                .agregarElemento(crearServicio(80000))
                .build();

        // Act
        List<ElementoServicio> copia = solicitud.getElementos();
        copia.clear();

        // Assert
        assertEquals(1, solicitud.getElementos().size());
    }
}
