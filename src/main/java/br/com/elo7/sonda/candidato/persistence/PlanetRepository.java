package br.com.elo7.sonda.candidato.persistence;

import br.com.elo7.sonda.candidato.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    Planet save(Planet planet);

    Optional<Planet> findByWidthAndHeight(int x, int y);

}
