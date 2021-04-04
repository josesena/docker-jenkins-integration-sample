package br.com.dasa.integracao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class UnidadeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 156961109417823354L;

	private Integer id;

	private String nome;

	private String preco;

	private Integer prazoHora;
	
	private Integer prazoHoraUrgente;

	private boolean permitePrazoUrgente;
	
	private EnderecoDto endereco;
	
	
	public void setPreco(BigDecimal preco) {
		this.preco = NumberFormat.getCurrencyInstance().format(preco);
	}
}
