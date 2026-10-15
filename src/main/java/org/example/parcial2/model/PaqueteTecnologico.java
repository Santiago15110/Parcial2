package org.example.parcial2.model;

import java.util.ArrayList;
import java.util.List;

public class PaqueteTecnologico implements ElementoServicio {
    private String codigo;
    private String nombre;
    private List<ElementoServicio> elementos;

    /**
     * Método para crear un paquete tecnológico vacío
     * @param codigo código del paquete
     * @param nombre nombre del paquete
     */
    public PaqueteTecnologico(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.elementos = new ArrayList<>();
    }

    /**
     * Método para agregar un elemento al paquete, impidiendo que el paquete
     * quede incluido directa o indirectamente dentro de sí mismo
     * @param elemento servicio, servicio personalizado u otro paquete
     * @return true si se agregó, false si es nulo o generaría un ciclo
     */
    public boolean agregar(ElementoServicio elemento) {
        if (elemento == null) {
            return false;
        }
        if (elemento == this) {
            return false;
        }
        if (elemento instanceof PaqueteTecnologico) {
            PaqueteTecnologico otro = (PaqueteTecnologico) elemento;
            if (otro.contiene(this)) {
                return false;
            }
        }
        elementos.add(elemento);
        return true;
    }

    /**
     * Método para verificar si un elemento está dentro del paquete en cualquier nivel
     * @param buscado elemento que se quiere encontrar
     * @return true si el elemento está en este paquete o en alguno de sus subpaquetes
     */
    public boolean contiene(ElementoServicio buscado) {
        for (ElementoServicio elemento : elementos) {
            if (elemento == buscado) {
                return true;
            }
            if (elemento instanceof PaqueteTecnologico) {
                PaqueteTecnologico subpaquete = (PaqueteTecnologico) elemento;
                if (subpaquete.contiene(buscado)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método para calcular el costo total del paquete sumando todos sus componentes
     * @return costo total del paquete
     */
    @Override
    public double calcularCosto() {
        double total = 0;
        for (ElementoServicio elemento : elementos) {
            total += elemento.calcularCosto();
        }
        return total;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    @Override
    public String getDescripcion() {
        return "";
    }

    public List<ElementoServicio> getElementos() { return new ArrayList<>(elementos); }
}


