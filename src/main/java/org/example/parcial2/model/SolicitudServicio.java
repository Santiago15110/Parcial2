package org.example.parcial2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudServicio {
    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private List<ElementoServicio> elementos;
    private double costoTotal;
    private LocalDate fechaProgramada;
    private String observaciones;
    private String prioridad;
    private Tecnico tecnico;
    private boolean confirmada;

    /**
     * Método para crear una solicitud de servicio a partir del Builder
     * @param builder builder con los datos ya validados de la solicitud
     */
    private SolicitudServicio(Builder builder) {
        this.codigo = builder.codigo;
        this.fechaCreacion = LocalDate.now();
        this.cliente = builder.cliente;
        this.elementos = new ArrayList<>(builder.elementos);
        this.fechaProgramada = builder.fechaProgramada;
        this.observaciones = builder.observaciones;
        this.prioridad = builder.prioridad;
        this.tecnico = builder.tecnico;
        this.confirmada = false;
        calcularCostoTotal();
    }

    /**
     * Método para calcular el costo total sumando los elementos y el desplazamiento del técnico (si hay)
     * @return costo total de la solicitud
     */
    public double calcularCostoTotal() {
        double total = 0;
        for (ElementoServicio elemento : elementos) {
            total += elemento.calcularCosto();
        }
        if (tecnico != null) {
            total += tecnico.getCostoDesplazamiento();
        }
        costoTotal = total;
        return costoTotal;
    }

    /**
     * Método para confirmar la solicitud; si tiene técnico asignado, este debe estar disponible
     */
    public void confirmar() {
        if (tecnico != null && !tecnico.isDisponible()) {
            throw new IllegalStateException(
                    "No hay técnicos disponibles: la solicitud no puede confirmarse");
        }
        confirmada = true;
    }

    public String getCodigo() { return codigo; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public Cliente getCliente() { return cliente; }
    public List<ElementoServicio> getElementos() { return new ArrayList<>(elementos); }
    public double getCostoTotal() { return costoTotal; }
    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public String getObservaciones() { return observaciones; }
    public String getPrioridad() { return prioridad; }
    public Tecnico getTecnico() { return tecnico; }
    public boolean isConfirmada() { return confirmada; }

    /**
     * Clase del builder anidada
     */
    public static class Builder {

        private String codigo;
        private Cliente cliente;
        private List<ElementoServicio> elementos = new ArrayList<>();
        private LocalDate fechaProgramada;
        private String observaciones;
        private String prioridad;
        private Tecnico tecnico;

        /**
         * Método para asignar el código único de la solicitud
         * @param codigo código único de la solicitud
         * @return el mismo builder
         */
        public Builder conCodigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        /**
         * Método para asignar el cliente de la solicitud
         * @param cliente cliente que realiza la solicitud
         * @return el mismo builder
         */
        public Builder conCliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        /**
         * Método para agregar un servicio, servicio personalizado o paquete a la solicitud
         * @param elemento elemento contratado
         * @return el mismo builder
         */
        public Builder agregarElemento(ElementoServicio elemento) {
            this.elementos.add(elemento);
            return this;
        }

        /**
         * Método para asignar la fecha programada de atención (opcional)
         * @param fecha fecha programada
         * @return el mismo builder
         */
        public Builder conFechaProgramada(LocalDate fecha) {
            this.fechaProgramada = fecha;
            return this;
        }

        /**
         * Método para asignar las observaciones técnicas (opcional)
         * @param observaciones observaciones técnicas
         * @return el mismo builder
         */
        public Builder conObservaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        /**
         * Método para asignar el nivel de prioridad (opcional)
         * @param prioridad nivel de prioridad
         * @return el mismo builder
         */
        public Builder conPrioridad(String prioridad) {
            this.prioridad = prioridad;
            return this;
        }

        /**
         * Método para asignar el técnico consultado cuando la atención es presencial (opcional)
         * @param tecnico técnico obtenido de la consulta a ProveedorTecnico
         * @return el mismo builder
         */
        public Builder conTecnico(Tecnico tecnico) {
            this.tecnico = tecnico;
            return this;
        }

        /**
         * Método para construir la solicitud validando la información obligatoria
         * @return solicitud de servicio creada
         */
        public SolicitudServicio build() {
            if (codigo == null || codigo.isBlank()) {
                throw new IllegalStateException("El código es obligatorio");
            }
            if (cliente == null) {
                throw new IllegalStateException("El cliente es obligatorio");
            }
            if (elementos.isEmpty()) {
                throw new IllegalStateException("Debe incluir al menos un servicio o paquete");
            }
            return new SolicitudServicio(this);
        }
    }
}
