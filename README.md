# INTEGRACAO DATA PROVIDER API #

## Java Verion
Este projeto requer que você tenha
[Java11](https://www.oracle.com/java/technologies/javase-downloads.html).

## Download Maven
Para começar, faça [download][maven-download] e [install][maven-install]

[maven]: https://maven.apache.org
[maven-download]: https://maven.apache.org/download.cgi
[maven-install]: https://maven.apache.org/install.html


## Download KeyCloak
[KeyCloak]: https://www.keycloak.org//downloads.html
* Navegue até a pasta /bin e execute o arquivo 
```
./standalone.sh
```
* Acesse a console [localhost] do keyCloak, crie o usuário e senha para e siga os passos para criar um Client KeyCloak [client-keycloak]

[localhost]: http://localhost:8080
[client-keycloak]: https://jiradasa.atlassian.net/wiki/spaces/COET/pages/1759838687/Como+criar+Client+no+Keycloak
## End-Point Busca Exames

* URl : localhost:8090/v1/exame?descricao=PAPANICOLAU
* METHOD : get
* AUTHORIZATION : Bearer token
* REQUEST :
```
	{
        "id": 7332,
        "nome": "PAPANICOLAU",
        "sinonimias": [
            "PAPANICOLAOU",
            "COLPOCITOLOGIA",
            "COLPOCITOLOGIA 2",
            "COLPOCITOLOGICO",
            "CITOLOGIA ONCOTICA",
            "CITOLOGIA DE MICROFLORA",
            "EXAME COLPOCITOLOGICO",
            "PAPANICOLAOU ONCOTICA",
            "CITOLOGIA EM MEIO LIQUIDO",
            "COLPOCITOLOGIA ONCOTICA",
            "CITOLOGIA CERVICO VAGINAL",
            "CITOLOGIA ONCOTICA-2A.AMOSTRA",
            "CITOLOGICO DE SECRECAO VAGINAL",
            "PREVENTIVO DO CANCER GINECOLOGICO",
            "CITOLOGIA DE SECRECAO DO CANAL VAGINAL",
            "COLPOCITOLOGIA ONCOPARASITARIA",
            "COLPOCITOLOGIA ONCOTICA TRIPLICE",
            "CITOLOGIA ONCÓTICA, CERVICO-VAGINAL",
            "COLPOCITOLOGIA ONCOTICA DE ESFREGACO VAGINAL",
            "CITOPATOLOGICO VAGINAL ONCOTICO E MICROFLORA"
        ],
        "categoria": {
            "id": 2,
            "nome": "Anatomia Patológica"
        }
    } 
```

* URl : localhost:8090/v1/exame/1/marca
* METHOD : get
* AUTHORIZATION : Bearer token
* REQUEST :
```
	{
	    "id": 1,
	    "mnemonico": "Delboni",
	    "nome": "Delboni Auriemo",
	    "segmento": "Executive",
	    "url": "http://www.delboniauriemo.com.br/",
	    "whatsapp": "11 3049-6999",
	    "urlAgendamento": "agendamentoonline.delboniauriemo.com.br/",
	    "urlLaudo": "delboniauriemo.com.br/resultados-de-exames",
	    "ufs": [
	        "SP"
	    ]
	} 
```

## End-Point Detalhe Exame
		
* URl : localhost:8090/v1/exame/7332
* METHOD : get
* AUTHORIZATION : Bearer token
* REQUEST :
```
	{
    "id": 7332,
    "nome": "PAPANICOLAU",
    "categoria": {
        "id": 2,
        "nome": "Anatomia Patológica"
    },
    "sinonimias": [
        "CITOLOGIA EM MEIO LIQUIDO",
        "PAPANICOLAOU ONCOTICA",
        "EXAME COLPOCITOLOGICO",
        "COLPOCITOLOGICO",
        "COLPOCITOLOGIA ONCOTICA TRIPLICE",
        "COLPOCITOLOGIA ONCOTICA DE ESFREGACO VAGINAL",
        "COLPOCITOLOGIA ONCOTICA",
        "COLPOCITOLOGIA ONCOPARASITARIA",
        "COLPOCITOLOGIA 2",
        "COLPOCITOLOGIA",
        "CITOPATOLOGICO VAGINAL ONCOTICO E MICROFLORA",
        "CITOLOGICO DE SECRECAO VAGINAL",
        "CITOLOGIA ONCOTICA-2A.AMOSTRA",
        "CITOLOGIA ONCOTICA",
        "CITOLOGIA DE SECRECAO DO CANAL VAGINAL",
        "CITOLOGIA DE MICROFLORA",
        "CITOLOGIA CERVICO VAGINAL",
        "CITOLOGIA ONCÓTICA, CERVICO-VAGINAL",
        "PREVENTIVO DO CANCER GINECOLOGICO",
        "PAPANICOLAOU"
    ],
    "material": {
        "id": 143,
        "nome": "Secreção Vaginal e Endocervical"
    },
    "marcasAtivas": [
        {
            "id": 1,
            "mnemonico": "Delboni",
            "nome": "Delboni Auriemo",
            "segmento": "Executive",
            "url": "http://www.delboniauriemo.com.br/",
            "whatsapp": "11 3049-6999",
            "urlAgendamento": "agendamentoonline.delboniauriemo.com.br/",
            "urlLaudo": "delboniauriemo.com.br/resultados-de-exames",
            "ufs": [
                "SP"
            ]
        }
        .
        .
        .
    ],
    "marcasInativas": [
        {
            "id": -1,
            "mnemonico": "Nao Informado",
            "nome": "Nao Informado",
            "segmento": "Standard",
            "ufs": []
        },
        {
            "id": 13,
            "mnemonico": "Vita",
            "nome": "Lâmina Medicina Diagnóstica - Santa Catarina",
            "segmento": "Executive",
            "url": "http://www.laminadiagnosticos.com.br/",
            "whatsapp": "48 98824-8555",
            "urlAgendamento": "agendamentoonline.laminadiagnosticos.com.br/",
            "urlLaudo": "laminadiagnosticos.com.br/resultados-de-exames",
            "ufs": [
                "SC"
            ]
        }
        .
        .
        .
    ],
    "errors": []
}
```
## End-Point Detalhe Marca 

* URl : localhost:8090/v1/exame/7332/marca/1/unidades
* METHOD : get
* AUTHORIZATION  : Bearer token
* REQUEST  :
```
{
    "unidades": [
        {
            "id": 697,
            "nome": "ALTO DE PINHEIROS",
            "preco": "R$ 137,00",
            "prazoHora": 96,
            "prazoHoraUrgente": 96,
            "permitePrazoUrgente": false,
            "endereco": {
                "id": 697,
                "cidade": "SÃO PAULO",
                "uf": "SP",
                "logradouro": "R. CAPEPUXIS",
                "numeroComercial": "429",
                "bairro": "ALTO DE PINHEIROS",
                "pontoReferencia": "PRÓXIMO À PONTE CIDADE UNIVERSITÁRIA (SENTIDO CEAGESP)",
                "cep": "05452-030",
                "regiao": "Zona Oeste"
            }
        }
     ],
    "errors": []
}
```