package br.com.elo7.sonda.candidato.persistence;

import br.com.elo7.sonda.candidato.model.Probe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Probes extends CrudRepository<Probe, Long> {

    Probe save(Probe probe);

    Optional<Probe> findById(int id);

}
