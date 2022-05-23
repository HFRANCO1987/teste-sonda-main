package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.dto.InputDTO;
import br.com.elo7.sonda.candidato.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.enuns.CommandEnum;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.model.Probe;
import br.com.elo7.sonda.candidato.persistence.Planets;
import br.com.elo7.sonda.candidato.persistence.Probes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
		/**
		 * TODO - Verificar melhor forma de persistir
		 */
		convertedProbes.forEach(probe -> probes.save(probe));
		
		return convertedProbes;
	}

	private void validProbes(InputDTO input) {
		if (input.getProbes().isEmpty())
			throw new IllegalArgumentException("Nenhuma Probe informada para posicionamento");

		validateInputDirection(input);

		validateInputCommand(input);
	}

	//Escrever testes
	private void validateInputCommand(InputDTO input) {
		//implementar regras de validação
	}

	//Escrever testes
	private void validateInputDirection(InputDTO input) {
		//implementar regras de validação
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
