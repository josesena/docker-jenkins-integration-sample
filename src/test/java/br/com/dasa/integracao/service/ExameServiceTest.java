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

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import br.com.dasa.integracao.config.ApiVariableConfig;
import br.com.dasa.integracao.model.DetalheExameDto;
import br.com.dasa.integracao.model.ExameDto;
import br.com.dasa.integracao.model.MarcaDto;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.services.DataProviderApiRestService;
import br.com.dasa.integracao.services.ExameService;
import br.com.dasa.integracao.services.MarcaService;
import br.com.dasa.integracao.services.exception.ExameException;
import br.com.dasa.integracao.utils.HttpUtil;
import mock.JsonMock;

@SpringBootTest
class ExameServiceTest {

	private String URL_FAKE = "http://localhost:8090";
	
	@InjectMocks
	private ExameService service;
	
	@Mock
	private MarcaService marcaApiService;
	
	@Mock
	private DataProviderApiRestService dataProviderRestService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpUtil httpUtil;

	@Mock
	private ApiVariableConfig appConfig;

	private JsonMock objectJson;

	private String jsonExame, jsonDetalheExame, jsonMarcas;

	@BeforeEach
	void setup() {
		objectJson = new JsonMock();
		jsonExame = objectJson.getExame();
		jsonDetalheExame = objectJson.getDetalheExame();
		jsonMarcas = objectJson.getMarcas();
	}
	
	@Test
	void busca_exames_retorno_ok() {
		when(appConfig.getUrlApiExameDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiExameDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonExame, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		List<ExameDto> response = service.buscaExames(new ParamFilter());
		assertThat(response).isNotNull();
	}
	
	@Test
	void busca_exames_retorno_no_content() {
		when(appConfig.getUrlApiExameDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiExameDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		List<ExameDto> response = service.buscaExames(new ParamFilter());
		assertThat(response).isEmpty();
	}
	
	@Test
	void busca_exames_retorno_bad_request() {
		when(appConfig.getUrlApiExameDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiExameDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		assertThrows(ExameException.class, () -> service.buscaExames(new ParamFilter()));
	}
	
	@Test
	void busca_exames_retorno_internal_server_error() {
		when(appConfig.getUrlApiExameDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiExameDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		assertThrows(ExameException.class, () -> service.buscaExames(new ParamFilter()));
	}
	
	@Test
	void busca_detalhe_exame_retorno_ok() {
		when(appConfig.getUrlApiDetailDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiDetailDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(jsonDetalheExame, HttpStatus.OK);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		DetalheExameDto response = service.buscaDetalheExame(1);
		assertThat(response).isNotNull();
	}
	
	@Test
	void busca_detalhe_exame_retorno_no_content() {
		when(appConfig.getUrlApiDetailDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiDetailDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		assertThrows(ExameException.class, () -> service.buscaDetalheExame(1));
	}
	
	@Test
	void busca_detalhe_exame_retorno_bad_request() {
		when(appConfig.getUrlApiDetailDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiDetailDp())).thenReturn(URL_FAKE);
		when(dataProviderRestService.get(URL_FAKE)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		assertThrows(ExameException.class, () -> service.buscaDetalheExame(1));
	}
	
	@Test
	void busca_detalhe_exame_retorno_internal_server_error() {
		when(appConfig.getUrlApiDetailDp()).thenReturn(URL_FAKE);
		when(httpUtil.prepareParamUri(new ParamFilter(), appConfig.getUrlApiDetailDp())).thenReturn(URL_FAKE);
		ResponseEntity<String> entity = new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		when(dataProviderRestService.get(URL_FAKE)).thenReturn(entity);
		assertThrows(Exception.class, () -> service.buscaDetalheExame(1));
	}
	
	@Test
	void popula_marca_ativa_e_inativa() {
		DetalheExameDto detalheExame = new Gson().fromJson(jsonDetalheExame, DetalheExameDto.class);
		MarcaDto[] listMarcas = new Gson().fromJson(jsonMarcas, MarcaDto[].class);
		List<MarcaDto> marcas = Lists.newArrayList(listMarcas);
		when(marcaApiService.buscaMarcas(detalheExame)).thenReturn(marcas);
		DetalheExameDto detalhe = service.populaMarcaAtivaAndInativas(detalheExame);
		assertThat(detalhe).isNotNull();
	}
}
