package br.com.dasa.integracao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class LoincDto implements Serializable {

	private static final long serialVersionUID = -3302869371894023825L;

	private String id;
}
