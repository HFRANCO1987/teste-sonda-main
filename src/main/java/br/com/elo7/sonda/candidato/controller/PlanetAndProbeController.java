package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.dto.InputDTO;
import br.com.elo7.sonda.candidato.model.Probe;
import br.com.elo7.sonda.candidato.service.ProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/planet-with-probes")
public class PlanetAndProbeController {
	@Autowired
	private ProbeService probeService;

	/**
	 * TODO - Verificar possíveis falhas nas requisições e tratar status
	 * @param inputDto
	 * @return
	 */
	@PostMapping
    public ResponseEntity<List<Probe>> register(@RequestBody InputDTO inputDto) {
		return ResponseEntity.ok(probeService.landProbes(inputDto));        
    }

}
