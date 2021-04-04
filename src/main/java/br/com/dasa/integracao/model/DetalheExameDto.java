package br.com.dasa.integracao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class DetalheExameDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190884009572702569L;

	private Integer id;

	private String nome;

	private CategoriaDto categoria;

	private List<String> sinonimias;

	private MaterialDto material;
	
	private String codTuss;
	
	private String descricaoTuss;
	
	private List<String> grupoTuss;
	
	@JsonProperty(value = "medinc")
	private MedincDto codigoMedinc;
	
	@JsonProperty(value = "loinc")
	private LoincDto codigoLoinc;
	
	private List<PreparoDto> preparos;
	
	private EspecialidadeDto subEspecialidade;
	
	private EspecialidadeDto especialidade;

	@JsonIgnore
	private List<Integer> idMarcas;

	private List<MarcaDto> marcasAtivas;

	private List<MarcaDto> marcasInativas;
	
	private List<ErrorDto> errors = new ArrayList<>();

}
