package br.com.dasa.integracao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Data
public class UnidadeConsolidadoDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4611575641823797055L;

	private Integer idProduto;

	private List<UnidadeDto> unidades;
	
	private List<ErrorDto> errors = new ArrayList<>();

	public UnidadeConsolidadoDto(List<UnidadeDto> list) {
		this.unidades = list;
	}
}
