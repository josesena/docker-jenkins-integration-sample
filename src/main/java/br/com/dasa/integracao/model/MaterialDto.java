package br.com.dasa.integracao.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MaterialDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 31603628355170183L;

	private Integer id;
	
	private String nome;
	
}
