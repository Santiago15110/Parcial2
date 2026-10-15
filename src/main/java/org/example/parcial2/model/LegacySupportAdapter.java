package org.example.parcial2.model;

import java.util.HashMap;
import java.util.Map;

public class LegacySupportAdapter implements ProveedorTecnico{

    private LegacySupport legacySupport;
    private Map<String, Integer> codigosEspecialidad;

    /**
     * Método para crear el adaptador de la plataforma LegacySupport
     * @param legacySupport plataforma externa que se va a adaptar
     */
    public LegacySupportAdapter(LegacySupport legacySupport) {
        this.legacySupport = legacySupport;
        this.codigosEspecialidad = new HashMap<>();
        // Valores de ejemplo: ajústalos a las especialidades reales
        codigosEspecialidad.put("Redes", 105);
        codigosEspecialidad.put("Mantenimiento", 101);
        codigosEspecialidad.put("Recuperacion de informacion", 102);
        codigosEspecialidad.put("Seguridad informatica", 103);
    }

    /**
     * Método para consultar un técnico traduciendo la especialidad al código de LegacySupport
     * @param especialidad nombre de la especialidad requerida
     * @return técnico con su identificador, disponibilidad y costo de desplazamiento
     */
    @Override
    public Tecnico consultarTecnico(String especialidad) {
        Integer codigo = codigosEspecialidad.get(especialidad);
        if (codigo == null) {
            throw new IllegalArgumentException("Especialidad no reconocida: " + especialidad);
        }
        String respuesta = legacySupport.consultar(codigo);
        return parsear(respuesta);
    }

    /**
     * Método para convertir la cadena de LegacySupport en un objeto Tecnico
     * @param respuesta cadena con el formato "ID|ESTADO|COSTO"
     * @return técnico con la información estructurada
     */
    private Tecnico parsear(String respuesta) {
        String[] partes = respuesta.split("\\|");
        String id = partes[0];
        boolean disponible = partes[1].equals("DISPONIBLE");
        double costo = Double.parseDouble(partes[2]);
        return new Tecnico(id, disponible, costo);
    }
}


