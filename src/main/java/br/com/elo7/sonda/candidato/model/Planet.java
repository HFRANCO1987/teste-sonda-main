package br.com.elo7.sonda.candidato.model;

import br.com.elo7.sonda.candidato.controller.request.PlanetaRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "planet")
@Data
@EqualsAndHashCode(of = "id")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;
    public Planet() {
    }
    public Planet(PlanetaRequest planetaRequest) {
        this.width = planetaRequest.getWidth();
        this.height = planetaRequest.getHeight();
    }

}
