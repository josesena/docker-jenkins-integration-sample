package br.com.dasa.integracao.services;

import static br.com.dasa.integracao.utils.NumberUtil.startWatch;
import static br.com.dasa.integracao.utils.NumberUtil.stopWatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.dasa.integracao.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataProviderApiRestService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpUtil httpUtil;
	
	public ResponseEntity<String> get(String url){
		
		long nanos = startWatch();
		ResponseEntity<String> response;
		
		try  {
			
			HttpEntity<String> httpEntity = httpUtil.getHeaders();
			log.info("[DataProviderRestService][get] Endpoint -> {}", url);
			response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			log.info("Response da API Data Provider <- {} | gastou {}", response.getStatusCode(), stopWatch(nanos));
			
		}catch(HttpClientErrorException e) {
			log.error("Endpoint: {} | Client error {} \n Error: ", url, e.getMessage(), e);
			throw e;
		}
		return response;
	}
}
