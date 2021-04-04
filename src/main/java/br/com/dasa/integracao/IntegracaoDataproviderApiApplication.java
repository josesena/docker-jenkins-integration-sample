package br.com.dasa.integracao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class IntegracaoDataproviderApiApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(IntegracaoDataproviderApiApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Value("${spring.application.name}")
	private String appName;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//Printa na cosole todas as variaveis de embiente e propriedades da JVM
		if(log.isInfoEnabled()) log.info("SYSTEM ENVIRONMENT: {}", new Gson().toJson(System.getenv()));
		if(log.isInfoEnabled()) log.info("JAVA JVM PROPERTIES: {}", new Gson().toJson(System.getProperties()));
		
		//A partir de aqui e aplicação esta em execucao:
		if(log.isInfoEnabled()) log.info("Aplicacao [{}] inciando com sucesso: args = {}", appName, new Gson().toJson(args.getOptionNames()));
	}

}
