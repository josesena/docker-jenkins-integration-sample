package br.com.dasa.integracao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.dasa.integracao.services.DataProviderApiRestService;
import br.com.dasa.integracao.utils.HttpUtil;
import mock.JsonMock;

@SpringBootTest
class DataProviderRestServiceTest {
	
	private String URL_FAKE = "http://localhost:8090";

	@InjectMocks
	private DataProviderApiRestService service;
	
	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpUtil httpUtil;
	
	private JsonMock objectJson;
	
	private String jsonExame;

	@BeforeEach
	void setup() {
		objectJson = new JsonMock();
		jsonExame = objectJson.getExame();
	}
	
	@Test
	void busca_registro_ok() {
		ResponseEntity<String> serviceResponse = new ResponseEntity<String>(jsonExame, HttpStatus.OK);
		
		when(restTemplate.exchange(
		        ArgumentMatchers.anyString(),
		        ArgumentMatchers.any(HttpMethod.class),
		        ArgumentMatchers.any(),
		        ArgumentMatchers.<Class<String>>any()))
		        .thenReturn(serviceResponse);
		ResponseEntity<String> response = service.get(URL_FAKE);
		assertThat(response).isNotNull();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void busca_resgistro_bad_request() {
		when(restTemplate.exchange(Mockito.anyString(), 
				Mockito.any(HttpMethod.class), Mockito.any(), 
				Mockito.<Class<String>>any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		assertThrows(HttpClientErrorException.class, () -> service.get(URL_FAKE));
	}
	
}
