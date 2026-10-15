package org.example.parcial2.model;

public abstract class DecoradorServicio implements ElementoServicio {

    protected final ElementoServicio elemento;

    public DecoradorServicio(ElementoServicio elemento){
        this.elemento=elemento;
    }


    @Override
    public String getNombre(){
        return elemento.getNombre();
    }


}