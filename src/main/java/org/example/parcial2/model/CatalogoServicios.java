package org.example.parcial2.model;

import java.util.ArrayList;

public class CatalogoServicios {
    private final ArrayList<ServicioTecnologico> servicios = new ArrayList<>();
    private final ArrayList<ElementoServicio> serviciosPersonalizados = new ArrayList<>();


    public void agregar(ServicioTecnologico servicio){
        servicios.add(servicio);
    }


    /**
     * método para consultar un servicio tecnologico
     * @return
     */
    public ArrayList<ServicioTecnologico>consultarServicios(){
        return new ArrayList<>(servicios);
    }


    /**
     * metodo para agregar un servicio personalizado
     * @param personalizado
     */
    public void agregarPersonalizado(ElementoServicio personalizado) {
        serviciosPersonalizados.add(personalizado);
    }

    public ArrayList<ElementoServicio> consultarPersonalizados() {
        return new ArrayList<>(serviciosPersonalizados);
    }
}
