package br.com.dasa.integracao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class MedincDto implements Serializable {

	private static final long serialVersionUID = -3162554393554152640L;

	private String id;
	
	private String descricao;
}
