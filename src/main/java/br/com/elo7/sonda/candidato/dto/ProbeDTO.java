package br.com.elo7.sonda.candidato.dto;

public class ProbeDTO {
	private int x; 
	private int y;
	private String direction;
	private String commands;

	public ProbeDTO() {
	}

	public ProbeDTO(String commands) {
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
}
