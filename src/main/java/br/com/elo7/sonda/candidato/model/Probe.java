package br.com.elo7.sonda.candidato.model;

import br.com.elo7.sonda.candidato.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "probe")
@Data
@EqualsAndHashCode(of = "id")
public class Probe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "x")
    private int x;
    @Column(name = "y")
    private int y;

    @Enumerated(EnumType.STRING)
    private DirectionEnum direction;

    @ManyToOne
    @JoinColumn(name = "id_planet")
    private Planet planet;

    public Probe(ProbeDTO probeDto, Planet planet) {
        this.planet = planet;
        this.x = probeDto.getX();
        this.y = probeDto.getY();
        this.direction = DirectionEnum.getDirectionEnum(probeDto.getDirection());
    }

    public Probe(DirectionEnum direction) {
        this.direction = direction;
    }

    public Probe(DirectionEnum direction, int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public void subtractAxisX() {
        this.x = this.x - 1;
    }

    public void subtractAxisY() {
        this.y = this.y - 1;
    }

    public void incrementAxisY() {
        this.y = this.y + 1;
    }

    public void incrementAxisX() {
        this.x = this.x + 1;
    }

    public void newDirection(DirectionEnum newDirection) {
        this.direction = newDirection;
    }

}
