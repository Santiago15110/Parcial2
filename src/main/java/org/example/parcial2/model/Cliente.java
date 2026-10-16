package org.example.parcial2.model;

public class Cliente {

    private  String documento;
    private  String nombre;
    private String correoElec;


    public Cliente(String documento, String nombre, String corrreoElec){

        this.documento=documento;
        this.nombre=nombre;
        this.correoElec=corrreoElec;

    }


    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoElec() {
        return correoElec;
    }
}
