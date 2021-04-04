package br.com.dasa.integracao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

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
import br.com.dasa.integracao.model.DetalheExameDto;
import br.com.dasa.integracao.model.MarcaDto;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.services.DataProviderApiRestService;
import br.com.dasa.integracao.services.MarcaService;
import br.com.dasa.integracao.services.exception.MarcaException;
import br.com.dasa.integracao.utils.HttpUtil;
import mock.JsonMock;

@SpringBootTest
class MarcaServiceTest {
	
	private String URL_FAKE = "http://localhost:8090";

	@InjectMocks
	private MarcaService service;
	
	@Mock
	private DataProviderApiRestService dataProviderRestService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpUtil httpUtil;
	
	@Mock
	private ApiVariableConfig appConfig;

	private JsonMock objectJson;
	
	private String jsonMarcas, jsonDetalheExame;
	
	@BeforeEach
	void setup() {
		objectJson = new JsonMock();
		jsonDetalheExame = objectJson.getDetalheExame();
		jsonMarcas = objectJson.getMarcas();
	}
	
	@Test
	void busca_marcas_retorno_ok() {
		when(appConfig.getUrlApiBrandDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiBrandDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonMarcas, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		DetalheExameDto detalhe = new Gson().fromJson(jsonDetalheExame, DetalheExameDto.class);
		List<MarcaDto> response = service.buscaMarcas(detalhe);
		assertThat(response).isNotNull();
	}
	
	@Test
	void busca_marcas_retorno_bad_request() {
		when(appConfig.getUrlApiBrandDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiBrandDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		DetalheExameDto detalhe = new Gson().fromJson(jsonDetalheExame, DetalheExameDto.class);
		List<MarcaDto> response = service.buscaMarcas(detalhe);
		assertThat(response).isEmpty();
		assertThat(detalhe.getErrors()).isNotEmpty();
	}
	
	@Test
	void busca_marcas_retorno_internal_server_error() {
		ResponseEntity<String> entity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		DetalheExameDto detalhe = new Gson().fromJson(jsonDetalheExame, DetalheExameDto.class);
		List<MarcaDto> response = service.buscaMarcas(detalhe);
		assertThat(response).isEmpty();
		assertThat(detalhe.getErrors()).isNotEmpty();
	}
	
	@Test
	void busca_detalhe_marca_retorno_ok() {
		when(appConfig.getUrlApiBrandDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiBrandDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonMarcas, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		MarcaDto response = service.buscaDetalheMarca(new ParamFilter());
		assertThat(response).isNotNull();
	}
	
	
	@Test
	void busca_detalhe_marca_bad_request() {
		when(appConfig.getUrlApiBrandDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiBrandDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		assertThrows(MarcaException.class, () ->  service.buscaDetalheMarca(new ParamFilter()));
	}
	
	@Test
	void busca_detalhe_marca_internal_server_error() {
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiBrandDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		assertThrows(MarcaException.class, () ->  service.buscaDetalheMarca(new ParamFilter()));
	}
}
