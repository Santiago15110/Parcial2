package org.example.parcial2.model;

public class LegacySupport {
    /**
     * Método para consultar un técnico en la plataforma externa
     * @param codigoEspecialidad
     * @return cadena con el formato "ID|ESTADO|COSTO"
     */
    public String consultar(int codigoEspecialidad) {
        if (codigoEspecialidad == 105) {
            return "TEC-105|DISPONIBLE|30000";
        }
        return "SIN_TECNICO|NO_DISPONIBLE|0";
    }
}


