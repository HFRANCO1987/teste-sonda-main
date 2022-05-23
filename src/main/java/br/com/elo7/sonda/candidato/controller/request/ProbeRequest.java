package br.com.elo7.sonda.candidato.controller.request;

import javax.persistence.Transient;

public class ProbeRequest {
	private int x; 
	private int y;
	private String direction;
	private String commands;

	@Transient
	private Boolean isCollision;

	public ProbeRequest() {
		this.isCollision = Boolean.FALSE;
	}

	public ProbeRequest(String commands) {
		this.commands = commands;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCommands() {
		return commands;
	}
	public void setCommands(String commands) {
		this.commands = commands;
	}

	public Boolean getCollision() {
		return isCollision;
	}

	public void setCollision(Boolean collision) {
		isCollision = collision;
	}
}
