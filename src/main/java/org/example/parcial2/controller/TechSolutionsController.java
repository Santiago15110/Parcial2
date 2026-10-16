package org.example.parcial2.controller;

import org.example.parcial2.model.*;

import java.util.ArrayList;

public class TechSolutionsController {

    private final TechSolutions techSolutions = new TechSolutions();
    private final ArrayList<PaqueteTecnologico> paquetes = new ArrayList<>();
    private final ProveedorTecnico proveedorTecnico = new LegacySupportAdapter(new LegacySupport());;

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

    public void guardarServicioPersonalizado(ElementoServicio personalizado) {
        techSolutions.getCatalogo().agregarPersonalizado(personalizado);
    }

    public ArrayList<ElementoServicio> consultarServiciosPersonalizados() {
        return techSolutions.getCatalogo().consultarPersonalizados();
    }

    public PaqueteTecnologico crearPaquete(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del paquete es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del paquete es obligatorio");
        }
        for (PaqueteTecnologico p : paquetes) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                throw new IllegalArgumentException("Ya existe un paquete con ese código");
            }
        }
        PaqueteTecnologico paquete = new PaqueteTecnologico(codigo.trim(), nombre.trim());
        paquetes.add(paquete);
        return paquete;
    }

    public void agregarElementoAPaquete(PaqueteTecnologico paquete, ElementoServicio elemento) {
        if (!paquete.agregar(elemento)) {
            throw new IllegalArgumentException(
                    "No se pudo agregar: el elemento es nulo o el paquete quedaría incluido dentro de sí mismo");
        }
    }

    public ArrayList<PaqueteTecnologico> consultarPaquetes() {
        return new ArrayList<>(paquetes);
    }

    public SolicitudServicio registrarSolicitud(SolicitudServicio.Builder builder) {
        return techSolutions.getSolicitudes().registrar(builder);
    }

    public ArrayList<SolicitudServicio> consultarSolicitudes() {
        return techSolutions.getSolicitudes().consultarSolicitudes();
    }

    public ArrayList<ElementoServicio> consultarElementosContratables() {
        ArrayList<ElementoServicio> lista = new ArrayList<>(consultarServicios());
        lista.addAll(consultarServiciosPersonalizados());
        lista.addAll(paquetes);
        return lista;
    }

    public Tecnico consultarTecnico(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            throw new IllegalArgumentException("La especialidad es obligatoria");
        }
        try {
            return proveedorTecnico.consultarTecnico(especialidad);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalStateException("LegacySupport devolvió una respuesta inválida");
        }
    }
}