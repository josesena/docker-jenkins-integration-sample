package br.com.dasa.integracao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class EnderecoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4025682196990865526L;
	
	private Integer id;

	private String cidade;
	
	private String uf;
	
	private String logradouro;
	
	private String numeroComercial;
	
	private String complemento;
	
	private String bairro;
	
	private String pontoReferencia;
	
	private String cep;
	
	private String regiao;
	
}
