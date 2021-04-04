package br.com.dasa.integracao.services.exception;

import org.springframework.http.HttpStatus;

public class UnidadeException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 113759295194359614L;
	
	public UnidadeException(String codigo, HttpStatus status, String message) {
		super(codigo, status, message);
	}
}
