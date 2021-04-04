package br.com.dasa.integracao.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoriaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5665053076673636455L;

	private Integer id;
	
	private String nome;
}
