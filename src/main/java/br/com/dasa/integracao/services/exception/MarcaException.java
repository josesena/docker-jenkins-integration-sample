package br.com.dasa.integracao.services.exception;

import org.springframework.http.HttpStatus;

public class MarcaException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4759139874869888846L;

	public MarcaException(String code, HttpStatus status, String message) {
		super(code, status, message);
	}
}
