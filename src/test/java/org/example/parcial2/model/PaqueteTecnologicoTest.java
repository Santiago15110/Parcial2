package org.example.parcial2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaqueteTecnologicoTest {

    /**
     * Método para crear un servicio de prueba con un costo conocido
     * @param codigo código del servicio
     * @param costo costo base del servicio
     * @return servicio de prueba
     */
    private ServicioTecnologico crearServicio(String codigo, double costo) {
        return new MantenimientoPreventivo(codigo, "Servicio " + codigo, "Descripción", costo);
    }

    /**
     * Método para probar que el costo del paquete es la suma de sus servicios
     */
    @Test
    @DisplayName("calcularCosto suma los servicios del paquete")
    void calcularCosto_paqueteConDosServicios_sumaLosCostos() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Mantenimiento");
        paquete.agregar(crearServicio("S-1", 80000));
        paquete.agregar(crearServicio("S-2", 120000));

        // Act
        double costo = paquete.calcularCosto();

        // Assert
        assertEquals(200000, costo, 0.001);
    }

    /**
     * Método para probar que el costo de un paquete vacío es cero
     */
    @Test
    @DisplayName("calcularCosto de un paquete vacío es cero")
    void calcularCosto_paqueteVacio_esCero() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Vacío");

        // Act
        double costo = paquete.calcularCosto();

        // Assert
        assertEquals(0, costo, 0.001);
    }

    /**
     * Método para probar que el costo suma todos los niveles de paquetes
     */
    @Test
    @DisplayName("calcularCosto suma paquetes dentro de paquetes en todos los niveles")
    void calcularCosto_paqueteDentroDePaquete_sumaTodosLosNiveles() {
        // Arrange
        PaqueteTecnologico mantenimiento = new PaqueteTecnologico("P-1", "Mantenimiento");
        mantenimiento.agregar(crearServicio("S-1", 80000));
        mantenimiento.agregar(crearServicio("S-2", 120000));
        PaqueteTecnologico empresarial = new PaqueteTecnologico("P-2", "Empresarial");
        empresarial.agregar(mantenimiento);
        empresarial.agregar(crearServicio("S-3", 180000));
        empresarial.agregar(crearServicio("S-4", 150000));

        // Act
        double costo = empresarial.calcularCosto();

        // Assert
        assertEquals(530000, costo, 0.001);
    }

    /**
     * Método para probar que un paquete puede contener servicios personalizados
     */
    @Test
    @DisplayName("calcularCosto incluye el costo de las características de un servicio personalizado")
    void calcularCosto_servicioPersonalizado_incluyeElIncremento() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Personalizado");
        paquete.agregar(new AtencionPrioritaria(crearServicio("S-1", 80000)));

        // Act
        double costo = paquete.calcularCosto();

        // Assert
        assertEquals(115000, costo, 0.001);
    }

    /**
     * Método para probar que no se agrega un elemento nulo
     */
    @Test
    @DisplayName("agregar un elemento nulo retorna false")
    void agregar_elementoNulo_retornaFalse() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Paquete");

        // Act
        boolean agregado = paquete.agregar(null);

        // Assert
        assertFalse(agregado);
        assertTrue(paquete.getElementos().isEmpty());
    }

    /**
     * Método para probar que un paquete no se agrega a sí mismo
     */
    @Test
    @DisplayName("agregar el mismo paquete retorna false")
    void agregar_elMismoPaquete_retornaFalse() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Paquete");

        // Act
        boolean agregado = paquete.agregar(paquete);

        // Assert
        assertFalse(agregado);
    }

    /**
     * Método para probar que se impide el ciclo directo entre dos paquetes
     */
    @Test
    @DisplayName("agregar un paquete que ya contiene al actual (ciclo directo) retorna false")
    void agregar_cicloDirecto_retornaFalse() {
        // Arrange
        PaqueteTecnologico a = new PaqueteTecnologico("P-A", "A");
        PaqueteTecnologico b = new PaqueteTecnologico("P-B", "B");
        a.agregar(b);

        // Act
        boolean agregado = b.agregar(a);

        // Assert
        assertFalse(agregado);
        assertTrue(b.getElementos().isEmpty());
    }

    /**
     * Método para probar que se impide el ciclo indirecto a varios niveles
     */
    @Test
    @DisplayName("agregar un paquete que contiene al actual en un nivel más profundo (ciclo indirecto) retorna false")
    void agregar_cicloIndirecto_retornaFalse() {
        // Arrange
        PaqueteTecnologico a = new PaqueteTecnologico("P-A", "A");
        PaqueteTecnologico b = new PaqueteTecnologico("P-B", "B");
        PaqueteTecnologico c = new PaqueteTecnologico("P-C", "C");
        a.agregar(b);
        b.agregar(c);

        // Act
        boolean agregado = c.agregar(a);

        // Assert
        assertFalse(agregado);
    }

    /**
     * Método para probar que se pueden agregar paquetes sin ciclos
     */
    @Test
    @DisplayName("agregar un paquete independiente retorna true")
    void agregar_paqueteIndependiente_retornaTrue() {
        // Arrange
        PaqueteTecnologico a = new PaqueteTecnologico("P-A", "A");
        PaqueteTecnologico b = new PaqueteTecnologico("P-B", "B");

        // Act
        boolean agregado = a.agregar(b);

        // Assert
        assertTrue(agregado);
        assertEquals(1, a.getElementos().size());
    }

    /**
     * Método para probar que contiene encuentra elementos en subpaquetes
     */
    @Test
    @DisplayName("contiene encuentra un elemento dentro de un subpaquete")
    void contiene_elementoEnSubpaquete_retornaTrue() {
        // Arrange
        ServicioTecnologico servicio = crearServicio("S-1", 80000);
        PaqueteTecnologico interno = new PaqueteTecnologico("P-1", "Interno");
        interno.agregar(servicio);
        PaqueteTecnologico externo = new PaqueteTecnologico("P-2", "Externo");
        externo.agregar(interno);

        // Act
        boolean contiene = externo.contiene(servicio);

        // Assert
        assertTrue(contiene);
    }

    /**
     * Método para probar que contiene no encuentra elementos ajenos
     */
    @Test
    @DisplayName("contiene retorna false para un elemento que no está en el paquete")
    void contiene_elementoAjeno_retornaFalse() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Paquete");
        paquete.agregar(crearServicio("S-1", 80000));

        // Act
        boolean contiene = paquete.contiene(crearServicio("S-2", 120000));

        // Assert
        assertFalse(contiene);
    }

    /**
     * Método para probar que getElementos devuelve una copia de la lista
     */
    @Test
    @DisplayName("getElementos devuelve una copia que no modifica el paquete")
    void getElementos_modificarLaCopia_noAfectaAlPaquete() {
        // Arrange
        PaqueteTecnologico paquete = new PaqueteTecnologico("P-1", "Paquete");
        paquete.agregar(crearServicio("S-1", 80000));

        // Act
        List<ElementoServicio> copia = paquete.getElementos();
        copia.clear();

        // Assert
        assertEquals(1, paquete.getElementos().size());
    }
}
