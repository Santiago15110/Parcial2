package org.example.parcial2.controller;

import org.example.parcial2.model.*;

import java.util.ArrayList;

public class TechSolutionsController {

    private final TechSolutions techSolutions = new TechSolutions();


    public Cliente registrarCliente(String documento, String nombreCompleto, String correo) {
        return techSolutions.getClientes().registrarCliente(documento, nombreCompleto, correo);
    }

    public Cliente buscarCliente(String documento) {
        return techSolutions.getClientes().buscarCliente(documento);
    }

    public ArrayList<Cliente> consultarClientes() {
        return techSolutions.getClientes().consultarClientes();
    }


    public ServicioTecnologico crearServicio(FactoryServicio fabrica, String codigo,
                                             String nombre, String descripcion) {
        ServicioTecnologico servicio = fabrica.crearServicio(codigo, nombre, descripcion);
        techSolutions.getCatalogo().agregar(servicio);
        return servicio;
    }

    public ArrayList<ServicioTecnologico> consultarServicios() {
        return techSolutions.getCatalogo().consultarServicios();
    }



    public ElementoServicio personalizarServicio(ServicioTecnologico base,
                                                 boolean prioritaria,
                                                 boolean soporte,
                                                 boolean domicilio) {
        ElementoServicio personalizado = base;
        if (prioritaria) personalizado = new AtencionPrioritaria(personalizado);
        if (soporte) personalizado = new SoporteTecnico(personalizado);
        if (domicilio) personalizado = new AtencionDomicilio(personalizado);
        return personalizado;
    }

}