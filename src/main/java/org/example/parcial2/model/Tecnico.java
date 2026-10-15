package org.example.parcial2.model;

public class Tecnico {
    private String idTecnico;
    private boolean disponible;
    private double costoDesplazamiento;

    /**
     * Método constructor
     * @param idTecnico identificador del técnico
     * @param disponible indica si el técnico está disponible
     * @param costoDesplazamiento valor del desplazamiento del técnico
     */
    public Tecnico(String idTecnico, boolean disponible, double costoDesplazamiento) {
        this.idTecnico = idTecnico;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public String getIdTecnico() { return idTecnico; }
    public boolean isDisponible() { return disponible; }
    public double getCostoDesplazamiento() { return costoDesplazamiento; }

    /**
     * Método para obtener la representación en texto del técnico
     * @return identificador, disponibilidad y costo de desplazamiento
     */
    @Override
    public String toString() {
        return idTecnico + " | " + (disponible ? "DISPONIBLE" : "NO DISPONIBLE")
                + " | desplazamiento: " + costoDesplazamiento;
    }
}

