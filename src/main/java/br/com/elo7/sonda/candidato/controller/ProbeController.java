package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.controller.request.PlanetWithProbeRequest;
import br.com.elo7.sonda.candidato.controller.response.ProbeResponse;
import br.com.elo7.sonda.candidato.exceptions.CollisionError;
import br.com.elo7.sonda.candidato.exceptions.StandardError;
import br.com.elo7.sonda.candidato.exceptions.ValidationError;
import br.com.elo7.sonda.candidato.service.ProbeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
@Tag(name = "/planet-with-probes", description = "Registrar planetas e sondas")
public class ProbeController {
    private ProbeService probeService;

    public ProbeController(ProbeService probeService) {
        this.probeService = probeService;
    }

    @Operation(description = "API para criar um planeta e um sonda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna OK para planeta e sonda criada.",
                    content = @Content(schema = @Schema(anyOf = {ProbeResponse.class}))),
            @ApiResponse(responseCode = "400", description = "Dados para criação inválidos.",
                    content = @Content(schema = @Schema(anyOf = {ValidationError.class}))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                    content = @Content(schema = @Schema(anyOf = {CollisionError.class}))),
            @ApiResponse(responseCode = "409", description = "Colisão entre sondas.",
                    content = @Content(schema = @Schema(anyOf = {StandardError.class}))),
            @ApiResponse(responseCode = "500", description = "Erro do lado do servidor.",
                    content = @Content(schema = @Schema(anyOf = {StandardError.class}))),

    })
    @PostMapping
    public ResponseEntity<List<ProbeResponse>> register(@RequestBody PlanetWithProbeRequest planetWithProbeRequest) {
        return new ResponseEntity<>(probeService.landProbes(planetWithProbeRequest), HttpStatus.CREATED);
    }

}
