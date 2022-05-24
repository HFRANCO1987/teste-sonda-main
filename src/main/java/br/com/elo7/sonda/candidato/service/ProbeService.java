package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.controller.request.InputDataRequest;
import br.com.elo7.sonda.candidato.controller.request.ProbeRequest;
import br.com.elo7.sonda.candidato.controller.response.ProbeResponse;
import br.com.elo7.sonda.candidato.enuns.CommandEnum;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.exceptions.CollisionError;
import br.com.elo7.sonda.candidato.exceptions.ServiceException;
import br.com.elo7.sonda.candidato.exceptions.ValidationError;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.model.Probe;
import br.com.elo7.sonda.candidato.persistence.Probes;
import br.com.elo7.sonda.candidato.utils.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProbeService {

	private PlanetService planetService;
	private Probes probes;

	private MessageUtil messageUtil;

	public ProbeService(PlanetService planetService, Probes probes, MessageUtil messageUtil) {
		this.planetService = planetService;
		this.probes = probes;
		this.messageUtil = messageUtil;
	}

	/***
	 * TODO - PENDENCIAS
	 * I - Valida tamanho da sonda;
	 * II - Valida possível colisão devido ao preenchimento da posição
	 *
	 * @param inputDatRequest
	 * @return
	 */
	public List<ProbeResponse> landProbes(InputDataRequest inputDatRequest) {
		this.validProbes(inputDatRequest);

		Planet planet = this.planetService.findByWidthAndHeight(inputDatRequest.getPlanet());
		this.planetService.save(planet);

		List<Probe> convertedProbes = convertAndMoveProbes(inputDatRequest.getProbes(), planet);

		this.validateCollisionBetweenProbes(convertedProbes, planet);

		convertedProbes.stream().filter(probe -> !probe.getIsCollision()).forEach(probe -> probes.save(probe));
		
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

	private void validateCollisionBetweenProbes(List<Probe> probes, Planet planet) {
		if (this.isPlanetCreated(planet)){
			for (Probe probe : probes){
				Probe probeExist = this.findByPlanetAndXAndYAndDirection(planet, probe.getX(), probe.getY(), probe.getDirection());
				if (probeExist != null){
					probe.setIsCollision(Boolean.TRUE);
				}
			}
		}
	}

	private boolean isPlanetCreated(Planet planet) {
		return planet.getId() != null;
	}

	private void validProbes(InputDataRequest input) {
		if (input.getProbes().isEmpty())
			throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(),  messageUtil.getMessage("probe.label"), messageUtil.getMessage("probe.is_empty")));

		validateInputDirection(input);

		validateInputCommand(input);
	}

	private void validateInputCommand(InputDataRequest input) {
		if (input.getProbes().stream().filter(probe -> probe.getCommands() == null || probe.getCommands().isEmpty()).count() > 0)
			throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("command.label"), messageUtil.getMessage("command.required")));

		List<String> listCommands = Stream.of(CommandEnum.values()).map(CommandEnum::getCommand).collect(Collectors.toList());
		for (ProbeRequest probeDTO : input.getProbes()){
			for (char command : probeDTO.getCommands().toCharArray()) {
				if (!listCommands.contains(String.valueOf(command))){
					throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("command.label"), String.valueOf(command).concat(" ").concat(messageUtil.getMessage("command.invalid"))));
				}
			}
		}
	}

	private void validateInputDirection(InputDataRequest input) {
		if (input.getProbes().stream().filter(probe -> probe.getDirection() == null || probe.getDirection().isEmpty()).count() > 0)
			throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("direction.label"), messageUtil.getMessage("direction.required")));

		List<String> listDirections = Stream.of(DirectionEnum.values()).map(DirectionEnum::getDirection).collect(Collectors.toList());
		for (ProbeRequest probeDTO : input.getProbes()){
			if (!listDirections.contains(String.valueOf(probeDTO.getDirection()))){
				throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("direction.label"), probeDTO.getDirection().concat(" ").concat(messageUtil.getMessage("direction.invalid"))));
			}
		}
	}

	/**
	 * TODO - Escrever teste para o metodo de conversão e movimentaçao de sonda
	 * @param input
	 * @param planet
	 * @return
	 */
	private List<Probe> convertAndMoveProbes(List<ProbeRequest> probes, Planet planet) {
		return probes
						.stream()
						.map(probeDto -> {
							Probe probe = new Probe(probeDto, planet);
							moveProbeWithAllCommands(probe, probeDto);
							return probe;
						}).collect(Collectors.toList());
	}

	public void moveProbeWithAllCommands(Probe probe, ProbeRequest probeDTO) {
		for (char command : probeDTO.getCommands().toCharArray()) {
			CommandEnum.getCommandEnum(String.valueOf(command)).applyCommandToProbe(probe);
		}
	}

	public Probe findByPlanetAndXAndYAndDirection(Planet planet, int x, int y, DirectionEnum direction){
		Optional<Probe> optionalProbe = this.probes.findByPlanetIdAndXAndYAndDirection(planet.getId(), x, y, direction);
		if (optionalProbe.isPresent())
			return optionalProbe.get();

		return null;
	}

}
