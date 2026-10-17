package org.example.parcial2.model;

public class AtencionDomicilio extends DecoradorServicio{

    /**
     * Metodo constructor de atencion domicilio
     * @param elementoServicio
     */
    public AtencionDomicilio(ElementoServicio elementoServicio){
        super(elementoServicio);
    }



    @Override
    public String getDescripcion(){
        return elemento.getDescripcion() + " + Atencion de domicilio";
    }

    @Override
    public double calcularCosto(){
        return elemento.calcularCosto() + 25000;
    }
}
