package org.example.parcial2.model;

public class SoporteTecnico extends DecoradorServicio {

    /**
     * Metodo constructor
     * @param elementoServicio
     */
    public SoporteTecnico(ElementoServicio elementoServicio){
        super(elementoServicio);
    }

    /**
     * metodo de obtener nueva descripcion para el servicioTecnologico
     * @return
     */
    @Override
    public String getDescripcion(){
        return elemento.getDescripcion() + " + Soporte tecnico";

    }


    /**
     *Metodo de calcular el costo al agregar un soporte tecnico
     * @return
     */
    @Override
    public double calcularCosto(){
        return elemento.calcularCosto() + 50000;
    }
}
