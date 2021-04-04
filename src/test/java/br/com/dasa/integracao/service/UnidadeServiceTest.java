package br.com.dasa.integracao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.dasa.integracao.config.ApiVariableConfig;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.model.UnidadeConsolidadoDto;
import br.com.dasa.integracao.services.DataProviderApiRestService;
import br.com.dasa.integracao.services.UnidadeService;
import br.com.dasa.integracao.services.exception.UnidadeException;
import br.com.dasa.integracao.utils.HttpUtil;
import mock.JsonMock;

@SpringBootTest
class UnidadeServiceTest {

	private String URL_FAKE = "http://localhost:8090";
	
	@Mock
	private DataProviderApiRestService dataProviderRestService;

	@InjectMocks
	private UnidadeService service;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpUtil httpUtil;

	@Mock
	private ApiVariableConfig appConfig;

	private JsonMock objectJson;

	private String jsonUnidades, jsonPrazoPorUnidade, jsonPrecoPorUnidade;
	
	@BeforeEach
	void setup() {
		objectJson = new JsonMock();
		jsonUnidades = objectJson.getUnidade();
		jsonPrazoPorUnidade = objectJson.getPrazoPorUnidade();
		jsonPrecoPorUnidade = objectJson.getPrecoPorUnidade();
	}
	
	@Test
	void busca_unidades_retorno_ok() {
		when(appConfig.getUrlApiUnityByProductDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityByProductDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonUnidades, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto response = service.buscaUnidadesConsolidado(new ParamFilter());
		assertThat(response).isNotNull();
	}
	
	@Test
	void busca_unidades_retorno_no_content() {
		when(appConfig.getUrlApiUnityByProductDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityByProductDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		assertThrows(UnidadeException.class, () -> service.buscaUnidadesConsolidado(new ParamFilter()));
	}
	
	@Test
	void busca_unidades_retorno_bad_request() {
		when(appConfig.getUrlApiUnityByProductDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityByProductDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		assertThrows(UnidadeException.class, () -> service.buscaUnidadesConsolidado(new ParamFilter()));
	}
	
	@Test
	void busca_prazo_por_unidade_retorno_ok() {
		when(appConfig.getUrlApiUnityDeadlineDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityDeadlineDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonPrazoPorUnidade, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto consolidado = new Gson().fromJson(jsonUnidades, UnidadeConsolidadoDto.class);
		consolidado = service.buscaPrazoPorUnidade(new ParamFilter(), consolidado);
		assertThat(consolidado.getUnidades()).isNotEmpty();
	}
	
	
	@Test
	void busca_prazo_por_unidade_retorno_no_content() {
		when(appConfig.getUrlApiUnityDeadlineDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityDeadlineDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto();
		consolidado = service.buscaPrazoPorUnidade(new ParamFilter(), consolidado);
		assertThat(consolidado.getErrors()).isNotEmpty();
	}
	
	
	@Test
	void busca_prazo_por_unidade_retorno_bad_request() {
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityDeadlineDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto();
		consolidado = service.buscaPrazoPorUnidade(new ParamFilter(), consolidado);
		assertThat(consolidado.getErrors()).isNotEmpty();
	}
	
	@Test
	void busca_prazo_por_unidade_retorno_internal_server_error() {
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiUnityDeadlineDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto();
		consolidado = service.buscaPrazoPorUnidade(new ParamFilter(), consolidado);
		assertThat(consolidado.getErrors()).isNotEmpty();
	}
	
	@Test
	void busca_preco_por_unidade_retorno_ok() {
		ParamFilter param = new  ParamFilter();
		when(appConfig.getUrlApiUnityPriceDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityPriceDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonPrecoPorUnidade, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto consolidado = new Gson().fromJson(jsonUnidades, UnidadeConsolidadoDto.class);
		consolidado = service.buscaPrecoPorUnidade(new ParamFilter(), consolidado);
		assertThat(consolidado.getUnidades()).isNotEmpty();
	}
	
	
	@Test
	void busca_preco_por_unidade_retorno_no_content() {
		ParamFilter param = new  ParamFilter();
		when(appConfig.getUrlApiUnityPriceDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityPriceDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto();
		consolidado = service.buscaPrecoPorUnidade(param, consolidado);
		assertThat(consolidado.getErrors()).isNotEmpty();
	}
	
	@Test
	void busca_preco_por_unidade_retorno_bad_request() {
		ParamFilter param = new  ParamFilter();
		when(httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityPriceDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto();
		consolidado = service.buscaPrecoPorUnidade(param, consolidado);
		assertThat(consolidado.getErrors()).isNotEmpty();
	}
	
	@Test
	void busca_variaveis_por_unidade_retorno_no_content() {
		ParamFilter param = new  ParamFilter();
		when(appConfig.getUrlApiUnityVariableDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(param, appConfig.getUrlApiUnityVariableDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto();
		String response = service.buscaVariaveisPorMarca(param, consolidado);
		assertThat(consolidado.getErrors()).isNotEmpty();
		assertThat(response).isNull();
	}
}
