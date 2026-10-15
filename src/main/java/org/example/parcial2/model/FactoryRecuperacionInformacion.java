package org.example.parcial2.model;

public class FactoryRecuperacionInformacion implements FactoryServicio{

    private static final double COSTO_BASE=150000;


    /**
     * Metodo para crear servicio de recuperacion de servicio
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String codigo, String nombre, String descripcion){
        return new RecuperacionInformacion(codigo, nombre, descripcion, COSTO_BASE);
    }

}
