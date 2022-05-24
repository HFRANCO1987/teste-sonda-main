package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.controller.request.PlanetWithProbeRequest;
import br.com.elo7.sonda.candidato.controller.request.ProbeRequest;
import br.com.elo7.sonda.candidato.controller.response.ProbeResponse;
import br.com.elo7.sonda.candidato.enuns.CommandEnum;
import br.com.elo7.sonda.candidato.exceptions.CollisionError;
import br.com.elo7.sonda.candidato.exceptions.ServiceException;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.model.Probe;
import br.com.elo7.sonda.candidato.persistence.ProbeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbeService {

	private PlanetService planetService;
	private ProbeRepository probeRepository;

	private ProbeValidation probeValidation;

	public ProbeService(PlanetService planetService, ProbeRepository probeRepository, ProbeValidation probeValidation) {
		this.planetService = planetService;
		this.probeRepository = probeRepository;
		this.probeValidation = probeValidation;
	}

	public List<ProbeResponse> landProbes(PlanetWithProbeRequest planetWithProbeRequest) {
		this.probeValidation.validProbes(planetWithProbeRequest);

		Planet planet = this.planetService.findByWidthAndHeight(planetWithProbeRequest.getPlanet());
		this.planetService.save(planet);

		List<Probe> convertedProbes = this.convertAndMoveProbes(planetWithProbeRequest.getProbes(), planet);

		this.probeValidation.validateCollisionBetweenProbes(convertedProbes, planet);

		convertedProbes.stream().filter(probe -> !probe.getIsCollision()).forEach(probe -> this.probeRepository.save(probe));
		
		return this.toProbeInProbeResponse(convertedProbes, planet);
	}

	private List<ProbeResponse> toProbeInProbeResponse(List<Probe> convertedProbes, Planet planet) {
		List<ProbeResponse> allProbeResponse = new ArrayList<>();
		convertedProbes.forEach(probe -> {
			if (probe.getIsCollision()){
				allProbeResponse.add(new ProbeResponse(probe, planet,
						"Probe in collision in planet " + planet + " in axisX : " + probe.getX() + " and axisY :" + probe.getY()));
			}else{
				allProbeResponse.add(new ProbeResponse(probe, "Successful landing!"));
			}
		});

		if (convertedProbes.stream().filter(probe -> probe.getIsCollision()).count() >= 1){
			throw new ServiceException(new CollisionError(allProbeResponse));
		}

		return allProbeResponse;
	}

	private List<Probe> convertAndMoveProbes(List<ProbeRequest> probes, Planet planet) {
		return probes
				.stream()
				.map(probeDto -> {
					Probe probe = new Probe(probeDto, planet);
					moveProbeWithAllCommands(probe, probeDto);
					return probe;
				}).collect(Collectors.toList());
	}

	public void moveProbeWithAllCommands(Probe probe, ProbeRequest probeRequest) {
		for (char command : probeRequest.getCommands().toCharArray()) {
			CommandEnum.getCommandEnum(String.valueOf(command)).applyCommandToProbe(probe);
		}
	}

}
