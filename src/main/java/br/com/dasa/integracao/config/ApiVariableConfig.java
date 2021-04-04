package br.com.dasa.integracao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ApiVariableConfig {
	
	@Value("${url.api.exame.dp}")
	private String urlApiExameDp;

	@Value("${url.api.detail.dp}")
	private String urlApiDetailDp;
	
	@Value("${url.api.brand.dp}")
	private String urlApiBrandDp;
	
	@Value("${url.api.unity.by.product.dp}")
	private String urlApiUnityByProductDp;

	@Value("${url.api.unity.deadline.dp}")
	private String urlApiUnityDeadlineDp;

	@Value("${url.api.unity.variable.dp}")
	private String urlApiUnityVariableDp;

	@Value("${url.api.unity.price.dp}")
	private String urlApiUnityPriceDp;
	
	@Value("${url.header.apiKey}")
	private String apiKey;
	
	@Value("${param.idCliente}")
	private Integer idCliente;
	
	@Value("${param.idSistema}")
	private Integer idSistema;
	
}
