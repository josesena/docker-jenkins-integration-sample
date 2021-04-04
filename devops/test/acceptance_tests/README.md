# Automação do seviço Integracao-dataprovider-api com Cypress


# Pré-Requisitos para instalação
* Node instalado
* npm instalado
# Instruções para instalação
No diretório raiz do projeto rodar os comandos:
* npm i
* npm install cypress --save-dev


# Instruções para executar os testes
Executor do Cypress:
* npm run cypress:open

Executar os testes em modo interativo:
* npm run test

Executar os testes em modo headless:
* npm run test:hl

Limpar relatórios dos testes
* npm clean_all



# ESTRUTURA DO PROJETO

# Arquivo de configuração
* cypress/config/
* Obs: Por padrão, a configuração do projeto aponta para o ambiente de homologaçao (hml).
* Esta configuração pode ser alterada ou pode-se criar scripts que aponte para outros ambiente.
# As especificações dos testes de API são alocados no diretório abaixo:
* devops/test/acceptance_tests/cypress/integration/API

# Configuração de Plugins do Cypress
* devops/test/acceptance_tests/cypress/plugins
# Relatórios de execução dos testes
* devops/test/acceptance_tests/cypress/reports
# Comandos customizados para interagir com os serviços de API ficam alocados no diretório abaixo:
* devops/test/acceptance_tests/cypress/support/api_commands

# Diretóro onde são baixadas as dependencias do projeto (bibliotecas)
* devops/test/acceptance_tests/node_modules
# Arquivo onde são alocados os arquivos e diretórios que serão ignorados pelo GIT (repositório)
* devops/test/acceptance_tests/.gitignore
# Arquivo que indica as dependencias de projeto, contém os scrips como atalhos de execução dos testes e outras configurações
* devops/test/acceptance_tests/package.json
# Arquivo com explicações básicas da estrutura e uso do projeto
* devops/test/acceptance_tests/README.md