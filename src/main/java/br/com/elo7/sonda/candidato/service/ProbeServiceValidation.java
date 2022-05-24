package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.controller.request.PlanetWithProbeRequest;
import br.com.elo7.sonda.candidato.controller.request.ProbeRequest;
import br.com.elo7.sonda.candidato.enuns.CommandEnum;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.exceptions.ServiceException;
import br.com.elo7.sonda.candidato.exceptions.ValidationError;
import br.com.elo7.sonda.candidato.model.Planet;
import br.com.elo7.sonda.candidato.model.Probe;
import br.com.elo7.sonda.candidato.persistence.ProbeRepository;
import br.com.elo7.sonda.candidato.utils.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProbeServiceValidation {

    private ProbeRepository probeRepository;
    private MessageUtil messageUtil;

    public ProbeServiceValidation(ProbeRepository probeRepository, MessageUtil messageUtil) {
        this.probeRepository = probeRepository;
        this.messageUtil = messageUtil;
    }

    protected void validProbes(PlanetWithProbeRequest input) {
        if (input.getProbes().isEmpty())
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(),  messageUtil.getMessage("probe.label"), messageUtil.getMessage("probe.is_empty")));

        validateCapacityPlanet(input);

        validateIfInputDirectionEmpty(input);

        validateInputDirectionIsValid(input);

        validateIfInputCommandEmpty(input);

        validateInputCommandIsValid(input);
    }

    private void validateIfInputCommandEmpty(PlanetWithProbeRequest input) {
        if (input.getProbes().stream().filter(probe -> probe.getCommands() == null || probe.getCommands().isEmpty()).count() > 0)
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("command.label"), messageUtil.getMessage("command.required")));
    }

    private void validateIfInputDirectionEmpty(PlanetWithProbeRequest input) {
        if (input.getProbes().stream().filter(probe -> probe.getDirection() == null || probe.getDirection().isEmpty()).count() > 0)
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("direction.label"), messageUtil.getMessage("direction.required")));
    }

    protected void validateCapacityPlanet(PlanetWithProbeRequest planetWithProbeRequest) {
        if (planetWithProbeRequest.getPlanet() == null || (planetWithProbeRequest.getPlanet().getWidth() == 0 && planetWithProbeRequest.getPlanet().getHeight() == 0)){
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(),"xAxis and yAxis cannot be zero!", "xAxis and yAxis cannot be zero!"));
        }

        if (planetWithProbeRequest.getPlanet().getWidth() == 0){
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(),"xAxis cannot be zero!", "xAxis cannot be zero!"));
        }

        if (planetWithProbeRequest.getPlanet().getHeight() == 0){
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(),"yAxis cannot be zero!", "yAxis cannot be zero!"));
        }

        int capacityPlanet = planetWithProbeRequest.getPlanet().getWidth() * planetWithProbeRequest.getPlanet().getHeight();
        if (planetWithProbeRequest.getProbes().size() > capacityPlanet){
            throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(),"Excess planet capacity!", "Excess planet capacity! Maximum (" + capacityPlanet + " probe(s))"));
        }
    }

    protected void validateInputCommandIsValid(PlanetWithProbeRequest input) {
        List<String> listCommands = Stream.of(CommandEnum.values()).map(CommandEnum::getCommand).collect(Collectors.toList());
        for (ProbeRequest probeDTO : input.getProbes()){
            for (char command : probeDTO.getCommands().toCharArray()) {
                if (!listCommands.contains(String.valueOf(command))){
                    throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("command.label"), String.valueOf(command).concat(" ").concat(messageUtil.getMessage("command.invalid"))));
                }
            }
        }
    }

    protected void validateInputDirectionIsValid(PlanetWithProbeRequest input) {
        List<String> listDirections = Stream.of(DirectionEnum.values()).map(DirectionEnum::getDirection).collect(Collectors.toList());
        for (ProbeRequest probeDTO : input.getProbes()){
            if (!listDirections.contains(String.valueOf(probeDTO.getDirection()))){
                throw new ServiceException(new ValidationError(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage("direction.label"), probeDTO.getDirection().concat(" ").concat(messageUtil.getMessage("direction.invalid"))));
            }
        }
    }

    protected void validateCollisionBetweenProbes(List<Probe> probes, Planet planet) {
        if (this.isPlanetCreated(planet)){
            for (Probe probe : probes){
                Optional<Probe> optionalProbe = this.probeRepository.findByPlanetIdAndXAndYAndDirection(planet.getId(), probe.getX(), probe.getY(), probe.getDirection());
                if (optionalProbe.isPresent() || probe.getX() > planet.getWidth() || probe.getY() > planet.getHeight()){
                    probe.setIsCollision(Boolean.TRUE);
                }
            }
        }
    }

    private boolean isPlanetCreated(Planet planet) {
        return planet.getId() != null;
    }

}
