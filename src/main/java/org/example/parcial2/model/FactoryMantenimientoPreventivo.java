package org.example.parcial2.model;

public class FactoryMantenimientoPreventivo implements FactoryServicio{

    private static final double COSTO_BASE=80000;


    /**
     * Metodo para crear Servicio de mantenimiento preventivo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String codigo, String nombre, String descripcion){

        return new MantenimientoPreventivo(codigo, nombre, descripcion, COSTO_BASE);
    }


}
