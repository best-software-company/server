# Web Service do projeto HomeTasks

Documentação disponível em [HomeTasks - Docs](https://github.com/best-software-company/docs)

> Em Construção

## Como montar localmente um ambiente de DEV para o HomeTasks Server

### Pré-requisitos

* IntelliJ IDEA (com Gradle)
* MySQL Server com banco de dados do projeto HomeTasks

### Importando projeto no IntelliJ

1. Caso já tenha usado algum projeto com Gradle anteriormente, recomendasse limpar o diretório `.gradle ` do seu home.

   ```bash
   $ sudo rm -r ~/.gradle
   ```

1. Faça o download do repositório Server para o diretório de sua preferência.

   ```bash
   $ git clone https://github.com/best-software-company/server.git
   ```

2. No tela inicial do IntelliJ, selecione a opção `Import Project`  e após informe o diretório do repositório baixado no item 2. 

3. Na tela seguinte, selecione a opção `Import project from external_model` , indique o Plugin `Gradle` na lista e clique no botão `Finish`.

4. Na tela de projeto, a mensagem `IntelliJ IDEA found a Gradle build script` será exibida. Clique em `Import Gradle Project` e aguarde enquanto o projeto é importado.

	* **Obs.:** Verifique no rodapé a mensagem `processes running...` ou `Indexing...` , que não será mais exibida quando a importação terminar.  

### Configurando Banco de Dados

> Ainda não inserido arquivo de configuração banco de dados. Deve-se alterar no código.

1. Edite o arquivo `config.properties`

```properties
host.mysql=127.0.0.1 
port.mysql=3306
dbname=name_database
user.mysql=####
password.mysql=####
```
