package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.controller.response.PlanetResponse;
import br.com.elo7.sonda.candidato.controller.request.PlanetaRequest;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.persistence.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanetService {

	private PlanetRepository planetRepository;

	public PlanetService(PlanetRepository planetRepository) {
		this.planetRepository = planetRepository;
	}

	public PlanetResponse createPlanet(PlanetaRequest planetaRequest) {
		Planet planet = this.beforePersist(planetaRequest);
		this.planetRepository.save(planet);
		return this.afterPersist(planet);
	}

	private PlanetResponse afterPersist(Planet planet) {
		return new PlanetResponse(planet);
	}

	private Planet beforePersist(PlanetaRequest planetaRequest) {
		return new Planet(planetaRequest);
	}

	public List<PlanetResponse> findByAll(){
		return this.planetRepository.findAll()
				.stream()
				.map(planet -> new PlanetResponse(planet))
				.collect(Collectors.toList());
	}

	public Planet findByWidthAndHeight(PlanetaRequest planet) {
		Optional<Planet> optionalPlanet = this.planetRepository.findByWidthAndHeight(planet.getWidth(), planet.getHeight());
		if (optionalPlanet.isPresent())
			return optionalPlanet.get();

		return new Planet(planet);
	}

	public void save(Planet planet) {
		if (planet.getId() == null)
			this.planetRepository.save(planet);
	}
}
