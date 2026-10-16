package org.example.parcial2.model;

import java.util.ArrayList;

public class GestorSolicitudes {

    private final ArrayList<SolicitudServicio> solicitudes = new ArrayList<>();

    public ArrayList<SolicitudServicio> consultarSolicitudes() {
        return new ArrayList<>(solicitudes);
    }

    public SolicitudServicio registrar(SolicitudServicio.Builder builder) {
        SolicitudServicio solicitud = builder.build();
        if (buscarPorCodigo(solicitud.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una solicitud con ese código");
        }
        solicitudes.add(solicitud);
        return solicitud;
    }

    public SolicitudServicio buscarPorCodigo(String codigo) {
        for (SolicitudServicio s : solicitudes) {
            if (s.getCodigo().equals(codigo)) {
                return s;
            }
        }
        return null;
    }
}