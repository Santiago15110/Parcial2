package org.example.parcial2.model;

public abstract class ServicioTecnologico implements ElementoServicio{

    private String codigo;
    private String nombre;
    private String descripcion;
    private double costoBase;


    /**
     * Metodo constructor de servicioTecnologico
     * @param codigo
     * @param nombre
     * @param descripcion
     * @param costoBase
     */
    public ServicioTecnologico(String codigo, String nombre, String descripcion, double costoBase){

        this.codigo=codigo;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.costoBase=costoBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }



}
