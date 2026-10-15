package org.example.parcial2.model;

import java.net.CookieStore;

public class FactoryMantenimientoCorrectivo implements FactoryServicio{


    private static final double COSTO_BASE = 120000;


    /**
     * Metodo para crear Servicio de mantenimiento correctivo
     * @param codigo
     * @param nombre
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String codigo, String nombre, String descripcion){

        return new MantenimientoCorrectivo(codigo, nombre, descripcion, COSTO_BASE);
    }
}
