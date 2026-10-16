package org.example.parcial2.model;

public class TechSolutions {

    private final CatalogoServicios catalogo = new CatalogoServicios();
    private final GestorClientes clientes = new GestorClientes();
    private final GestorSolicitudes solicitudes = new GestorSolicitudes();


    public TechSolutions(){
        cargarDatosIniciales();
    }

    public void cargarDatosIniciales(){

        clientes.registrarCliente("1031", "David", "David@hotmail.com");
        clientes.registrarCliente("1060", "Santiago", "Santi123@gmail.com");

        catalogo.agregar(new FactoryMantenimientoPreventivo().crearServicio("S-001", "Mantenimiento preventivo", "Revisión y limpieza de equipos"));
        catalogo.agregar(new FactoryMantenimientoCorrectivo().crearServicio("S-002", "Mantenimiento correctivo", "Reparación de fallas en equipos"));
        catalogo.agregar(new FactoryInstalacionRedes().crearServicio("S-003", "Instalación de redes", "Instalación y configuración de redes"));
        catalogo.agregar(new FactoryRecuperacionInformacion().crearServicio("S-004", "Recuperación de información", "Recuperación de datos perdidos"));
    }

    public CatalogoServicios getCatalogo() { return catalogo; }

    public GestorClientes getClientes() { return clientes; }

    public GestorSolicitudes getSolicitudes() { return solicitudes; }
}