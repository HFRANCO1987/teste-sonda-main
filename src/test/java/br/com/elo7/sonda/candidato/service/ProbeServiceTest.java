package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.enuns.DirectionEnum;
import br.com.elo7.sonda.candidato.model.Probe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProbeServiceTest {
	
	@Autowired
	private ProbeService subject;
	
	@Test
	public void should_change_probe_direction_from_N_To_W_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.NORTH);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("L"));
		assertEquals('W', probe.getDirection().getDirection());
	}
	
	@Test
	public void should_change_probe_direction_from_W_To_S_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.WEST);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("L"));
		assertEquals('S', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_S_To_E_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.SOUTH);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("L"));
		assertEquals('E', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_E_To_N_when_receive_the_command_L() {
		Probe probe = new Probe(DirectionEnum.EAST);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("L"));
		assertEquals('N', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_N_To_E_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.NORTH);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("R"));
		assertEquals('E', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_E_To_S_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.EAST);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("R"));
		assertEquals('S', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_S_To_W_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.SOUTH);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("R"));
		assertEquals('W', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_direction_from_W_To_N_when_receive_the_command_R() {
		Probe probe = new Probe(DirectionEnum.WEST);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("R"));
		assertEquals('N', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_N_To_1_2_N_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.NORTH, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("M"));
		assertEquals(2, probe.getY());
		assertEquals(1, probe.getX());
		assertEquals('N', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_S_To_1_0_S_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.SOUTH, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("M"));
		assertEquals(0, probe.getY());
		assertEquals(1, probe.getX());
		assertEquals('S', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_W_To_0_1_W_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.WEST, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("M"));
		assertEquals(0, probe.getX());
		assertEquals(1, probe.getY());
		assertEquals('W', probe.getDirection().getDirection());
	}

	@Test
	public void should_change_probe_position_from_1_1_E_To_2_1_E_when_receive_the_command_M() {
		Probe probe = new Probe(DirectionEnum.EAST, 1,1);
		subject.moveProbeWithAllCommands(probe, new ProbeDTO("M"));
		assertEquals(2, probe.getX());
		assertEquals(1, probe.getY());
		assertEquals('E', probe.getDirection().getDirection());
	}
}
