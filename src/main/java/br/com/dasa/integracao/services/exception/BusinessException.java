package br.com.dasa.integracao.services.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6505289396034335937L;

	private final String code;

	private final HttpStatus status;
	
	private final String message;

}
