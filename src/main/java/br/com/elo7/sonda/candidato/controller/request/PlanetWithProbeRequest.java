package br.com.elo7.sonda.candidato.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class PlanetWithProbeRequest {

	private PlanetaRequest planet;
	private List<ProbeRequest> probes;

}
