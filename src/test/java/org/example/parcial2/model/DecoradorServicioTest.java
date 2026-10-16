package org.example.parcial2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecoradorServicioTest {

    /**
     * Método para crear un mantenimiento correctivo de prueba con su costo del enunciado
     * @return servicio de prueba de $120.000
     */
    private ServicioTecnologico crearCorrectivo() {
        return new MantenimientoCorrectivo("S-002", "Mantenimiento correctivo", "Reparación", 120000);
    }

    /**
     * Método para probar el incremento de la atención prioritaria
     */
    @Test
    @DisplayName("AtencionPrioritaria suma $35.000")
    void calcularCosto_atencionPrioritaria_suma35000() {
        // Arrange
        ElementoServicio servicio = new AtencionPrioritaria(crearCorrectivo());

        // Act
        double costo = servicio.calcularCosto();

        // Assert
        assertEquals(155000, costo, 0.001);
    }

    /**
     * Método para probar el incremento del soporte técnico
     */
    @Test
    @DisplayName("SoporteTecnico suma $50.000")
    void calcularCosto_soporteTecnico_suma50000() {
        // Arrange
        ElementoServicio servicio = new SoporteTecnico(crearCorrectivo());

        // Act
        double costo = servicio.calcularCosto();

        // Assert
        assertEquals(170000, costo, 0.001);
    }

    /**
     * Método para probar el incremento de la atención a domicilio
     */
    @Test
    @DisplayName("AtencionDomicilio suma $25.000")
    void calcularCosto_atencionDomicilio_suma25000() {
        // Arrange
        ElementoServicio servicio = new AtencionDomicilio(crearCorrectivo());

        // Act
        double costo = servicio.calcularCosto();

        // Assert
        assertEquals(145000, costo, 0.001);
    }

    /**
     * Método para probar que varias características se acumulan
     */
    @Test
    @DisplayName("varias características se acumulan sobre el mismo servicio")
    void calcularCosto_variasCaracteristicas_seAcumulan() {
        // Arrange
        ElementoServicio servicio = new SoporteTecnico(new AtencionPrioritaria(crearCorrectivo()));

        // Act
        double costo = servicio.calcularCosto();

        // Assert
        assertEquals(205000, costo, 0.001);
    }

    /**
     * Método para probar que las tres características juntas suman todos los incrementos
     */
    @Test
    @DisplayName("las tres características juntas suman $110.000")
    void calcularCosto_lasTresCaracteristicas_suman110000() {
        // Arrange
        ElementoServicio servicio = new AtencionDomicilio(
                new SoporteTecnico(new AtencionPrioritaria(crearCorrectivo())));

        // Act
        double costo = servicio.calcularCosto();

        // Assert
        assertEquals(230000, costo, 0.001);
    }

    /**
     * Método para probar que el servicio original del catálogo no cambia
     */
    @Test
    @DisplayName("decorar un servicio no modifica el servicio original")
    void calcularCosto_servicioOriginal_noCambiaAlDecorarlo() {
        // Arrange
        ServicioTecnologico original = crearCorrectivo();

        // Act
        new AtencionPrioritaria(original);
        new SoporteTecnico(original);

        // Assert
        assertEquals(120000, original.calcularCosto(), 0.001);
    }

    /**
     * Método para probar que el mismo servicio puede contratarse con y sin complementos
     */
    @Test
    @DisplayName("el mismo servicio se contrata con complementos para un cliente y sin ellos para otro")
    void calcularCosto_mismoServicioConYSinComplementos_costosDistintos() {
        // Arrange
        ServicioTecnologico catalogo = crearCorrectivo();
        ElementoServicio clienteConComplementos = new SoporteTecnico(new AtencionPrioritaria(catalogo));
        ElementoServicio clienteSinComplementos = catalogo;

        // Act
        double conComplementos = clienteConComplementos.calcularCosto();
        double sinComplementos = clienteSinComplementos.calcularCosto();

        // Assert
        assertEquals(205000, conComplementos, 0.001);
        assertEquals(120000, sinComplementos, 0.001);
    }

    /**
     * Método para probar que el decorador conserva el nombre del servicio
     */
    @Test
    @DisplayName("getNombre del decorador es el del servicio envuelto")
    void getNombre_decorador_devuelveElNombreDelServicio() {
        // Arrange
        ElementoServicio servicio = new AtencionPrioritaria(crearCorrectivo());

        // Act
        String nombre = servicio.getNombre();

        // Assert
        assertEquals("Mantenimiento correctivo", nombre);
    }

    /**
     * Método para probar que la descripción indica las características agregadas
     */
    @Test
    @DisplayName("getDescripcion agrega el texto de la característica")
    void getDescripcion_decorador_agregaLaCaracteristica() {
        // Arrange
        ElementoServicio servicio = new AtencionPrioritaria(crearCorrectivo());

        // Act
        String descripcion = servicio.getDescripcion();

        // Assert
        assertEquals("Reparación + Atencion prioritaria", descripcion);
    }
}
