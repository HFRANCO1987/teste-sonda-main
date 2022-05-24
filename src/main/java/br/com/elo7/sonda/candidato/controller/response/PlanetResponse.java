package br.com.elo7.sonda.candidato.controller.response;

import br.com.elo7.sonda.candidato.model.Planet;
import lombok.Data;

@Data
public class PlanetResponse {

    private Long id;
    private int width;
    private int height;
    public PlanetResponse(Planet planet) {
        this.id = planet.getId();
        this.width = planet.getWidth();
        this.height = planet.getHeight();
    }

}
