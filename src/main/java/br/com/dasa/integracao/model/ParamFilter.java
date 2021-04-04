package br.com.dasa.integracao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamFilter {

	private Integer id; 
	
	private Integer idMarca;
	
	private Integer idProduto;
	
	private String q; 
	
	private String idsEspecialidade;
	
	private String idsSubEspecialidade;
	
	private String incluirSuspenso; 
	
	private String exibirSomenteInativos; 
	
	private String idCategoria;
	
	private String utilizarVolumetria;
	
	private Integer idCliente;
	
	private String variaveis;
	
	private Integer idSistema;
	
	private Integer produtos;
}
