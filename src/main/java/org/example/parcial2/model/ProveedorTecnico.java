package org.example.parcial2.model;

public interface ProveedorTecnico {
    /**
     * Método para consultar la disponibilidad de un técnico según su especialidad
     * @param especialidad nombre de la especialidad requerida
     * @return técnico con su identificador, disponibilidad y costo de desplazamiento
     */
    Tecnico consultarTecnico(String especialidad);
}
