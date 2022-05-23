package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.controller.request.InputDataRequest;
import br.com.elo7.sonda.candidato.controller.request.ProbeRequest;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.exceptions.ServiceException;
import br.com.elo7.sonda.candidato.model.Probe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProbeServiceTest {
	
	@Autowired
	private ProbeService subject;

	@Test
	public void should_change_probe_direction_from_N_To_W_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.NORTH);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("L"));
		assertEquals("W", probe.getDirection().getDirection());
	}
	
	@Test
	public void should_change_probe_direction_from_W_To_S_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.WEST);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("L"));
		assertEquals("S", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_S_To_E_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.SOUTH);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("L"));
		assertEquals("E", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_E_To_N_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.EAST);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("L"));
		assertEquals("N", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_N_To_E_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.NORTH);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("R"));
		assertEquals("E", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_E_To_S_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.EAST);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("R"));
		assertEquals("S", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_S_To_W_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.SOUTH);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("R"));
		assertEquals("W", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_W_To_N_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.WEST);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("R"));
		assertEquals("N", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_N_To_1_2_N_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.NORTH, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("M"));
		assertEquals(2, probe.getY());
		assertEquals(1, probe.getX());
		assertEquals("N", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_S_To_1_0_S_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.SOUTH, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("M"));
		assertEquals(0, probe.getY());
		assertEquals(1, probe.getX());
		assertEquals("S", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_W_To_0_1_W_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.WEST, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("M"));
		assertEquals(0, probe.getX());
		assertEquals(1, probe.getY());
		assertEquals("W", probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_E_To_2_1_E_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.EAST, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeRequest("M"));
		assertEquals(2, probe.getX());
		assertEquals(1, probe.getY());
		assertEquals("E", probe.getDirection().getDirection());
	}

	@Test
	public void should_validate_if_probe_is_null() {
		InputDataRequest inputDTO = new InputDataRequest();
		try{
			subject.landProbes(inputDTO);
		}catch (NullPointerException e){
		}
	}

	@Test
	public void should_validate_if_probe_is_empty() {
		try{
			InputDataRequest inputDTO = new InputDataRequest();
			inputDTO.setProbes(new ArrayList<>());
			subject.landProbes(inputDTO);
		}catch (ServiceException serviceException){
			assertEquals("Nenhum Probe informada para posicionamento!", serviceException.getValidationError().getMsg());
		}
	}

	@Test
	public void deveValidarSeDirecaoDoProbeEstaVazio() {
		try{
			InputDataRequest inputDTO = new InputDataRequest();
			ProbeRequest probeDTO = new ProbeRequest();
			inputDTO.setProbes(new ArrayList<>());
			inputDTO.getProbes().add(probeDTO);
			subject.landProbes(inputDTO);
		}catch (ServiceException serviceException){
			assertEquals("Direção é um campo obrigatório!", serviceException.getValidationError().getMsg());
		}
	}

	@Test
	public void should_validate_if_direction_probe_is_invalid() {
		ProbeRequest probeDTO = new ProbeRequest();
		try{
			InputDataRequest inputDTO = new InputDataRequest();
			inputDTO.setProbes(new ArrayList<>());
			probeDTO.setDirection("H");
			inputDTO.getProbes().add(probeDTO);
			subject.landProbes(inputDTO);
		}catch (ServiceException serviceException){
			assertEquals(probeDTO.getDirection() + " é uma direção invalida!", serviceException.getValidationError().getMsg());
		}
	}

	@Test
	public void should_validate_if_command_probe_is_empty() {
		try{
			InputDataRequest inputDTO = new InputDataRequest();
			ProbeRequest probeDTO = new ProbeRequest();
			inputDTO.setProbes(new ArrayList<>());
			probeDTO.setDirection("N");
			inputDTO.getProbes().add(probeDTO);
			subject.landProbes(inputDTO);
		}catch (ServiceException serviceException){
			assertEquals("Comando é um campo obrigatório!", serviceException.getValidationError().getMsg());
		}
	}


	@Test
	public void should_validate_if_command_probe_is_invalid() {
		ProbeRequest probeDTO = new ProbeRequest();
		try{
			InputDataRequest inputDTO = new InputDataRequest();
			inputDTO.setProbes(new ArrayList<>());
			probeDTO.setDirection("N");
			probeDTO.setCommands("LMRMMH");
			inputDTO.getProbes().add(probeDTO);
			subject.landProbes(inputDTO);
		}catch (ServiceException serviceException){
			assertEquals("H" + " é um comando invalido!", serviceException.getValidationError().getMsg());
		}
	}
}
