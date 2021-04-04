package br.com.dasa.integracao.services.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ExameException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -619334324983897719L;
	
	public ExameException(String code, HttpStatus status, String message) {
		super(code, status, message);
	}
}
