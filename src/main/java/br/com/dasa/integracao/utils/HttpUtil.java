package br.com.dasa.integracao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dasa.integracao.config.ApiVariableConfig;
import br.com.dasa.integracao.model.ParamFilter;

@Component
public class HttpUtil {
	
	@Autowired
	private ApiVariableConfig appConfig;
	
	public String prepareParamUri(ParamFilter param, String url) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if(param.getId() != null)
			builder.replaceQueryParam("id", param.getId());
		if (param.getQ() != null)
			builder.replaceQueryParam("q", param.getQ());
		if (param.getIdMarca() != null)
			builder.replaceQueryParam("idMarca", param.getIdMarca());
		if (param.getIdsEspecialidade() != null)
			builder.replaceQueryParam("idsEspecialidade", param.getIdsEspecialidade());
		if (param.getIdsSubEspecialidade() != null)
			builder.replaceQueryParam("idsSubEspecialidade", param.getIdsSubEspecialidade());
		if (param.getUtilizarVolumetria() != null)
			builder.replaceQueryParam("utilizarVolumetria", param.getUtilizarVolumetria());
		if (param.getIncluirSuspenso() != null)
			builder.replaceQueryParam("incluirSuspenso", param.getIncluirSuspenso());
		if (param.getExibirSomenteInativos() != null)
			builder.replaceQueryParam("exibirSomenteInativos", param.getExibirSomenteInativos());
		if (param.getIdCategoria() != null)
			builder.replaceQueryParam("idCategoria", param.getIdCategoria());
		if(param.getIdCliente() != null)
			builder.replaceQueryParam("idCliente", param.getIdCliente());
		if(param.getVariaveis() != null)
			builder.replaceQueryParam("variaveis", param.getVariaveis());
		if(param.getIdProduto() != null) 
			builder.replaceQueryParam("idProduto", param.getIdProduto());
		if(param.getIdSistema() != null)
			builder.replaceQueryParam("idSistema", param.getIdSistema());
		if(param.getProdutos() != null)
			builder.replaceQueryParam("produtos", param.getProdutos());

		return builder.toUriString();
	}
	
	
	public HttpEntity<String> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("apiKey", appConfig.getApiKey());
		return new HttpEntity<>(headers);
	}
}
