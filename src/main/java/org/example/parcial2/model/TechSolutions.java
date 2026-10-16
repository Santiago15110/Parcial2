package org.example.parcial2.model;

public class TechSolutions {

    private final CatalogoServicios catalogo = new CatalogoServicios();
    private final GestorClientes clientes = new GestorClientes();
    private final GestorSolicitudes solicitudes = new GestorSolicitudes();

    public CatalogoServicios getCatalogo() { return catalogo; }

    public GestorClientes getClientes() { return clientes; }

    public GestorSolicitudes getSolicitudes() { return solicitudes; }
}