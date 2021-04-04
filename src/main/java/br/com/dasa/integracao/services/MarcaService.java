package br.com.dasa.integracao.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.dasa.integracao.config.ApiVariableConfig;
import br.com.dasa.integracao.model.DetalheExameDto;
import br.com.dasa.integracao.model.ErrorDto;
import br.com.dasa.integracao.model.MarcaDto;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.model.enums.ErroEnum;
import br.com.dasa.integracao.services.exception.MarcaException;
import br.com.dasa.integracao.utils.HttpUtil;
import br.com.dasa.integracao.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MarcaService {
	
	@Autowired
	private DataProviderApiRestService dataProviderRestService;
	
	@Autowired
	private ApiVariableConfig appConfig;

	@Autowired
	private HttpUtil httpUtil;
	
	public List<MarcaDto> buscaMarcas(DetalheExameDto  detalhe) {
		
		ResponseEntity<String> response;
		List<MarcaDto> marcas = new ArrayList<>();
		
		try {
			
			log.info("[MarcaService][buscaMarcas] Iniciando busca todas as marcas");
			response = dataProviderRestService.get(appConfig.getUrlApiBrandDp());
			log.info("[MarcaService][buscaMarcas] Fim da busca de marcas");
			
			marcas = JsonUtil.convertJsonToListObject(response.getBody(), MarcaDto.class);
			
			return marcas;
			
		}catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de da marcas | Endpoint: {} | \nClient error {} \nError: ", 
					appConfig.getUrlApiBrandDp(), e.getMessage(), e);
			detalhe.getErrors().add(new ErrorDto(ErroEnum.BRAND_NOT_FOUND, ExceptionUtils.getRootCauseMessage(e)));
		} catch (Exception e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de todas as marcas | Endpoint: {} \n Error: ", appConfig.getUrlApiBrandDp(), e);
			detalhe.getErrors().add(new ErrorDto(ErroEnum.INTERNAL_ERROR, ExceptionUtils.getRootCauseMessage(e)));
		}
		return marcas;
		
	}

	public MarcaDto buscaDetalheMarca(ParamFilter param) {
		
		ResponseEntity<String> response;
		try {

			log.info("[MarcaService][buscaDetalheMarca] Iniciando busca detalhe da marca");
			String url = httpUtil.prepareParamUri(param, appConfig.getUrlApiBrandDp());
			response = dataProviderRestService.get(url);
			log.info("[MarcaService][buscaDetalheMarca] Fim da busca detalhe da marca");

			List<MarcaDto> list = JsonUtil.convertJsonToListObject(response.getBody(), MarcaDto.class);

			return list.get(0);
		} catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de detalhe da marca | Endpoint: {} |\nClient error \nError: ", 
					appConfig.getUrlApiBrandDp(), e.getMessage(), e);
			throw new MarcaException("erro-api-data-provider", e.getStatusCode(), e.getMessage());
		}catch(Exception e) {
			log.error(">>>>>>>>>>>>>>>> Erro na requisição >>>>>>>>>>>>>>>> \nException: ",e);
			throw new MarcaException("erro-interno", HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getRootCauseMessage(e));
		}
	}

}
