package org.example.parcial2.model;

import java.util.ArrayList;

public class CatalogoServicios {
    private final ArrayList<ServicioTecnologico> servicios = new ArrayList<>();
    private final ArrayList<ElementoServicio> serviciosPersonalizados = new ArrayList<>();


    public void agregar(ServicioTecnologico servicio){
        servicios.add(servicio);
    }


    public ArrayList<ServicioTecnologico>consultarServicios(){
        return new ArrayList<>(servicios);
    }


    public void agregarPersonalizado(ElementoServicio personalizado) {
        serviciosPersonalizados.add(personalizado);
    }

    public ArrayList<ElementoServicio> consultarPersonalizados() {
        return new ArrayList<>(serviciosPersonalizados);
    }
}
