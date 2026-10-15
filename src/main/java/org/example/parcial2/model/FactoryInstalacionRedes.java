package org.example.parcial2.model;

public class FactoryInstalacionRedes implements FactoryServicio{

    private static final double COSTO_BASE= 180000;


    /**
     * Metodo para crear servicio de instalacion de redes
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String codigo, String nombre, String descripcion){

        return new InstalacionRedes(codigo, nombre, descripcion, COSTO_BASE);
    }
}
