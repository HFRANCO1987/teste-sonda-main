package br.com.elo7.sonda.candidato.exceptions;

import br.com.elo7.sonda.candidato.controller.response.ProbeResponse;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class CollisionError extends StandardError {

	private List<ProbeResponse> errors = new ArrayList<>();

	public CollisionError(List<ProbeResponse> errors) {
		super(HttpStatus.CONFLICT.value(), "Collision when placing probe!");
		this.errors = errors;
	}

	public List<ProbeResponse> getErrors() {
		return errors;
	}

}
