package org.example.parcial2.model;

public class AtencionPrioritaria extends DecoradorServicio {


    public AtencionPrioritaria(ElementoServicio elemento){
        super(elemento);
    }

    @Override
    public String getDescripcion(){
        return  elemento.getDescripcion() + " + Atencion prioritaria";
    }

    @Override
    public double calcularCosto(){
        return elemento.calcularCosto() + 35000;
    }



}
