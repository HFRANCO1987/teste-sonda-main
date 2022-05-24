package br.com.elo7.sonda.candidato.persistence;

import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.model.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {

    Probe save(Probe probe);

    Optional<Probe> findByPlanetIdAndXAndYAndDirection(Long idPlanet, int x, int y, DirectionEnum direction);

}
