package br.com.elo7.sonda.candidato.controller.response;

import br.com.elo7.sonda.candidato.controller.request.ProbeRequest;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.model.Probe;
import lombok.Data;

@Data
public class ProbeResponse {

    private Long id;
    private int x;
    private int y;
    private DirectionEnum direction;
    private Long idPlanet;
    private String situacao;

    public ProbeResponse(Probe probe, String situacao) {
        this.id = probe.getId();
        this.x = probe.getX();
        this.y = probe.getY();
        this.direction = probe.getDirection();
        this.idPlanet = probe.getPlanet().getId();
        this.situacao = situacao;
    }

    public ProbeResponse(Probe probe, Planet planet, String situacao) {
        this.x = probe.getX();
        this.y = probe.getY();
        this.direction = probe.getDirection();
        this.idPlanet = planet.getId();
        this.situacao = situacao;
    }

}
