package br.com.dasa.integracao.utils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.dasa.integracao.model.EnderecoDto;
import br.com.dasa.integracao.model.UnidadeConsolidadoDto;
import br.com.dasa.integracao.model.UnidadeDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {

	private JsonUtil() {
	}

	public static <T> List<T> convertJsonToListObject(String body, Class<T> type) {

		Type listType = TypeToken.getParameterized(List.class, type).getType();

		return new Gson().fromJson(body, listType);
	}

	public static UnidadeConsolidadoDto matchPrecoPorUnidade(String body, UnidadeConsolidadoDto consolidado)
			throws JSONException {

		JSONArray arr = new JSONArray(body);
		JSONObject objExame = null;
		JSONArray arrayExame = null;
		for (int i = 0; i < arr.length(); i++) {
			objExame = arr.getJSONObject(i);
			int id = objExame.getInt("idUnidade");
			arrayExame = objExame.getJSONArray("exames");
			for (int j = 0; j < arrayExame.length(); j++) {
				JSONObject obj = arrayExame.getJSONObject(j);
				double d = obj.getDouble("preco");
				consolidado.getUnidades().forEach(unidade -> {
					if (unidade.getId().equals(id)) {
						unidade.setPreco(BigDecimal.valueOf(d));
					}
				});
			}

		}

		return consolidado;
	}

	public static UnidadeConsolidadoDto matchPrazoPorUnidade(String body, UnidadeConsolidadoDto consolidado)
			throws JSONException {
		log.info("[UnidadeApiService][matchPrazoPorUnidade] Consoldyidando info de prazo na lista de unidades");

		JSONArray arr = new JSONArray(body);

		JSONObject objPrazo = null;
		for (int i = 0; i < arr.length(); i++) {
			objPrazo = arr.getJSONObject(i);
			int id = objPrazo.getInt("idUnidade");
			int prazoHora = objPrazo.getInt("prazoHora");
			int prazoHoraUrgente = objPrazo.getInt("prazoHoraUrgente");
			boolean permitePrazoUrgente = objPrazo.getBoolean("permitePrazoUrgente");
			consolidado.getUnidades().forEach(u -> {
				if (u.getId().equals(id)) {
					u.setPrazoHora(prazoHora);
					u.setPrazoHoraUrgente(prazoHoraUrgente);
					u.setPermitePrazoUrgente(permitePrazoUrgente);
				}
			});
		}

		return consolidado;
	}

	public static String getVariaveisMarca(String body) throws JSONException {

		StringBuilder sb = new StringBuilder();

		JSONArray arr = new JSONArray(body);
		JSONObject objMarca = null;
		JSONArray arrayMarca = null;
		for (int x = 0; x < arr.length(); x++) {
			objMarca = arr.getJSONObject(x);
		}

		if (objMarca != null)
			arrayMarca = objMarca.getJSONArray("marcas");

		if (arrayMarca != null) {
			for (int i = 0; i < arrayMarca.length(); i++) {
				JSONObject objVariaveis = arrayMarca.getJSONObject(i);
				JSONArray arrayVariaveis = objVariaveis.getJSONArray("variaveis");
				if (arrayVariaveis != null) {
					for (int j = 0; j < arrayVariaveis.length(); j++) {
						JSONObject obj = arrayVariaveis.getJSONObject(j);
						sb.append(obj.getInt("id")).append(":").append("0").append(",");
					}
				}
			}

		}

		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static UnidadeConsolidadoDto parseJsonParaUnidade(String body) {

		List<UnidadeDto> unidades = null;
		List<EnderecoDto> enderecos = null;
		int idProduto = 0;

		try {

			JSONObject objUnidade = new JSONObject(body);
			idProduto = objUnidade.getInt("idProduto");
			unidades = convertJsonToListObject(objUnidade.getJSONArray("unidades").toString(), UnidadeDto.class);
			enderecos = convertJsonToListObject(objUnidade.getJSONArray("unidades").toString(), EnderecoDto.class);

			for (EnderecoDto endereco : enderecos) {
				unidades.forEach(unidade -> {
					if (unidade.getId().equals(endereco.getId())) {
						unidade.setEndereco(endereco);
					}
				});
			}

		} catch (JSONException e) {
			log.info("[UnidadeApiService][convertJsonToUnidades] Erro no parsing do JSON \nErro: ", e);
		}

		UnidadeConsolidadoDto consolidado = new UnidadeConsolidadoDto(unidades);
		consolidado.setIdProduto(idProduto);

		return consolidado;
	}
}
