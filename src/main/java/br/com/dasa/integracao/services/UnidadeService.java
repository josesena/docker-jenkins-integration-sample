package br.com.dasa.integracao.services;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.dasa.integracao.config.ApiVariableConfig;
import br.com.dasa.integracao.model.ErrorDto;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.model.UnidadeConsolidadoDto;
import br.com.dasa.integracao.model.enums.ErroEnum;
import br.com.dasa.integracao.services.exception.UnidadeException;
import br.com.dasa.integracao.utils.HttpUtil;
import br.com.dasa.integracao.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnidadeService {
	
	@Autowired
	private DataProviderApiRestService dataProviderRestService;

	@Autowired
	private ApiVariableConfig appConfig;
	
	@Autowired
	private HttpUtil httpUtil;
	
	public UnidadeConsolidadoDto buscaUnidadesConsolidado(ParamFilter param) {
		
		ResponseEntity<String> response;

		try {
			
			log.info("[UnidadeService][buscaUnidadePorProduto] Iniciando busca unidade por produto");
			String url = httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityByProductDp());
			response = dataProviderRestService.get(url);
			log.info("[UnidadeService][buscaUnidadePorProduto] Fim da busca unidade por produto");

			if (response.getBody() == null)
				throw new UnidadeException("erro-unidade-nao-encontrado", response.getStatusCode(), response.toString());
			
			
			UnidadeConsolidadoDto consolidado = JsonUtil.parseJsonParaUnidade(response.getBody());
			
			log.info("*********** Iniciando busca na API de Prazo por Unidade ***********");
			consolidado = buscaPrazoPorUnidade(param, consolidado);
			log.info("Fim da busca na API de Prazo por Unidade");
			
			log.info("*********** Iniciando busca na API de variaveis e preço por unidade ***********");
			consolidado = buscaPrecoPorUnidade(param, consolidado);
			log.info("Fim da busca na API de Preço por Unidade");
			
			return consolidado;
		} catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de unidades consolidada | Endpoint: {} |\nClient error {} \n Error: ", 
					appConfig.getUrlApiUnityByProductDp(), e.getMessage(), e);
			throw new UnidadeException("erro-api-data-provider", e.getStatusCode(), e.getMessage());
		}catch(Exception e) {
			log.error(">>>>>>>>>>>>>>>>Erro na requisição>>>>>>>>>>>>>>>>\nException: ",e);
			throw e;
		}
	}

	

	public UnidadeConsolidadoDto buscaPrazoPorUnidade(ParamFilter param, UnidadeConsolidadoDto consolidado) {
		
		ResponseEntity<String> response;
		
		try {

			log.info("[UnidadeService][buscaPrazoPorUnidade] Iniciando busca prazo por unidade");
			String url = httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityDeadlineDp());
			response = dataProviderRestService.get(url);
			log.info("[UnidadeService][buscaPrazoPorUnidade] Fim da busca prazo por unidade");
			
			if(response.getBody() == null) {
				consolidado.getErrors().add(new ErrorDto(ErroEnum.DEADLINE_NOT_FOUND, response.toString()));
				return consolidado;
			}
			
			return JsonUtil.matchPrazoPorUnidade(response.getBody(), consolidado);
		} catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de preço por unidades | Endpoint: {} | \nClient error {} \nError: ", 
					appConfig.getUrlApiUnityDeadlineDp(), e.getMessage(), e);
			consolidado.getErrors().add(new ErrorDto(ErroEnum.DEADLINE_NOT_FOUND, ExceptionUtils.getRootCauseMessage(e)));
		}catch(Exception e) {
			log.error(">>>>>>>>>>>>>>>>Erro na requisição>>>>>>>>>>>>>>>>\nException: ",e);
			consolidado.getErrors().add(new ErrorDto(ErroEnum.INTERNAL_ERROR, ExceptionUtils.getRootCauseMessage(e)));
		}
		
		return consolidado;
	}
	
	public UnidadeConsolidadoDto buscaPrecoPorUnidade(ParamFilter param, UnidadeConsolidadoDto consolidado) {
		
		ResponseEntity<String> response;
		try {

			param.setProdutos(param.getIdProduto());
			param.setVariaveis(buscaVariaveisPorMarca(param, consolidado));
			param.setIdSistema(appConfig.getIdSistema());
			log.info("[UnidadeService][buscaPrecoPorUnidade] Iniciando busca preco por unidade");
			String url = httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityPriceDp());
			response = dataProviderRestService.get(url);
			log.info("[UnidadeService][buscaPrecoPorUnidade] Fim da busca preco por unidade");
			
			if(response.getBody() == null) {
				consolidado.getErrors().add(new ErrorDto(ErroEnum.PRICE_NOT_FOUND, response.toString()));
				return consolidado;
			}
			
			return JsonUtil.matchPrecoPorUnidade(response.getBody(), consolidado);
		} catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de preço por unidades | Endpoint: {} | \nClient error {} \nError: ", 
					appConfig.getUrlApiUnityPriceDp(),e .getMessage(), e);
			consolidado.getErrors().add(new ErrorDto(ErroEnum.PRICE_NOT_FOUND, e.getMessage()));
		}catch(Exception e ) {
			log.error(">>>>>>>>>>>>>>>>Erro na requisição>>>>>>>>>>>>>>>>\nException: ",e);
			consolidado.getErrors().add(new ErrorDto(ErroEnum.INTERNAL_ERROR, ExceptionUtils.getRootCauseMessage(e)));
		}
		
		return consolidado;
	}

	public String buscaVariaveisPorMarca(ParamFilter param, UnidadeConsolidadoDto consolidado) {
		
		String variaveis = null;
		ResponseEntity<String> response;
		
		try {

			param.setIdCliente(appConfig.getIdCliente());
			param.setIdProduto(null);
			log.info("[UnidadeService][buscaVariaveisPorMarca] Iniciando busca de variáveis por marca");
			String url = httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityVariableDp());
			response = dataProviderRestService.get(url);
			log.info("[UnidadeService][buscaVariaveisPorMarca] Fim da busca variaveis por marca");
			
			if(response.getBody() == null) {
				consolidado.getErrors().add(new ErrorDto(ErroEnum.VARIABLE_NOT_FOUND, null));
			}

			return JsonUtil.getVariaveisMarca(response.getBody());

		} catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de variaveis | Endpoint: {} | \nClient error {} \nError: ", 
					appConfig.getUrlApiUnityVariableDp(), e.getMessage(), e);
			consolidado.getErrors().add(new ErrorDto(ErroEnum.VARIABLE_NOT_FOUND, ExceptionUtils.getRootCauseMessage(e)));
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>Erro na requisição>>>>>>>>>>>>>>>>\nException: ", e);
			consolidado.getErrors().add(new ErrorDto(ErroEnum.INTERNAL_ERROR, ExceptionUtils.getRootCauseMessage(e)));
		}
		return variaveis;
	}
}
