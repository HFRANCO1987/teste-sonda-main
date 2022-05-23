package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.dto.InputDTO;
import br.com.elo7.sonda.candidato.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.enuns.CommandEnum;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.exceptions.ServiceException;
import br.com.elo7.sonda.candidato.exceptions.ValidationError;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.model.Probe;
import br.com.elo7.sonda.candidato.persistence.Planets;
import br.com.elo7.sonda.candidato.persistence.Probes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProbeService {

	private Planets planets;
	private Probes probes;

	public ProbeService(Planets planets, Probes probes) {
		this.planets = planets;
		this.probes = probes;
	}

	/***
	 * TODO - PENDENCIAS
	 * I - Valida tamanho da sonda;
	 * II - Valida possível colisão devido ao preenchimento da posição
	 * III - Valida informações de direção e rotas inexistentes ou invalidas;
	 *
	 *
	 * @param input
	 * @return
	 */
	public List<Probe> landProbes(InputDTO input) {
		this.validProbes(input);

		Planet planet = new Planet(input);
		planets.save(planet);
		
		List<Probe> convertedProbes = convertAndMoveProbes(input, planet);
		convertedProbes.forEach(probe -> probes.save(probe));
		
		return convertedProbes;
	}

	private void validProbes(InputDTO input) {
		if (input.getProbes().isEmpty())
			throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), "Probe", "Nenhum Probe informada para posicionamento!"));

		validateInputDirection(input);

		validateInputCommand(input);
	}

	private void validateInputCommand(InputDTO input) {
		if (input.getProbes().stream().filter(probe -> probe.getCommands() == null || probe.getCommands().isEmpty()).count() > 0)
			throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), "Command", "Comando é um campo obrigatório!"));

		List<String> listCommands = Stream.of(CommandEnum.values()).map(CommandEnum::getCommand).collect(Collectors.toList());
		for (ProbeDTO probeDTO : input.getProbes()){
			for (char command : probeDTO.getCommands().toCharArray()) {
				if (!listCommands.contains(String.valueOf(command))){
					throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), "Command", command + " é um comando inválido"));
				}
			}
		}
	}

	private void validateInputDirection(InputDTO input) {
		if (input.getProbes().stream().filter(probe -> probe.getDirection() == null || probe.getDirection().isEmpty()).count() > 0)
			throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), "Direção", "Direção é um campo obrigatório!"));

		List<String> listDirections = Stream.of(DirectionEnum.values()).map(DirectionEnum::getDirection).collect(Collectors.toList());
		for (ProbeDTO probeDTO : input.getProbes()){
			if (!listDirections.contains(String.valueOf(probeDTO.getDirection()))){
				throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), "Direction", probeDTO.getDirection() + " é uma direção inválida"));
			}
		}
	}

	/**
	 * TODO - Escrever teste para o metodo de conversão e movimentaçao de sonda
	 * @param input
	 * @param planet
	 * @return
	 */
	private List<Probe> convertAndMoveProbes(InputDTO input, Planet planet) {
		return input.getProbes()
						.stream().map(probeDto -> {
							Probe probe = new Probe(probeDto, planet);
							moveProbeWithAllCommands(probe, probeDto);
							return probe;
						}).collect(Collectors.toList());
	}

	public void moveProbeWithAllCommands(Probe probe, ProbeDTO probeDTO) {
		for (char command : probeDTO.getCommands().toCharArray()) {
			CommandEnum.getCommandEnum(String.valueOf(command)).applyCommandToProbe(probe);
		}
	}

}
