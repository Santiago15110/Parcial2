package org.example.parcial2.model;

import java.util.ArrayList;

public class GestorSolicitudes {

    private final ArrayList<SolicitudServicio> solicitudes = new ArrayList<>();

    public ArrayList<SolicitudServicio> consultarSolicitudes() {
        return new ArrayList<>(solicitudes);
    }
}