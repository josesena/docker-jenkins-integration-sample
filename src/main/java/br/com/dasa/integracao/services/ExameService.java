package br.com.dasa.integracao.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.dasa.integracao.config.ApiVariableConfig;
import br.com.dasa.integracao.model.DetalheExameDto;
import br.com.dasa.integracao.model.ExameDto;
import br.com.dasa.integracao.model.MarcaDto;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.services.exception.ExameException;
import br.com.dasa.integracao.utils.HttpUtil;
import br.com.dasa.integracao.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExameService {
	
	@Autowired
	private DataProviderApiRestService dataProviderRestService;

	@Autowired
	private ApiVariableConfig appConfig;

	@Autowired
	private HttpUtil httpUtil;

	@Autowired
	private MarcaService marcaApiService;

	public List<ExameDto> buscaExames(ParamFilter param) {

		ResponseEntity<String> response;
		List<ExameDto> listExame = new ArrayList<>();

		try {
			
			log.info("[ExameService][buscaExames] Iniciando busca de exames");
			String url = httpUtil.prepareParamUri(param, appConfig.getUrlApiExameDp());
			response = dataProviderRestService.get(url);
			log.info("[ExameService][buscaExames] Fim da busca de exames");
			
			if (response.getBody() == null)
				return listExame;

			JSONObject obj = new JSONObject(response.getBody());
			JSONArray jsonArray = obj.getJSONArray("content");
			return JsonUtil.convertJsonToListObject(jsonArray.toString(), ExameDto.class);

		} catch (HttpClientErrorException e) {
			log.warn("Ocorreu um erro na chamada do Data Provider na busca de exames | Endpoint: {} | \nClient error {} \nError: ", appConfig.getUrlApiExameDp(), e.getMessage(), e);
			throw new ExameException("erro-api-data-provider", e.getStatusCode(), ExceptionUtils.getRootCauseMessage(e));
		} 
		catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>> Erro na requisição >>>>>>>>>>>>>>>> \nException: ", e);
			throw new ExameException("erro-interno", HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getRootCauseMessage(e));
		}

	}

	public DetalheExameDto buscaDetalheExame(Integer id) {

		ResponseEntity<String> response;
		try {
			
			log.info("[ExameService][buscaDetalheExame] Iniciando busca detalhe do exame");
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format(appConfig.getUrlApiDetailDp(), id));
			response = dataProviderRestService.get(builder.toUriString());
			log.info("[ExameService][buscaDetalheExame] Fim da busca de detalhe do exame");

			if (response.getBody() == null)
				throw new ExameException("erro-detalhe-exame-nao-encontrado", response.getStatusCode(),
						response.getHeaders().toString());

			DetalheExameDto detalheExame = new Gson().fromJson(response.getBody(), DetalheExameDto.class);

			return populaMarcaAtivaAndInativas(detalheExame);
		} catch (HttpClientErrorException e) {
			log.error("Ocorreu um erro na chamada do Data Provider na busca de detalhe do exame | Endpoint: {} |Client error {} \n Error: ",
					appConfig.getUrlApiDetailDp(), e.getMessage(), e);
			throw new ExameException("data-provider-erro-api", e.getStatusCode(), ExceptionUtils.getRootCauseMessage(e));
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>> Erro na requisição >>>>>>>>>>>>>>>> \nException: ", e);
			throw e;
		}
	}

	public DetalheExameDto populaMarcaAtivaAndInativas(DetalheExameDto detalheExame) {

		List<MarcaDto> marcas = marcaApiService.buscaMarcas(detalheExame);
		List<MarcaDto> listAtiva = new ArrayList<>();
		List<MarcaDto> listInativa = new ArrayList<>();
		if (!marcas.isEmpty()) {
			listAtiva = marcas.stream()
					.filter(x -> (detalheExame.getIdMarcas().stream().filter(y -> y.equals(x.getId())).count()) > 0)
					.collect(Collectors.toList());
			listInativa = marcas.stream()
					.filter(x -> (detalheExame.getIdMarcas().stream().filter(y -> y.equals(x.getId())).count()) < 1)
					.collect(Collectors.toList());
		}

		detalheExame.setMarcasAtivas(listAtiva);
		detalheExame.setMarcasInativas(listInativa);

		return detalheExame;
	}
}
