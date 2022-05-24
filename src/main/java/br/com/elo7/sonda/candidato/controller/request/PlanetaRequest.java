package br.com.elo7.sonda.candidato.controller.request;

import lombok.Data;

@Data
public class PlanetaRequest {

    private int width;
    private int height;

    public PlanetaRequest() {
    }

    public PlanetaRequest(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
