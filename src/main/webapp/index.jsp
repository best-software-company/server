<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>API REST</title>
  </head>
  <h1 id="documenta-o-da-api-do-web-service">Documentação da API do Web Service</h1>
  <blockquote>
      <p>O documento descreve API REST do Serviço Web fornecido pela solução Home Tasks. </p>
  </blockquote>
  <hr>
  <p><strong>OBS.:</strong> Todas as requisições efetuadas, com exceção do Registro, necessitam que o usuário esteja previamente autenticado. Por isso o envio de um Token, retornado do processo de autenticação, no cabeçalho é <code>OBRIGATÓRIO</code>. <code>token: &quot;token&quot;</code></p>
  <hr>
  <p>Os seguintes erros podem ser retornados em qualquer requisição, que necessite de autenticação, caso o Usuário não informe o token ou não tenha permissão.</p>
  <p><strong>Código de resposta de erro:</strong><code>401 UNAUTHORIZED</code></p>
  <p>Quando é feita a requisição sem informar o token ou o mesmo não é válido.</p>
  <p><strong>Corpo da resposta:</strong></p>
  <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Autenticação Necessária"</span>
}
</code></pre>
  <p><strong>Código de resposta de erro:</strong><code>403 FORBIDDEN</code></p>
  <p>Caso o usuário autenticado tenha um token válido, mas não tenha permissão para efetuar a requisição.</p>
  <p><strong>Corpo da resposta:</strong></p>
  <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Permissão Negada"</span>
}
</code></pre>
  <p><strong>URL Base:</strong> <code>/hometasks/api/v1/</code></p>
  <h2 id="autentica-o">Autenticação</h2>
  <p>Endpoint: <strong><code>/login</code></strong></p>
  <h4 id="post-login">POST /login</h4>
  <p>Realiza a autenticação do usuário junto ao servidor. Necessário o envio das credenciais do usuário no cabeçalho da requisição. Retorna o token de autenticação caso a autenticação tenha ocorrido com sucesso.</p>
  <ul>
      <li><p><strong>Requisitos:</strong> </p>
          <p>Cabeçalho <code>Authorization: Basic &lt;idUsuario:senha&gt;</code>, com <code>idUsuario:senha</code> codificados em Base 64, na requisição.</p>
      </li>
      <li><p><strong>Código  de resposta de sucesso:</strong><code>200 OK</code></p>
          <p>Autenticação realizada com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"token"</span>: <span class="hljs-string">"7ba40d1a6034ac67a2805bfca21cbbf723d0311b"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <ol>
              <li>Credenciais não enviadas no cabeçalho Authorization da Requisição.</li>
              <li><p><strong>Corpo da resposta:</strong></p>
                  <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Credenciais não enviadas no cabeçalho Authorization"</span>
}
</code></pre>
              </li>
              <li>Formato de envio das credenciais não foi reconhecido.</li>
              <li><p><strong>Corpo da resposta:</strong></p>
                  <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Formato das credenciais inválido. Correto - Base64(idUsuario:senha)"</span>
}
</code></pre>
              </li>
          </ol>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>  Usuário não encontrado.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
      <span class="hljs-attr">"error"</span>: <span class="hljs-string">"idUsuario inválido ou inexistente"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>401 UNAUTHORIZED</code></p>
          <p>  Senha inválida.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
      <span class="hljs-attr">"error"</span>: <span class="hljs-string">"senha inválida"</span>
}
</code></pre>
      </li>
  </ul>
  <hr>
  <h2 id="1-usu-rio">1. Usuário</h2>
  <p>Gerenciar informações sobre o usuário e verificar informações dos demais usuários.</p>
  <p>Endpoint: <strong><code>/users</code></strong></p>
  <h4 id="post-users">POST /users</h4>
  <p>Executa o registro de um novo usuário no servidor. O corpo da requisição contém todos os parâmetros do Usuário em formato <code>application/json</code>. Retorna também em formato <code>application/json</code> os dados do Usuário recém criado.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Os seguintes atributos são obrigatórios no corpo da requisição:<code>idUsuario</code>, <code>nome</code>, <code>senha</code>, <code>data</code>, <code>genero</code>, <code>perfil</code>, <code>telefone</code> e <code>email</code>     </p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
          <span class="hljs-attr">"idUsuario"</span>: <span class="hljs-string">"idUsuario-2"</span>,
          <span class="hljs-attr">"nome"</span>: <span class="hljs-string">"Usuario 2"</span>,
          <span class="hljs-attr">"data"</span>: <span class="hljs-string">"1900-02-02"</span>,
          <span class="hljs-attr">"genero"</span>: <span class="hljs-string">"Feminino"</span>,
          <span class="hljs-attr">"pontos"</span>: <span class="hljs-number">0</span>,
          <span class="hljs-attr">"telefone"</span>: <span class="hljs-string">"88888888"</span>,
          <span class="hljs-attr">"senha"</span>: <span class="hljs-string">"senha"</span>,
          <span class="hljs-attr">"email"</span>: <span class="hljs-string">"usuario2@bsc.com"</span>,
          <span class="hljs-attr">"perfil"</span>: <span class="hljs-string">"Morador"</span>,
          <span class="hljs-attr">"idCasa"</span>: <span class="hljs-number">0</span>,
        <span class="hljs-attr">"foto"</span>: <span class="hljs-literal">null</span>,
        <span class="hljs-attr">"token"</span>: <span class="hljs-literal">null</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
          <p>Usuário criado com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
        <span class="hljs-attr">"idUsuario"</span>: <span class="hljs-string">"idUsuario-2"</span>,
        <span class="hljs-attr">"nome"</span>: <span class="hljs-string">"Usuario 2"</span>,
        <span class="hljs-attr">"data"</span>: <span class="hljs-string">"1900-02-02"</span>,
        <span class="hljs-attr">"genero"</span>: <span class="hljs-string">"Feminino"</span>,
        <span class="hljs-attr">"pontos"</span>: <span class="hljs-number">0</span>,
        <span class="hljs-attr">"telefone"</span>: <span class="hljs-string">"88888888"</span>,
        <span class="hljs-attr">"senha"</span>: <span class="hljs-literal">null</span>,
        <span class="hljs-attr">"email"</span>: <span class="hljs-string">"usuario2@bsc.com"</span>,
        <span class="hljs-attr">"perfil"</span>: <span class="hljs-string">"Morador"</span>,
          <span class="hljs-attr">"idCasa"</span>: <span class="hljs-number">0</span>,
          <span class="hljs-attr">"foto"</span>: <span class="hljs-literal">null</span>,
          <span class="hljs-attr">"token"</span>: <span class="hljs-literal">null</span>
  }
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Quando algum campo obrigatório não foi informado no corpo da solicitação. </p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Atributos Obrigatórios - idUsuario, nome, senha, data, genero, ..."</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>409 CONFLICT</code></p>
          <p>Quando já existe um usuário com mesmo idUsuario registrado.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Login já existe"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="get-users-idusuario-">GET /users/{idUsuario}</h4>
  <p>Recuperar as informações do(s) usuário(s) solicitado por {idUsuario} enviado como parâmetro da URL. Retorna os dados em formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho <code>token</code> da requisição.</p>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>200 OK</code></p>
          <p>Usuário(s) encontrado(s).</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">[
    {
        <span class="hljs-attr">"idUsuario"</span>: <span class="hljs-string">"idUsuario-1"</span>,
        <span class="hljs-attr">"nome"</span>: <span class="hljs-string">"Usuario 1"</span>,
        <span class="hljs-attr">"data"</span>: <span class="hljs-string">"1900-01-01"</span>,
        <span class="hljs-attr">"genero"</span>: <span class="hljs-string">"Masculino"</span>,
        <span class="hljs-attr">"pontos"</span>: <span class="hljs-number">0</span>,
        <span class="hljs-attr">"telefone"</span>: <span class="hljs-string">"9999999"</span>,
        <span class="hljs-attr">"senha"</span>: <span class="hljs-literal">null</span>,
        <span class="hljs-attr">"email"</span>: <span class="hljs-string">"usuario1@bsc.com"</span>,
        <span class="hljs-attr">"perfil"</span>: <span class="hljs-string">"Morador"</span>,
        <span class="hljs-attr">"idCasa"</span>: <span class="hljs-number">0</span>,
        <span class="hljs-attr">"foto"</span>: <span class="hljs-literal">null</span>,
        <span class="hljs-attr">"token"</span>: <span class="hljs-literal">null</span>
    },
    {
        <span class="hljs-attr">"idUsuario"</span>: <span class="hljs-string">"idUsuario-2"</span>,
        <span class="hljs-attr">"nome"</span>: <span class="hljs-string">"Usuario 2"</span>,
        <span class="hljs-attr">"data"</span>: <span class="hljs-string">"1900-02-02"</span>,
        <span class="hljs-attr">"genero"</span>: <span class="hljs-string">"Feminino"</span>,
        <span class="hljs-attr">"pontos"</span>: <span class="hljs-number">0</span>,
        <span class="hljs-attr">"telefone"</span>: <span class="hljs-string">"88888888"</span>,
        <span class="hljs-attr">"senha"</span>: <span class="hljs-literal">null</span>,
        <span class="hljs-attr">"email"</span>: <span class="hljs-string">"usuario2@bsc.com"</span>,
        <span class="hljs-attr">"perfil"</span>: <span class="hljs-string">"Morador"</span>,
        <span class="hljs-attr">"idCasa"</span>: <span class="hljs-number">0</span>,
        <span class="hljs-attr">"foto"</span>: <span class="hljs-literal">null</span>,
        <span class="hljs-attr">"token"</span>: <span class="hljs-literal">null</span>
    }
]
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Usuário(s) não encontrado(s) de acordo com idUsuario informado na URL da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Nenhum usuário encontrado"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>405 METHOD NOT ALLOWED</code></p>
          <p>Nenhum parâmetro foi informado na URL</p>
      </li>
  </ul>
  <h4 id="put-users">PUT /users</h4>
  <p>Executa a alteração dos dados do usuário no servidor. O corpo da requisição deve conter todos os parâmetros do Usuário que deseja ser alterado em formato <code>application/json</code>. </p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho <code>token</code> da requisição.</p>
          <p>Os seguintes atributos são obrigatórios no corpo da requisição:<code>idUsuario</code>, <code>nome</code>, <code>data</code>, <code>genero</code>, <code>perfil</code>, <code>telefone</code> e <code>email</code></p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
        <span class="hljs-attr">"idUsuario"</span>: <span class="hljs-string">"idUsuario-1"</span>,
        <span class="hljs-attr">"nome"</span>: <span class="hljs-string">"Usuario 1"</span>,
        <span class="hljs-attr">"data"</span>: <span class="hljs-string">"1900-02-02"</span>,
        <span class="hljs-attr">"genero"</span>: <span class="hljs-string">"Feminino"</span>,
        <span class="hljs-attr">"telefone"</span>: <span class="hljs-string">"7777777"</span>,
        <span class="hljs-attr">"email"</span>: <span class="hljs-string">"usuario2@bsc.com"</span>,
        <span class="hljs-attr">"perfil"</span>: <span class="hljs-string">"Morador"</span>,

}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
          <p>Usuário atualizado com sucesso. Sem corpo de resposta.</p>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Quando algum campo obrigatório não foi informado no corpo da solicitação.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Atributos Obrigatórios - idUsuario, nome, senha, data, genero, ..."</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Quando ocorre algum erro na atualização do Usuário no Banco de Dados.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Erro ao atualizar Usuário no Banco"</span>
}
</code></pre>
      </li>
  </ul>
  <hr>
  <h2 id="2-tarefa">2. Tarefa</h2>
  <p>Gerenciar informações sobre as tarefas</p>
  <p>Endpoint: <strong><code>/tasks</code></strong></p>
  <h4 id="get-tasks">GET /tasks</h4>
  <p>Recuperar as tarefas com status <code>aberta</code> relacionadas ao usuário autenticado e as tarefas com status <code>finalizada</code> da Casa para avaliação. Retorna uma lista de tarefas no formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho.</p>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>200 OK</code></p>
          <p>Tarefas relacionadas ao usuário encontradas.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">[
    {
        <span class="hljs-attr">"id"</span>:<span class="hljs-number">1</span>,
        <span class="hljs-attr">"task_name"</span>: <span class="hljs-string">"lavar louça"</span>,
        <span class="hljs-attr">"description"</span>: <span class="hljs-string">"Lavar louça do almoço todos os dias"</span>,
        <span class="hljs-attr">"user_id"</span>:<span class="hljs-number">1</span>,
        <span class="hljs-attr">"status"</span>:<span class="hljs-string">"aberta"</span>,
        <span class="hljs-attr">"date_limit"</span>:<span class="hljs-string">"01/01/1900 23:23"</span>,
        <span class="hljs-attr">"Pontos"</span>:<span class="hljs-number">60</span>
    },
    {
        <span class="hljs-attr">"id"</span>:<span class="hljs-number">2</span>,
        <span class="hljs-attr">"task_name"</span>: <span class="hljs-string">"recolher lixo"</span>,
        <span class="hljs-attr">"description"</span>: <span class="hljs-string">"recolher lixo da casa"</span>,
        <span class="hljs-attr">"status"</span>:<span class="hljs-string">"finalizada"</span>,
        <span class="hljs-attr">"user_id"</span>:<span class="hljs-number">3</span>,
        <span class="hljs-attr">"date_limit"</span>:<span class="hljs-string">"01/01/1900 23:23"</span>,
        <span class="hljs-attr">"Pontos"</span>:<span class="hljs-number">60</span>
    }
]
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong> <code>404 NOT FOUND</code></p>
          <p>Caso o usuário autenticado não possua nenhuma tarefa com status <code>aberta</code> ou não possuir nenhuma tarefa com status <code>finalizada</code> para avalização.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Nenhuma tarefa encontrada"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="post-tasks">POST /tasks</h4>
  <p>Executa o cadastro de uma nova tarefa no servidor. O corpo da requisição contém todos os parâmetros da tarefa em formato <code>application/json</code>. Retorna os dados da tarefa em <code>json</code> caso sejá criada com sucesso.</p>
  <ul>
      <li><strong>Requisitos:</strong></li>
  </ul>
  <p>Os atributos <code>task_name</code> e <code>user_id</code> são obrigatórios no corpo da requisição.</p>
  <ul>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"task_name"</span>: <span class="hljs-string">"recolher lixo"</span>,
    <span class="hljs-attr">"description"</span>: <span class="hljs-string">"recolher lixo da casa"</span>,
    <span class="hljs-attr">"user_id"</span>:<span class="hljs-number">1</span>,
    <span class="hljs-attr">"date_limit"</span>:<span class="hljs-string">"01/01/1900 23:23"</span>,
    <span class="hljs-attr">"Pontos"</span>:<span class="hljs-number">60</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
          <p>Tarefa criada com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">5</span>,
    <span class="hljs-attr">"task_name"</span>: <span class="hljs-string">"recolher lixo"</span>,
    <span class="hljs-attr">"description"</span>: <span class="hljs-string">"recolher lixo da casa"</span>,
    <span class="hljs-attr">"status"</span>:<span class="hljs-string">"finalizada"</span>,
    <span class="hljs-attr">"user_id"</span>:<span class="hljs-number">3</span>,
    <span class="hljs-attr">"date_limit"</span>:<span class="hljs-string">"01/01/1900 23:23"</span>,
    <span class="hljs-attr">"Pontos"</span>:<span class="hljs-number">60</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Caso algum atributo obrigatório não tenha sido enviado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>: <span class="hljs-string">"Atributos Obrigatórios:task_name e user_id"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="put-tasks">PUT /tasks</h4>
  <p>Executa a alteração dos dados tarefa no servidor. O corpo da requisição deve conter todos os parâmetros da tarefa que serão atualizado em formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>O atributo <code>id</code> da tarefa é obrigatório no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">5</span>,
    <span class="hljs-attr">"status"</span>:<span class="hljs-string">"finalizada"</span>,
    <span class="hljs-attr">"Comentarios"</span>:
    [
        {
            <span class="hljs-attr">"comentario1"</span>:<span class="hljs-string">"Tarefa bem realizada"</span>,
            <span class="hljs-attr">"comentario2"</span>:<span class="hljs-string">"OK"</span>
        }
    ]
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
          <p>Tarefa atualizada com sucesso. Sem corpo de resposta.</p>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Tarefa não encontrada para atualização.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Tarefa não encontrada"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Atributo <code>id</code> não informado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributo id obrigatório"</span>
}
</code></pre>
      </li>
  </ul>
  <hr>
  <h2 id="3-rotina">3. Rotina</h2>
  <p>Gerenciar informações das Rotinas do usuário</p>
  <p>Endpoint: <strong><code>/routines</code></strong></p>
  <h4 id="get-routines">GET /routines</h4>
  <p>Recuperar as rotinas relacionadas ao usuário autenticado. Retorna os dados em formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho.</p>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>200 OK</code></p>
          <p>Rotinas do usuário encontradas.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">[
    {
        <span class="hljs-attr">"id"</span>:<span class="hljs-number">1</span>,
        <span class="hljs-attr">"routine_name"</span>: <span class="hljs-string">"Rotina 1"</span>,
        <span class="hljs-attr">"description"</span>: <span class="hljs-string">"Descrição da rotina"</span>,
        <span class="hljs-attr">"days"</span>:<span class="hljs-string">"seg,ter,qui"</span>,
        <span class="hljs-attr">"hour_limit"</span>:<span class="hljs-string">"23:23"</span>,
        <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"alternar"</span>,
        <span class="hljs-attr">"date_val"</span>:<span class="hljs-string">"01/01/2000"</span>
    },
    {
        <span class="hljs-attr">"id"</span>:<span class="hljs-number">2</span>,
        <span class="hljs-attr">"routine_name"</span>: <span class="hljs-string">"Rotina 2"</span>,
        <span class="hljs-attr">"description"</span>: <span class="hljs-string">"Descrição da rotina 2"</span>,
        <span class="hljs-attr">"days"</span>:<span class="hljs-string">"seg,sex"</span>,
        <span class="hljs-attr">"hour_limit"</span>:<span class="hljs-string">"23:23"</span>,
        <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"todos"</span>,
        <span class="hljs-attr">"date_val"</span>:<span class="hljs-string">"01/01/2000"</span>
    }
]
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Caso usuário autenticado não possua nenhuma rotina registrada.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Nenhuma rotina registrada"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="post-routines">POST /routines</h4>
  <p>Executa o cadastro de uma nova rotina no servidor. O corpo da requisição contém todos os parâmetros da rotina em formato <code>application/json</code>. Retorna os dados da Rotina recem criada.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Atributo <code>routine_name</code> é obrigatório no corpo da requisição</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"routine_name"</span>: <span class="hljs-string">"Rotina 2"</span>,
    <span class="hljs-attr">"description"</span>: <span class="hljs-string">"Descrição da rotina 2"</span>,
    <span class="hljs-attr">"days"</span>:<span class="hljs-string">"seg,sex"</span>,
    <span class="hljs-attr">"hour_limit"</span>:<span class="hljs-string">"23:23"</span>,
    <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"todos"</span>,
    <span class="hljs-attr">"date_val"</span>:<span class="hljs-string">"01/01/2000"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
          <p>Rotina criada com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">3</span>,
    <span class="hljs-attr">"routine_name"</span>: <span class="hljs-string">"Rotina 2"</span>,
    <span class="hljs-attr">"description"</span>: <span class="hljs-string">"Descrição da rotina 2"</span>,
    <span class="hljs-attr">"days"</span>:<span class="hljs-string">"seg,sex"</span>,
    <span class="hljs-attr">"hour_limit"</span>:<span class="hljs-string">"23:23"</span>,
    <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"todos"</span>,
    <span class="hljs-attr">"date_val"</span>:<span class="hljs-string">"01/01/2000"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Se o atributo obrigatório não for enviado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code>{
    <span class="hljs-attr">"error"</span> : <span class="hljs-string">"Atributo routine_name obrigatório"</span>
}
</code></pre></li>
  </ul>
  <h4 id="put-routines">PUT /routines</h4>
  <p>Executa a alteração do dados da rotina no servidor. Os atributos que irão ser atualizados devem ser enviados no corpo da requisição em formato <code>application/json</code>. </p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Atributo <code>id</code> da rotina é obrigatório no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">2</span>,
    <span class="hljs-attr">"routine_name"</span>: <span class="hljs-string">"Rotina 2"</span>,
    <span class="hljs-attr">"description"</span>: <span class="hljs-string">"Descrição da rotina 2"</span>,
    <span class="hljs-attr">"days"</span>:<span class="hljs-string">"seg,sex"</span>,
    <span class="hljs-attr">"hour_limit"</span>:<span class="hljs-string">"23:23"</span>,
    <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"todos"</span>,
    <span class="hljs-attr">"date_val"</span>:<span class="hljs-string">"01/01/2000"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
          <p>Rotina atualizada com sucesso. Sem corpo de resposta.</p>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Rotina não encontrada para atualização.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Rotina não encontrada"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Atributo <code>id</code> da rotina não informado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributo id obrigatório"</span>
}
</code></pre>
      </li>
  </ul>
  <hr>
  <h2 id="4-casa">4. Casa</h2>
  <p>Gerenciar informações da Casa</p>
  <p>Endpoint: <strong><code>/home</code></strong></p>
  <h4 id="get-home-name_home-">GET /home/{name_home}</h4>
  <p>Recuperar as informações da casa solicitada pelo parâmetro {name_home}. Retorna os dados em formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho.</p>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>200 OK</code></p>
          <p>Casa encontrada.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">1</span>,
    <span class="hljs-attr">"home_name"</span>: <span class="hljs-string">"Casa 1"</span>,
    <span class="hljs-attr">"address"</span>: <span class="hljs-string">"Endereço"</span>,
    <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"name"</span>,
    <span class="hljs-attr">"aluguel"</span>:<span class="hljs-number">200</span>,
    <span class="hljs-attr">"foto"</span>:<span class="hljs-string">"foto.png"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Casa não encontrada de acordo com o parâmetro informado na URL da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-Json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Rotina não encontrada"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="post-home">POST /home</h4>
  <p>Executa o cadastro de uma nova Casa no servidor. O corpo da requisição contém todos os parâmetros para cadastro da Casa em formato <code>application/json</code>. Retorna os dados em formado <code>json</code> da Casa cadastrada.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Os seguintes atributos são obrigatórios no corpo da requisição: <code>home_name</code>, <code>address</code>, <code>responsavel</code> e <code>aluguel</code>.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"home_name"</span>: <span class="hljs-string">"Casa 2"</span>,
    <span class="hljs-attr">"address"</span>: <span class="hljs-string">"Endereço 2"</span>,
    <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"name"</span>,
    <span class="hljs-attr">"aluguel"</span>:<span class="hljs-number">200</span>,
    <span class="hljs-attr">"foto"</span>:<span class="hljs-string">"foto.png"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
          <p>Casa cadastrada com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">2</span>,
    <span class="hljs-attr">"home_name"</span>: <span class="hljs-string">"Casa 2"</span>,
    <span class="hljs-attr">"address"</span>: <span class="hljs-string">"Endereço"</span>,
    <span class="hljs-attr">"responsavel"</span>:<span class="hljs-string">"name"</span>,
    <span class="hljs-attr">"aluguel"</span>:<span class="hljs-number">200</span>,
    <span class="hljs-attr">"foto"</span>:<span class="hljs-string">"foto.png"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Algum campo obrigatório não foi enviado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributos obrigatórios - home_name, address, responsavel e aluguel"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="put-home">PUT /home</h4>
  <p>Executa a alteração do dados da Casa no servidor. O corpo da requisição deve conter os parâmetros para alteração em formato <code>application/json</code>. </p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>É obrigatório o envio do atributo <code>id</code> no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">1</span>,
    <span class="hljs-attr">"home_name"</span>: <span class="hljs-string">"Casa 1"</span>,
    <span class="hljs-attr">"aluguel"</span>:<span class="hljs-number">300</span>,
    <span class="hljs-attr">"foto"</span>:<span class="hljs-string">"foto.png"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
          <p>Casa atualizada com sucesso. Sem corpo de resposta.</p>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Se a Casa com id informado não for encontrada.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Casa não encontrada"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Se o atributo obrigatório não foi informado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributo id obrigatório"</span>
}
</code></pre>
      </li>
  </ul>
  <hr>
  <h2 id="5-conta">5. Conta</h2>
  <p>Gerenciar informações da Conta do Usuário</p>
  <p>Endpoint: <strong><code>/account</code></strong></p>
  <h4 id="get-account-">GET /account/</h4>
  <p>Recuperar as informações da Conta do usuário autenticado. Retorna os dados em formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho da requisição.</p>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>200 OK</code></p>
          <p>Contas dos usuários encontradas com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">[
    {
        <span class="hljs-attr">"id"</span>:<span class="hljs-number">1</span>,
        <span class="hljs-attr">"id_user_cred"</span>: <span class="hljs-number">4</span>,
        <span class="hljs-attr">"valor"</span>: <span class="hljs-number">10</span>,
        <span class="hljs-attr">"pago"</span>:<span class="hljs-literal">true</span>,
    },
    {
        <span class="hljs-attr">"id"</span>:<span class="hljs-number">2</span>,
        <span class="hljs-attr">"id_user_cred"</span>: <span class="hljs-number">3</span>,
        <span class="hljs-attr">"valor"</span>: <span class="hljs-number">25</span>,
        <span class="hljs-attr">"pago"</span>:<span class="hljs-literal">false</span>,
    }
]
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Caso o usuário autenticado ainda não possua nenhuma conta.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Conta não encontrada"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="put-account">PUT /account</h4>
  <p>Executa a alteração nas informações da Conta do Usuário. O corpo da requisição contém todos os parâmetros para alteração em formato <code>application/json</code>. </p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>O atributo <code>id</code> da conta é obrigatório no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id"</span>:<span class="hljs-number">2</span>,
    <span class="hljs-attr">"valor"</span>: <span class="hljs-number">25</span>,
    <span class="hljs-attr">"pago"</span>:<span class="hljs-literal">true</span>,
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
          <p>Conta atualizada com sucesso. Sem corpo de resposta.</p>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Se a Conta com <code>id</code> informado não for encontrada.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Conta não encontrada"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Se o atributo obrigatório não foi informado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributo id obrigatório"</span>
}
</code></pre>
      </li>
  </ul>
  <hr>
  <h2 id="6-regras">6. Regras</h2>
  <p>Gerenciar informações das Regras da Casa</p>
  <p>Endpoint: <strong><code>/rules</code></strong></p>
  <h4 id="get-rules-home_id-">GET /rules/{home_id}</h4>
  <p>Recuperar as regras da Casa informado pelo parâmetro {home_id}. Retorna os dados em formato <code>application/json</code>.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Token de autenticação enviado no cabeçalho da requisição.</p>
      </li>
      <li><p><strong>Código de reposta de sucesso:</strong><code>200 OK</code></p>
          <p>Regras encontradas com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">[
    {
        <span class="hljs-attr">"id_regra"</span>:<span class="hljs-number">1</span>,
        <span class="hljs-attr">"id_home"</span>:<span class="hljs-number">4</span>,
        <span class="hljs-attr">"descricao"</span>:<span class="hljs-string">"Porta sempre trancada"</span>
    },
    {
        <span class="hljs-attr">"id_regra"</span>:<span class="hljs-number">2</span>,
        <span class="hljs-attr">"id_home"</span>:<span class="hljs-number">4</span>,
        <span class="hljs-attr">"descricao"</span>:<span class="hljs-string">"Tarefa não executada acrescenta 10 reais no aluguel"</span>
    }
]
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Regras não encontradas de acordo com o parâmetro informado na URL da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-Json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Regras não encontradas"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="post-rules">POST /rules</h4>
  <p>Executa o cadastro de uma nova regra na Casa. O corpo da requisição contém todos os parâmetros para cadastro da Regra em formato <code>application/json</code>. Retorna os dados em formado <code>json</code> da nova Regra cadastrada.</p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>Os seguintes atributos são obrigatórios no corpo da requisição: <code>id_home</code> e <code>descricao</code>.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id_home"</span>:<span class="hljs-number">4</span>,
    <span class="hljs-attr">"descricao"</span>:<span class="hljs-string">"Nova regra"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
          <p>Casa cadastrada com sucesso.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id_regra"</span>:<span class="hljs-number">3</span>,
    <span class="hljs-attr">"id_home"</span>:<span class="hljs-number">4</span>,
    <span class="hljs-attr">"descricao"</span>:<span class="hljs-string">"nova regra"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Algum campo obrigatório não foi enviado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributos obrigatórios - id_home e descricao"</span>
}
</code></pre>
      </li>
  </ul>
  <h4 id="put-rules">PUT /rules</h4>
  <p>Executa a alteração nas informações das regras da Casa. O corpo da requisição contém todos os parâmetros para alteração em formato <code>application/json</code>. </p>
  <ul>
      <li><p><strong>Requisitos:</strong></p>
          <p>O atributo <code>id_regra</code>  e <code>id_home</code> são obrigatórios no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da requisição:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"id_regra"</span>:<span class="hljs-number">2</span>,
    <span class="hljs-attr">"id_home"</span>:<span class="hljs-number">4</span>,
    <span class="hljs-attr">"descricao"</span>:<span class="hljs-string">"Tarefa não executada acrescenta 20 reais no aluguel"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
          <p>Regra atualizada com sucesso. Sem corpo de resposta.</p>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
          <p>Se a Regra com <code>id_regra</code> informado não for encontrada.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Regra não encontrada"</span>
}
</code></pre>
      </li>
      <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
          <p>Se o atributo obrigatório não foi informado no corpo da requisição.</p>
      </li>
      <li><p><strong>Corpo da resposta:</strong></p>
          <pre><code class="lang-json">{
    <span class="hljs-attr">"error"</span>:<span class="hljs-string">"Atributos id_regra e id_home são obrigatórios"</span>
}
</code></pre>
      </li>
  </ul>

</html>
