package br.com.elo7.sonda.candidato.persistence;

import br.com.elo7.sonda.candidato.model.Planet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Planets extends CrudRepository<Planet, Long> {

    Planet save(Planet planet);

    Optional<Planet> findById(int id);

}
