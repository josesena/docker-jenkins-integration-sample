package br.com.dasa.integracao.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class ExameDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8195290576109800269L;

	private Integer id;
	
	private String nome;
	
	private List<String> sinonimias;
	
	private CategoriaDto categoria;
}
