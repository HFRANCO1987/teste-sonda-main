package br.com.elo7.sonda.candidato.model;

import br.com.elo7.sonda.candidato.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;

public class Probe {
	private int id;
	private int x;
	private int y;
	private DirectionEnum direction;
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DirectionEnum getDirection() {
		return direction;
	}
	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}
	public Planet getPlanet() {
		return planet;
	}
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

    public void subtractAxisX() {
		this.x = this.x - 1;
    }

	public void subtractAxisY() {
		this.y = this.y - 1 ;
	}

	public void incrementAxisY() {
		this.y = this.y + 1;
	}

	public void incrementAxisX() {
		this.x =  this.x + 1;
	}

	public void newDirection(DirectionEnum newDirection){
		this.direction = newDirection;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
