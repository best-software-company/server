package com.bsc.hometasks.rest;

import com.bsc.hometasks.db.dao.RotinaDAO;
import com.bsc.hometasks.db.dao.TarefaDAO;
import com.bsc.hometasks.db.dao.UsuarioDAO;
import com.bsc.hometasks.pojo.Rotina;
import com.bsc.hometasks.pojo.Tarefa;
import com.bsc.hometasks.pojo.Usuario;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/")
public class HomeTasks {
    UsuarioDAO user = new UsuarioDAO();
    TarefaDAO tarefa = new TarefaDAO();
    RotinaDAO rotina = new RotinaDAO();

    //testado
    @Path("/login/{credenciais}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response login(@PathParam("credenciais") String credenciais) {

        String log[] = credenciais.split(":");
        UsuarioDAO user = new UsuarioDAO();
        Usuario buscarUser = new Usuario();

        if(log.length==2){
            buscarUser = user.buscaUsuario(log[0]);
            if( buscarUser!=null){
                if(buscarUser.getSenha().equals(log[1])){
                    return Response.status(Response.Status.OK).entity("{\n" +
                            "    \"token\": \"true\"\n" +
                            "}").build();
                }
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Usuário ou Senha inválidos ou inexistente\"\n" +
                "}").build();

    }

    //testado
    //de alguma forma, cria todos os jsons e caso o usuario nao preencha volta uma string vazia
    @Path("/users")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addUser(Usuario newUser, @Context UriInfo uriInfo) {
        int count = verificaCampos(newUser);
        if(count>=8){
            if(user.buscaUsuariosId(newUser.getIdUsuario()).size()>0){
                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                        "    \"error\": \"\"Login já existe\"\n" +
                        "}").build();
            }

            if(user.criaUsuario(newUser)){
                //return Response.created(builder.build()).build();
                return Response.status(Response.Status.CREATED).entity(newUser).build();
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"\"Atributos Obrigatórios - Login, nome, senha, data, genero, perfil, telefone e email\"\n" +
                "}").build();

    }

    //testado
    @Path("/users/{nome}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response searchUsers(@PathParam("nome") String nome, @HeaderParam("token") String token) {
        if(token.equals("true")){
            List<Usuario> users = user.buscaUsuariosId(nome);
            if(users!=null) return Response.ok(users).build();
            else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhum usuário encontrado\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testado
    @Path("/users")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateUser(Usuario updateUser, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        if(token.equals("true")){
            if(user.atualizaUsuario(updateUser)  > 0){
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        "    \"error\": \"\"Usuário atualizado com sucesso.\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"\"Usuário não encontrado.\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/tasks/{idEstado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response searchTasks(String idEstado, @HeaderParam("token") String token) {
        String log[] = idEstado.split(":");
        if(token.equals("true")){
            List<Tarefa> tarefas = tarefa.buscaTarefasUsuarioEstado(log[0],log[1]);
            if(tarefas.size()>=0) return Response.status(Response.Status.OK).entity(tarefas).build();
            else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhuma tarefa encontrada\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //de alguma forma, cria todos os jsons e caso o usuario nao preencha volta uma string vazia
    @Path("/tasks")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addUser(Tarefa newTarefa, @Context UriInfo uriInfo,@HeaderParam("token") String token) {
        if(token.equals("true")){
            System.out.println(newTarefa);
            int count = verificaCamposT(newTarefa);
            if(count>=7) {
                int id = tarefa.criaTarefa(newTarefa);
                if (id > 0) {
                    return Response.status(Response.Status.CREATED).entity(tarefa.buscaTarefa(id)).build();
                }
            }

            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"\"Atributos Obrigatórios - Nome, data, valor, idRelator, idResponsavel e estado\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();

    }

    @Path("/tasks")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateTasks(Tarefa updateTarefa, @Context UriInfo uriInfo,@HeaderParam("token") String token) {
        if(token.equals("true")){
            Tarefa upTarefa = tarefa.buscaTarefa(updateTarefa.getIdTarefa());

            if(upTarefa!=null){
                tarefa.atualizaTarefa(upTarefa);
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        "    \"error\": \"\"Tarefa atualizado com sucesso.\"\n" +
                        "}").build();
            }

            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"\"Tarefa não encontrada.\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/routines/{idUsuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response searchRoutines(String idUsuario, @HeaderParam("token") String token) {

        if(token.equals("true")){
            List<Rotina> rotinas = rotina.buscaRotinasUsuario(idUsuario);
            if(rotinas.size()>=0) return Response.status(Response.Status.OK).entity(rotinas).build();
            else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhuma rotina encontrada\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //de alguma forma, cria todos os jsons e caso o usuario nao preencha volta uma string vazia
    @Path("/routines")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addRoutines(Rotina newRotina, @Context UriInfo uriInfo,@HeaderParam("token") String token) {
        if(token.equals("true")){
            int count = verificaCamposR(newRotina);
            if(count>=2) {
                int id = rotina.criaRotina(newRotina);
                if (id > 0) {
                    return Response.status(Response.Status.CREATED).entity(rotina.buscaRotina(id)).build();
                }
            }

            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"\"Atributos Obrigatórios - Nome e validade\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();

    }

    @Path("/routines")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateRoutines(Rotina updateRotina, @Context UriInfo uriInfo,@HeaderParam("token") String token) {
        if(token.equals("true")){
            Rotina upRotina = rotina.buscaRotina(updateRotina.getIdRotina());

            if(upRotina!=null){
                rotina.atualizaRotina(upRotina);
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        "    \"error\": \"\"Rotina atualizada com sucesso.\"\n" +
                        "}").build();
            }

            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"\"Rotina não encontrada.\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }


    int verificaCampos(Usuario newUser){
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newUser.getIdUsuario().length()!=0) count++;
        if (newUser.getNome().length()!=0) count++;
        if (newUser.getSenha().length()!=0) count++;
        if (newUser.getData().length()!=0) count++;
        if (newUser.getGenero().length()!=0) count++;
        if (newUser.getPerfil().length()!=0) count++;
        if (newUser.getTelefone().length()!=0) count++;
        if (newUser.getEmail().length()!=0) count++;
        return count;
    }

    int verificaCamposT(Tarefa newTarefa){
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newTarefa.getNome().length()!=0) count++;
        if (newTarefa.getDescricao().length()!=0) count++;
        if (newTarefa.getData().length()!=0) count++;
        if (newTarefa.getValor()!=0) count++;
        if (newTarefa.getIdRelator().length()!=0) count++;
        if (newTarefa.getIdResponsavel().length()!=0) count++;
        if (newTarefa.getEstado().length()!=0) count++;
        return count;
    }
    int verificaCamposR(Rotina newRotina){
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newRotina.getIdRotina()!=0) count++;
        if (newRotina.getValidade().length()!=0) count++;
        return count;
    }

}
