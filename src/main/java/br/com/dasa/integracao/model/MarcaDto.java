package br.com.dasa.integracao.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class MarcaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5029416948111956477L;

	private Integer id;
	
	private String mnemonico; 
	
	private String nome; 
	
	private String logotipo; 
	
	private String segmento; 
	
	private String url; 
	
	private String whatsapp;  
	
	private String urlAgendamento; 
	
	private String urlLaudo;
	
	private List<String> ufs;
}
