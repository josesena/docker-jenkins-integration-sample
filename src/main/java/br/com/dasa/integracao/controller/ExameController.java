package br.com.dasa.integracao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dasa.integracao.model.DetalheExameDto;
import br.com.dasa.integracao.model.ExameDto;
import br.com.dasa.integracao.model.MarcaDto;
import br.com.dasa.integracao.model.ParamFilter;
import br.com.dasa.integracao.model.UnidadeConsolidadoDto;
import br.com.dasa.integracao.services.ExameService;
import br.com.dasa.integracao.services.MarcaService;
import br.com.dasa.integracao.services.UnidadeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/exame")
public class ExameController {

	@Autowired
	private ExameService exameApiService;
	
	@Autowired
	private MarcaService marcaApiService;
	
	@Autowired
	private UnidadeService unidadeApiService;

	@GetMapping
	public @ResponseBody List<ExameDto> buscaExames(
			@RequestHeader String authorization,
			@RequestParam(value = "descricao") String descricao) {
		String path = "/exame/descricao=" + descricao;
		if(log.isTraceEnabled())log.trace("GET {} -> [ExameController][buscaExames] Busca registro de exames", path);
		ParamFilter filter = new ParamFilter();
		filter.setQ(descricao);
		return exameApiService.buscaExames(filter);
	}
	
	@GetMapping("/{id}")
	public @ResponseBody DetalheExameDto detalheExame(
			@RequestHeader String authorization,
			@PathVariable("id") Integer id) {
		String path = "/exame/" + id;
		if(log.isTraceEnabled()) log.trace("GET {} -> [ExameController][detalheExame] Busca detalhe do exame", path);
		return exameApiService.buscaDetalheExame(id);

	}
	
	@GetMapping("/marcas")
	public @ResponseBody List<MarcaDto> buscaMarcas(@RequestHeader String authorization) {
		String path = "/marcas";
		if(log.isTraceEnabled()) log.trace("GET {} -> [DetalheMarcaController][buscaDetalheMarca] Busca detalhe da marca", path);
		return marcaApiService.buscaMarcas(null);

	}
	
	@GetMapping("/{id}/marca")
	public @ResponseBody MarcaDto detalheMarca(@RequestHeader String authorization,
			@PathVariable("id") Integer id) {
		String path = "/detalhe-marca/"+ id;
		if(log.isTraceEnabled()) log.trace("GET {} -> [DetalheMarcaController][buscaDetalheMarca] Busca detalhe da marca", path);
		ParamFilter filter = new ParamFilter();
		filter.setId(id);
		return marcaApiService.buscaDetalheMarca(filter);

	}
	
	@GetMapping("/{idProduto}/marca/{idMarca}/unidades")
	public @ResponseBody UnidadeConsolidadoDto buscaUnidades(
			@RequestHeader String authorization,
			@PathVariable("idProduto") Integer idProduto, 
			@PathVariable("idMarca") Integer idMarca) {
		String path = "/exame/" +idProduto + "/marca/" + idMarca + "/unidades";
		
		if(log.isTraceEnabled()) log.trace("GET {} -> [BuscaUnidadeController][buscaUnidades] Busca de unidades consolidado", path);
		
		ParamFilter param = new ParamFilter();
		param.setIdProduto(idProduto);
		param.setIdMarca(idMarca);
		
		return unidadeApiService.buscaUnidadesConsolidado(param);
	}

}
