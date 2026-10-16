package org.example.parcial2.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LegacySupportAdapterTest {

    /**
     * Método para probar la consulta de una especialidad con técnico disponible
     */
    @Test
    @DisplayName("consultarTecnico convierte la respuesta DISPONIBLE en un Tecnico estructurado")
    void consultarTecnico_especialidadConTecnico_devuelveTecnicoDisponible() {
        // Arrange
        ProveedorTecnico proveedor = new LegacySupportAdapter(new LegacySupport());

        // Act
        Tecnico tecnico = proveedor.consultarTecnico("Redes");

        // Assert
        assertEquals("TEC-105", tecnico.getIdTecnico());
        assertTrue(tecnico.isDisponible());
        assertEquals(30000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    /**
     * Método para probar la consulta de una especialidad sin técnico
     */
    @Test
    @DisplayName("consultarTecnico convierte la respuesta NO_DISPONIBLE en un Tecnico no disponible")
    void consultarTecnico_especialidadSinTecnico_devuelveTecnicoNoDisponible() {
        // Arrange
        ProveedorTecnico proveedor = new LegacySupportAdapter(new LegacySupport());

        // Act
        Tecnico tecnico = proveedor.consultarTecnico("Mantenimiento");

        // Assert
        assertEquals("SIN_TECNICO", tecnico.getIdTecnico());
        assertFalse(tecnico.isDisponible());
        assertEquals(0, tecnico.getCostoDesplazamiento(), 0.001);
    }

    /**
     * Método para probar que el adaptador interpreta cualquier respuesta con el formato de LegacySupport
     */
    @Test
    @DisplayName("consultarTecnico interpreta el formato ID|ESTADO|COSTO de la plataforma externa")
    void consultarTecnico_respuestaConFormatoLegacy_laInterpretaCorrectamente() {
        // Arrange
        LegacySupport legacyFalso = new LegacySupport() {
            @Override
            public String consultar(int codigoEspecialidad) {
                return "TEC-200|DISPONIBLE|45000";
            }
        };
        ProveedorTecnico proveedor = new LegacySupportAdapter(legacyFalso);

        // Act
        Tecnico tecnico = proveedor.consultarTecnico("Redes");

        // Assert
        assertEquals("TEC-200", tecnico.getIdTecnico());
        assertTrue(tecnico.isDisponible());
        assertEquals(45000, tecnico.getCostoDesplazamiento(), 0.001);
    }

    /**
     * Método para probar que el adaptador envía a LegacySupport el código numérico de la especialidad
     */
    @Test
    @DisplayName("consultarTecnico traduce el nombre de la especialidad al código numérico")
    void consultarTecnico_nombreDeEspecialidad_seEnviaElCodigoNumerico() {
        // Arrange
        int[] codigoRecibido = new int[1];
        LegacySupport legacyEspia = new LegacySupport() {
            @Override
            public String consultar(int codigoEspecialidad) {
                codigoRecibido[0] = codigoEspecialidad;
                return "SIN_TECNICO|NO_DISPONIBLE|0";
            }
        };
        ProveedorTecnico proveedor = new LegacySupportAdapter(legacyEspia);

        // Act
        proveedor.consultarTecnico("Redes");

        // Assert
        assertEquals(105, codigoRecibido[0]);
    }

    /**
     * Método para probar que una especialidad desconocida se rechaza
     */
    @Test
    @DisplayName("consultarTecnico con una especialidad desconocida lanza IllegalArgumentException")
    void consultarTecnico_especialidadDesconocida_lanzaIllegalArgumentException() {
        // Arrange
        ProveedorTecnico proveedor = new LegacySupportAdapter(new LegacySupport());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> proveedor.consultarTecnico("Cocina"));
    }

    /**
     * Método para probar que una especialidad nula se rechaza
     */
    @Test
    @DisplayName("consultarTecnico con especialidad nula lanza IllegalArgumentException")
    void consultarTecnico_especialidadNula_lanzaIllegalArgumentException() {
        // Arrange
        ProveedorTecnico proveedor = new LegacySupportAdapter(new LegacySupport());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> proveedor.consultarTecnico(null));
    }
}