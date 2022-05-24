package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.aspect.LogRequest;
import br.com.elo7.sonda.candidato.controller.response.PlanetResponse;
import br.com.elo7.sonda.candidato.controller.request.PlanetaRequest;
import br.com.elo7.sonda.candidato.exceptions.StandardError;
import br.com.elo7.sonda.candidato.exceptions.ValidationError;
import br.com.elo7.sonda.candidato.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/planet")
@Tag(name = "/planet", description = "Criar planetas")
public class PlanetController {

    private PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @LogRequest
    @Operation(description = "API para criar um planeta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna OK para planeta e sonda criada.",
                    content = @Content(schema = @Schema(anyOf = {PlanetResponse.class}))),
            @ApiResponse(responseCode = "400", description = "Dados para criação inválidos.",
                    content = @Content(schema = @Schema(anyOf = {ValidationError.class}))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                    content = @Content(schema = @Schema(anyOf = {StandardError.class}))),
            @ApiResponse(responseCode = "500", description = "Erro do lado do servidor.",
                    content = @Content(schema = @Schema(anyOf = {StandardError.class}))),

    })
    @PostMapping
    public ResponseEntity<PlanetResponse> createPlanet(@RequestBody PlanetaRequest planetaRequest) {
        return new ResponseEntity<PlanetResponse>(planetService.createPlanet(planetaRequest), HttpStatus.CREATED);
    }

    @Operation(description = "API para buscar todos planetas já criados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                    content = @Content(schema = @Schema(anyOf = {StandardError.class}))),
            @ApiResponse(responseCode = "500", description = "Erro do lado do servidor.",
                    content = @Content(schema = @Schema(anyOf = {StandardError.class}))),

    })
    @GetMapping
    public ResponseEntity<List<PlanetResponse>> allPlanet() {
        return ResponseEntity.ok(planetService.findByAll());
    }

}
