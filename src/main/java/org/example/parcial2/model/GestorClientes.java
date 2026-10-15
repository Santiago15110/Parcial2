package org.example.parcial2.model;

import java.util.ArrayList;

public class GestorClientes {

    private ArrayList<Cliente> clientes = new ArrayList<>();

    public Cliente registrarCliente(String documento, String nombre, String correo){

        if(documento == null || documento.isBlank() || nombre.isBlank()|| nombre ==null ||correo.isBlank() || correo ==null){
            throw new IllegalArgumentException("Todos los datos del cliente son obligatorios");

        }
        if(buscarCliente(documento) !=null){
            throw new IllegalArgumentException("Ya existe un cliente con ese documento");
        }

        Cliente cliente = new Cliente(documento, nombre, correo);
        clientes.add(cliente);
        return cliente;
    }


    public Cliente buscarCliente(String documento){
        for(Cliente c: clientes){
            if(c.getDocumento().equals(documento)){
                return c;
            }
        }
        return null;
    }


}
