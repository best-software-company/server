<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>API REST</title>
  </head>
  <h1 id="documenta-o-da-api-do-web-service">Documentação da API REST do Web Service</h1>
  <p>O documento descreve a forma de utilização da API REST do Serviço Web fornecido pelo servidor da solução Home Tasks. </p>
  <hr>
  <p><strong>OBS.:</strong> Todas as requisições efetuadas necessitam que o usuário esteja previamente autenticado. Por isso o envio de um JWT (JSON Web Token), retornado do processo de autenticação, no cabeçalho é <code>OBRIGATÓRIO</code>. <code>Authorization: Bearer &lt;JWT&gt;</code></p>
  <hr>
  <p>Os seguintes erros podem ser retornados em qualquer requisição caso o Usuário não esteja autenticado ou não tenha permissão.</p>
  <p><strong>Código de resposta de erro:</strong><code>401 UNAUTHORIZED</code></p>
  <p>Quando é feita a requisição sem informar o token de autenticação no cabeçalho.</p>
  <p><strong>Corpo da resposta:</strong></p>
  <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Autenticação Necessária&quot;
}
</code></pre>
  <p><strong>Código de resposta de erro:</strong><code>403 FORBIDDEN</code></p>
  <p>Caso o usuário autenticado não tenha permissão para efetuar a requisição.</p>
  <p><strong>Corpo da resposta:</strong></p>
  <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Permissão Negada&quot;
}
</code></pre>
  <p>Endpoint Geral: <code>/api/v1/hometasks</code></p>
  <h2 id="1-usu-rio">1. Usuário</h2>
  <p>Gerenciar informações sobre o usuário e verificar informações dos demais usuários.</p>
  <p>Endpoint: <strong><code>/users</code></strong></p>
  <h4 id="get-users-name-or-email-">GET /users/{name or email}</h4>
  <p>Recuperar as informações do usuário solicitado por {name} ou {email} enviado na URL. Retorna os dados em formato <code>application/json</code>.</p>
  <ul>
    <li><p><strong>Requisitos:</strong></p>
      <p>Token de autenticação (JWT) enviado no cabeçalho da requisição.</p>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>200 OK</code></p>
      <p>Usuário encontrado.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;id&quot;: 1,
    &quot;full_name&quot;: &quot;username&quot;,
    &quot;cpf&quot;: &quot;123.456.789-00&quot;,
    &quot;login&quot;:&quot;login&quot;,
    &quot;telephone&quot;:&quot;9999-9999&quot;,
    &quot;genre&quot;:&quot;male&quot;,
    &quot;date_nasc&quot;:&quot;01/01/1900&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
      <p>Usuário não encontrado de acordo com os dados informados na URL da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Usuário não encontrado&quot;
}
</code></pre>
    </li>
  </ul>
  <h4 id="post-users">POST /users</h4>
  <p>Executa o registro de um novo usuário no servidor. O corpo da requisição contém todos os parâmetros do Usuário em formato <code>application/json</code>. Retorna também em formato <code>application/json</code> os dados, incluindo <code>id</code>, do Usuário recém criado.</p>
  <ul>
    <li><p><strong>Requisitos:</strong></p>
      <p>Os seguintes atributos são obrigatórios no corpo da requisição:<code>full_name</code>, <code>cpf</code>, <code>login</code> e <code>password</code>     </p>
    </li>
    <li><p><strong>Corpo da requisição:</strong></p>
      <pre><code class="lang-json">{
    &quot;full_name&quot;: &quot;username&quot;,
    &quot;cpf&quot;: &quot;123.456.789-00&quot;,
    &quot;login&quot;:&quot;login&quot;,
    &quot;password&quot;:&quot;password&quot;,
    &quot;telephone&quot;:&quot;9999-9999&quot;,
    &quot;genre&quot;:&quot;male&quot;,
    &quot;date_nasc&quot;:&quot;01/01/1900&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
      <p>Usuário criado com sucesso.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;id&quot;: 1,
    &quot;full_name&quot;: &quot;username&quot;,
    &quot;cpf&quot;: &quot;123.456.789-00&quot;,
    &quot;login&quot;:&quot;login&quot;,
    &quot;telephone&quot;:&quot;9999-9999&quot;,
    &quot;genre&quot;:&quot;male&quot;,
    &quot;date_nasc&quot;:&quot;01/01/1900&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Quando algum campo obrigatório não foi informado no corpo da solicitação. </p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Atributos Obrigatórios - full_name, cpf, login e password&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>409 CONFLICT</code></p>
      <p>Quando já existe um usuário com mesmo login registrado.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Login já existe&quot;
}
</code></pre>
    </li>
  </ul>
  <h4 id="post-login">POST /login</h4>
  <p>Realiza a autenticação do usuário junto ao servidor. Necessário o envio do <code>login</code> e <code>password</code> codificados em base 64 no cabeçalho da requisição: <code>Authorization: Basic &lt;Base64(login:password)&gt;</code>. Retorna o token de autenticação (JWT) caso a autenticação tenha ocorrido com sucesso.</p>
  <ul>
    <li><p><strong>Requisitos:</strong></p>
      <p>Cabeçalho <code>Authorization: Basic &lt;login:password&gt;</code>, com <code>login:password</code> codificados em Base 64, na requisição.</p>
    </li>
    <li><p><strong>Código  de resposta de sucesso:</strong><code>200 OK</code></p>
      <p>Autenticação realizada com sucesso.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;token&quot;:&quot;eyJhbGciOiJIUzI1NiIs.eyJ1bmlxdWVfbmFtZSI6IlR.SmjuyXgloA2RUhIlAEetrQwfC0Eh&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>401 UNAUTHORIZED</code></p>
      <p>Autenticação não pode ser realizada.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Usuário ou Senha inválidos ou inexistente&quot;
}
</code></pre>
    </li>
  </ul>
  <h4 id="put-users">PUT /users</h4>
  <p>Executa a alteração do dados do usuário no servidor. O corpo da requisição deve conter todos os parâmetros do Usuário que deseja ser alterado em formato <code>application/json</code>. </p>
  <ul>
    <li><p><strong>Requisitos:</strong></p>
      <p>O atributo <code>id</code> é obrigatório no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da requisição:</strong></p>
      <pre><code class="lang-json">{
    &quot;id&quot;:1,
    &quot;telephone&quot;:&quot;8888-9999&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>204 NO CONTENT</code></p>
      <p>Usuário atualizado com sucesso. Sem corpo de resposta.</p>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
      <p>Usuário não encontrado.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Usuário não encontrado&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Atributo <code>id</code> não foi informado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Atributo id obrigatório&quot;
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
        &quot;id&quot;:1,
        &quot;task_name&quot;: &quot;lavar louça&quot;,
        &quot;description&quot;: &quot;Lavar louça do almoço todos os dias&quot;,
        &quot;user_id&quot;:1,
        &quot;status&quot;:&quot;aberta&quot;,
        &quot;date_limit&quot;:&quot;01/01/1900 23:23&quot;,
        &quot;Pontos&quot;:60
    },
    {
        &quot;id&quot;:2,
        &quot;task_name&quot;: &quot;recolher lixo&quot;,
        &quot;description&quot;: &quot;recolher lixo da casa&quot;,
        &quot;status&quot;:&quot;finalizada&quot;,
        &quot;user_id&quot;:3,
        &quot;date_limit&quot;:&quot;01/01/1900 23:23&quot;,
        &quot;Pontos&quot;:60
    }
]
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong> <code>404 NOT FOUND</code></p>
      <p>Caso o usuário autenticado não possua nenhuma tarefa com status <code>aberta</code> ou não possuir nenhuma tarefa com status <code>finalizada</code> para avalização.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Nenhuma tarefa encontrada&quot;
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
    &quot;task_name&quot;: &quot;recolher lixo&quot;,
    &quot;description&quot;: &quot;recolher lixo da casa&quot;,
    &quot;user_id&quot;:1,
    &quot;date_limit&quot;:&quot;01/01/1900 23:23&quot;,
    &quot;Pontos&quot;:60
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
      <p>Tarefa criada com sucesso.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;id&quot;:5,
    &quot;task_name&quot;: &quot;recolher lixo&quot;,
    &quot;description&quot;: &quot;recolher lixo da casa&quot;,
    &quot;status&quot;:&quot;finalizada&quot;,
    &quot;user_id&quot;:3,
    &quot;date_limit&quot;:&quot;01/01/1900 23:23&quot;,
    &quot;Pontos&quot;:60
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Caso algum atributo obrigatório não tenha sido enviado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;: &quot;Atributos Obrigatórios:task_name e user_id&quot;
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
    &quot;id&quot;:5,
    &quot;status&quot;:&quot;finalizada&quot;,
    &quot;Comentarios&quot;:
    [
        {
            &quot;comentario1&quot;:&quot;Tarefa bem realizada&quot;,
            &quot;comentario2&quot;:&quot;OK&quot;
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
    &quot;error&quot;:&quot;Tarefa não encontrada&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Atributo <code>id</code> não informado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributo id obrigatório&quot;
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
        &quot;id&quot;:1,
        &quot;routine_name&quot;: &quot;Rotina 1&quot;,
        &quot;description&quot;: &quot;Descrição da rotina&quot;,
        &quot;days&quot;:&quot;seg,ter,qui&quot;,
        &quot;hour_limit&quot;:&quot;23:23&quot;,
        &quot;responsavel&quot;:&quot;alternar&quot;,
        &quot;date_val&quot;:&quot;01/01/2000&quot;
    },
    {
        &quot;id&quot;:2,
        &quot;routine_name&quot;: &quot;Rotina 2&quot;,
        &quot;description&quot;: &quot;Descrição da rotina 2&quot;,
        &quot;days&quot;:&quot;seg,sex&quot;,
        &quot;hour_limit&quot;:&quot;23:23&quot;,
        &quot;responsavel&quot;:&quot;todos&quot;,
        &quot;date_val&quot;:&quot;01/01/2000&quot;
    }
]
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
      <p>Caso usuário autenticado não possua nenhuma rotina registrada.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Nenhuma rotina registrada&quot;
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
    &quot;routine_name&quot;: &quot;Rotina 2&quot;,
    &quot;description&quot;: &quot;Descrição da rotina 2&quot;,
    &quot;days&quot;:&quot;seg,sex&quot;,
    &quot;hour_limit&quot;:&quot;23:23&quot;,
    &quot;responsavel&quot;:&quot;todos&quot;,
    &quot;date_val&quot;:&quot;01/01/2000&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
      <p>Rotina criada com sucesso.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;id&quot;:3,
    &quot;routine_name&quot;: &quot;Rotina 2&quot;,
    &quot;description&quot;: &quot;Descrição da rotina 2&quot;,
    &quot;days&quot;:&quot;seg,sex&quot;,
    &quot;hour_limit&quot;:&quot;23:23&quot;,
    &quot;responsavel&quot;:&quot;todos&quot;,
    &quot;date_val&quot;:&quot;01/01/2000&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Se o atributo obrigatório não for enviado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code>{
    &quot;error&quot; : &quot;Atributo routine_name obrigatório&quot;
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
    &quot;id&quot;:2,
    &quot;routine_name&quot;: &quot;Rotina 2&quot;,
    &quot;description&quot;: &quot;Descrição da rotina 2&quot;,
    &quot;days&quot;:&quot;seg,sex&quot;,
    &quot;hour_limit&quot;:&quot;23:23&quot;,
    &quot;responsavel&quot;:&quot;todos&quot;,
    &quot;date_val&quot;:&quot;01/01/2000&quot;
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
    &quot;error&quot;:&quot;Rotina não encontrada&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Atributo <code>id</code> da rotina não informado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributo id obrigatório&quot;
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
    &quot;id&quot;:1,
    &quot;home_name&quot;: &quot;Casa 1&quot;,
    &quot;address&quot;: &quot;Endereço&quot;,
    &quot;responsavel&quot;:&quot;name&quot;,
    &quot;aluguel&quot;:200,
    &quot;foto&quot;:&quot;foto.png&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
      <p>Casa não encontrada de acordo com o parâmetro informado na URL da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-Json">{
    &quot;error&quot;:&quot;Rotina não encontrada&quot;
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
    &quot;home_name&quot;: &quot;Casa 2&quot;,
    &quot;address&quot;: &quot;Endereço 2&quot;,
    &quot;responsavel&quot;:&quot;name&quot;,
    &quot;aluguel&quot;:200,
    &quot;foto&quot;:&quot;foto.png&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
      <p>Casa cadastrada com sucesso.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;id&quot;:2,
    &quot;home_name&quot;: &quot;Casa 2&quot;,
    &quot;address&quot;: &quot;Endereço&quot;,
    &quot;responsavel&quot;:&quot;name&quot;,
    &quot;aluguel&quot;:200,
    &quot;foto&quot;:&quot;foto.png&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Algum campo obrigatório não foi enviado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributos obrigatórios - home_name, address, responsavel e aluguel&quot;
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
    &quot;id&quot;:1,
    &quot;home_name&quot;: &quot;Casa 1&quot;,
    &quot;aluguel&quot;:300,
    &quot;foto&quot;:&quot;foto.png&quot;
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
    &quot;error&quot;:&quot;Casa não encontrada&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Se o atributo obrigatório não foi informado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributo id obrigatório&quot;
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
        &quot;id&quot;:1,
        &quot;id_user_cred&quot;: 4,
        &quot;valor&quot;: 10,
        &quot;pago&quot;:true,
    },
    {
        &quot;id&quot;:2,
        &quot;id_user_cred&quot;: 3,
        &quot;valor&quot;: 25,
        &quot;pago&quot;:false,
    }
]
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
      <p>Caso o usuário autenticado ainda não possua nenhuma conta.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Conta não encontrada&quot;
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
    &quot;id&quot;:2,
    &quot;valor&quot;: 25,
    &quot;pago&quot;:true,
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
    &quot;error&quot;:&quot;Conta não encontrada&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Se o atributo obrigatório não foi informado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributo id obrigatório&quot;
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
        &quot;id_regra&quot;:1,
        &quot;id_home&quot;:4,
        &quot;descricao&quot;:&quot;Porta sempre trancada&quot;
    },
    {
        &quot;id_regra&quot;:2,
        &quot;id_home&quot;:4,
        &quot;descricao&quot;:&quot;Tarefa não executada acrescenta 10 reais no aluguel&quot;
    }
]
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>404 NOT FOUND</code></p>
      <p>Regras não encontradas de acordo com o parâmetro informado na URL da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-Json">{
    &quot;error&quot;:&quot;Regras não encontradas&quot;
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
    &quot;id_home&quot;:4,
    &quot;descricao&quot;:&quot;Nova regra&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de sucesso:</strong><code>201 CREATED</code></p>
      <p>Casa cadastrada com sucesso.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;id_regra&quot;:3,
    &quot;id_home&quot;:4,
    &quot;descricao&quot;:&quot;nova regra&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Algum campo obrigatório não foi enviado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributos obrigatórios - id_home e descricao&quot;
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
    &quot;id_regra&quot;:2,
    &quot;id_home&quot;:4,
    &quot;descricao&quot;:&quot;Tarefa não executada acrescenta 20 reais no aluguel&quot;
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
    &quot;error&quot;:&quot;Regra não encontrada&quot;
}
</code></pre>
    </li>
    <li><p><strong>Código de resposta de erro:</strong><code>400 BAD REQUEST</code></p>
      <p>Se o atributo obrigatório não foi informado no corpo da requisição.</p>
    </li>
    <li><p><strong>Corpo da resposta:</strong></p>
      <pre><code class="lang-json">{
    &quot;error&quot;:&quot;Atributos id_regra e id_home são obrigatórios&quot;
}
</code></pre>
    </li>
  </ul>
</html>
