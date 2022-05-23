package br.com.elo7.sonda.candidato.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class InputDataRequest {

	private PlanetaRequest planet;
	private List<ProbeRequest> probes;

}
