package com.bsc.hometasks.rest;

import com.bsc.hometasks.db.dao.*;
import com.bsc.hometasks.pojo.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.sax.SAXSource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Path("/")
public class Controller {
    UsuarioDAO user = new UsuarioDAO();
    TarefaDAO tarefa = new TarefaDAO();
    RotinaDAO rotina = new RotinaDAO();
    CasaDAO casa = new CasaDAO();
    PagamentoDAO pagamento = new PagamentoDAO();
    RegraDAO regra = new RegraDAO();
    ComentarioDAO comentario = new ComentarioDAO();
    UsuarioRotinaDAO userRotinas = new UsuarioRotinaDAO();

    //testado
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response login(@HeaderParam("Authorization") String credBase64) {
        if (credBase64 != null) {
            String basic = credBase64.split(" ")[1];
            String credenciais = new String(Base64.getDecoder().decode(basic));
            String log[] = credenciais.split(":");
            UsuarioDAO user = new UsuarioDAO();
            Usuario buscarUser = new Usuario();
            if (log.length == 2) {
                buscarUser = user.buscaUsuario(log[0]);
                if (buscarUser != null) {
                    if (buscarUser.getSenha().compareTo(log[1]) == 0) {
                        String token = getToken(credenciais);
                        buscarUser.setToken(token);
                        user.atualizaUsuario(buscarUser);
                        return Response.status(Response.Status.OK).entity("{\n" +
                                " \"token\": \"" + buscarUser.getToken() + "\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                            " \"error\": \"senha inválida\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        " \"error\": \"idUsuario inválido ou inexistente\"\n" +
                        "}").build();

            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    " \"error\": \"Formato das credenciais inválido. Correto - Base64(idUsuario:senha)\"\n" +
                    "}").build();

        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                " \"error\": \"Credenciais não enviadas no cabeçalho Authorization\"\n" +
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
        if (count >= 8) {
            if (user.buscaUsuariosId(newUser.getIdUsuario()).size() > 0) {
                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                        " \"error\": \"Login já existe\"\n" +
                        "}").build();
            }

            if (user.criaUsuario(newUser)) {
                //return Response.created(builder.build()).build();
                newUser.setSenha(null);
                return Response.status(Response.Status.CREATED).entity(newUser).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                " \"error\": \"Atributos Obrigatórios - idUsuario, nome, senha, data, genero, perfil, telefone e email\"\n" +
                "}").build();
    }

    //testado
    @Path("/users/list/{idUsuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUser(@PathParam("idUsuario") String idUsuario, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            List<Usuario> users = user.buscaUsuariosId(idUsuario);
            if (users != null) {
                for (Usuario usuario : users) {
                    usuario.setSenha(null);
                    usuario.setToken(null);
                }
                return Response.ok(users).build();

            } else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "\"error\": \"Nenhum usuário encontrado\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testado
    @Path("/users/{idUsuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@PathParam("idUsuario") String idUsuario, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            Usuario usuario = user.buscaUsuario(idUsuario);
            if (usuario != null) {
                usuario.setSenha(null);
                usuario.setToken(null);
                return Response.ok(usuario).build();

            } else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "\"error\": \"Usuário não encontrado\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/users/home")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchHomeId(@HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int idCasaUserToken = userToken.getIdCasa();
            if (idCasaUserToken != 0) {
                List<Usuario> usuariosCasa = user.buscaUsuariosCasa(idCasaUserToken);
                if (usuariosCasa.size() > 0) {
                    for (Usuario usuario : usuariosCasa) {
                        usuario.setSenha(null);
                        usuario.setToken(null);
                    }
                    return Response.ok(usuariosCasa).build();

                }
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "\"error\": \"Nenhum usuário encontrado\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Usuário não está associado a uma casa\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testado
    @Path("/users")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateUser(Usuario updateUser, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                if (updateUser.getIdUsuario() != null) {
                    if (userToken.getIdUsuario().compareTo(updateUser.getIdUsuario()) == 0) {
                        updateUser.setToken(userToken.getToken());
                        if (updateUser.getSenha() == null) {
                            updateUser.setSenha(userToken.getSenha());
                        }
                        int count = verificaCampos(updateUser);
                        if (count >= 8) {
                            updateUser.setPontos(userToken.getPontos());
                            updateUser.setTotalTarefas(userToken.getTotalTarefas());
                            updateUser.setTarefasAvaliadas(userToken.getTarefasAvaliadas());
                            if (user.atualizaUsuario(updateUser) > 0) {
                                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                                        "    \"resposta\": \"\"Usuário atualizado com sucesso.\"\n" +
                                        "}").build();
                            }
                            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                                    "    \"error\": \"\"Erro ao atualizar Usuário.\"\n" +
                                    "}").build();
                        }
                        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                                " \"error\": \"Atributos Obrigatórios - idUsuario, nome, data, genero, perfil, telefone e email\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.FORBIDDEN).entity("{\n" +
                            "    \"error\": \"Permissão Negada\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        " \"error\": \"Atributos Obrigatórios - idUsuario, nome, senha, data, genero, perfil, telefone e email\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testado
    @Path("/tasks/{estado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTasks(@PathParam("estado") String estado, @HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                List<Tarefa> tarefas = tarefa.buscaTarefasUsuario(userToken, estado);
                if (tarefas.size() > 0) return Response.status(Response.Status.OK).entity(tarefas).build();
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "    \"error\": \"Nenhuma tarefa encontrada\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testado
    //de alguma forma, cria todos os jsons e caso o usuario nao preencha volta uma string vazia
    @Path("/tasks")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addTask(Tarefa newTarefa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                int count = verificaCamposT(newTarefa);
                if (count >= 5) {
                    if (userToken.getIdCasa() != 0) {
                        newTarefa.setIdRelator(userToken.getIdUsuario());
                        Usuario userResponsavel = user.buscaUsuario(newTarefa.getIdResponsavel());
                        if (userResponsavel != null) {
                            if(userResponsavel.getIdCasa() == userToken.getIdCasa()) {
                                int idTarefa = tarefa.criaTarefa(newTarefa);
                                if (idTarefa > 0) {
                                    int totalTarefasUser = userResponsavel.getTotalTarefas();
                                    totalTarefasUser++;
                                    userResponsavel.setTotalTarefas(totalTarefasUser);
                                    user.atualizaUsuario(userResponsavel);
                                    newTarefa.setIdTarefa(idTarefa);
                                    return Response.status(Response.Status.CREATED).entity(newTarefa).build();
                                }
                                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                        "    \"error\": \"Não foi possível criar a tarefa no banco.\"\n" +
                                        "}").build();
                            }
                            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                                    "    \"error\": \"Usuário responsável pela tarefa não está na mesma casa\"\n" +
                                    "}").build();
                        }
                        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                                "    \"error\": \"Usuário responsável pela tarefa não encontrado\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.CONFLICT).entity("{\n" +
                            "    \"error\": \"Usuário não está associado a uma casa\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Atributos Obrigatórios - nome, descricao, data, valor, idResponsavel e estado\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();

    }

    //testado mas nao retorna nada
    @Path("/tasks")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateTask(Tarefa updateTarefa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                if (updateTarefa.getIdTarefa() != 0) {
                    int count = verificaCamposT(updateTarefa);
                    if (count != 0) {
                        Tarefa oldTarefa = tarefa.buscaTarefa(updateTarefa.getIdTarefa());
                        if (oldTarefa != null) {
                            String usuario = userToken.getIdUsuario();
                            String responsavel = oldTarefa.getIdResponsavel();
                            String relator = oldTarefa.getIdRelator();
                            if ((usuario.compareTo(responsavel) == 0) || (usuario.compareTo(relator) == 0)) {
                                atualizaCamposTarefa(updateTarefa, oldTarefa, userToken);
                                String estado = updateTarefa.getEstado();
                                boolean repasse = updateTarefa.isRepasse();
                                if(estado.compareToIgnoreCase("avaliada") == 0){
                                    if (repasse){
                                        String idDevedor = updateTarefa.getIdRelator();
                                        String idCredor = updateTarefa.getIdResponsavel();
                                        Float valor = updateTarefa.getValor();
                                        Calendar c = Calendar.getInstance();
                                        Date date = c.getTime();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        String data = sdf.format(date);
                                        String descricao = "Pagamento referente a " + updateTarefa.getNome();
                                        pagamento.criaPagamento(new Pagamento(
                                                0,idDevedor, idCredor, 0, valor,data,descricao, false));
                                    }
                                    Usuario userTarefa = user.buscaUsuario(updateTarefa.getIdResponsavel());
                                    int totalTarefas = userTarefa.getTotalTarefas();
                                    int tarefasAvaliadas = userTarefa.getTarefasAvaliadas();
                                    tarefasAvaliadas++;
                                    int pontos = (tarefasAvaliadas/totalTarefas)*100;
                                    userTarefa.setTarefasAvaliadas(tarefasAvaliadas);
                                    userTarefa.setPontos(pontos);
                                    user.atualizaUsuario(userTarefa);
                                }
                                if (tarefa.atualizaTarefa(updateTarefa) > 0) {
                                    return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                                            "    \"resposta\": \"Tarefa atualizada com sucesso\"\n" +
                                            "}").build();
                                }
                                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                        "    \"error\": \"Não foi possível atualizar tarefa no banco.\"\n" +
                                        "}").build();
                            }
                            return Response.status(Response.Status.FORBIDDEN).entity("{\n" +
                                    "    \"error\": \"Permissão Negada\"\n" +
                                    "}").build();
                        }
                        return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                                "    \"error\": \"A tarefa não foi encontrada\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.NOT_MODIFIED).entity("{\n" +
                            "    \"error\": \"Tarefa sem nenhum campo para alterar.\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Atributo idTarefa obrigatório\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/comments/{idTarefa}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchComments(@PathParam("idTarefa") String idTarefa, @HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                Tarefa task = tarefa.buscaTarefa(Integer.parseInt(idTarefa));
                if (task != null) {
                    List<Comentario> comentarios = comentario.buscaComentariosTarefa(Integer.parseInt(idTarefa));
                    if (comentarios.size() > 0) return Response.status(Response.Status.OK).entity(comentarios).build();
                    return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                            " \"error\": \"Nenhum comentário encontrado\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "\"error\": \"A tarefa não foi encontrada\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").

                build();

    }

    @Path("/comments")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(Comentario newComentario, @HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                int count = verificaCamposComentario(newComentario);
                if (count == 3) {
                    Tarefa task = tarefa.buscaTarefa(newComentario.getIdTarefa());
                    if (task != null) {
                        newComentario.setIdUsuario(userToken.getIdUsuario());
                        int idComentario = comentario.criaComentario(newComentario);
                        if (idComentario > 0) {
                            newComentario.setIdComentario(idComentario);
                            return Response.status(Response.Status.CREATED).entity(newComentario).build();
                        }
                        return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                "    \"error\": \"Não foi possível adicionar o comentário\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                            "    \"error\": \"A tarefa não foi encontrada\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Atributos Obrigatórios - texto, data e idTarefa\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();

    }

    @Path("/comments")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(Comentario updateComentario, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                if (updateComentario.getIdComentario() != 0) {
                    Comentario oldComments = comentario.buscaComentarioById(updateComentario.getIdComentario());
                    if (oldComments != null) {
                        String usuario = userToken.getIdUsuario();
                        String userComments = oldComments.getIdUsuario();
                        if (usuario.compareTo(userComments) == 0) {
                            int count = verificaCamposComentario(updateComentario);
                            if (count != 0) {
                                atualizaCamposComentario(updateComentario, oldComments);
                                if (comentario.atualizaComentario(updateComentario) > 0) {
                                    return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                                            "    \"resposta\": \"Comentario atualizado com sucesso\"\n" +
                                            "}").build();
                                }
                                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                        "    \"error\": \"Não foi possível atualizar comentario no banco.\"\n" +
                                        "}").build();
                            }
                            return Response.status(Response.Status.NOT_MODIFIED).entity("{\n" +
                                    "    \"error\": \"Comentário sem nenhum campo para alterar.\"\n" +
                                    "}").build();
                        }
                        return Response.status(Response.Status.FORBIDDEN).entity("{\n" +
                                "    \"error\": \"Permissão Negada\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                            "    \"error\": \"Comentário não encontrado\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Atributo idComentario obrigatório\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //    //testada
    @Path("/routines")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRoutines(@HeaderParam("token") String token) {
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            List<Rotina> rotinas = rotina.buscaRotinasUsuario(userToken.getIdUsuario());
            if (rotinas.size() > 0) {
                return Response.status(Response.Status.OK).entity(rotinas).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "\"error\": \"Nenhuma rotina encontrada\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "\"error\": \"Autenticação Necessária\"\n").build();
    }


    //testada
    ///de alguma forma, cria todos os jsons e caso o usuario nao preencha volta uma string vazia
    @Path("/routines")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addRoutines(Rotina newRotina, @HeaderParam("token") String token) {
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int count = verificaCamposR(newRotina);
            if (count == 3) {
                newRotina.setIdUsuario(userToken.getIdUsuario());
                int idRotina = rotina.criaRotina(newRotina);
                if (idRotina > 0) {
                    newRotina.setIdRotina(idRotina);
                    return Response.status(Response.Status.CREATED).entity(newRotina).build();
                }
                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                        "\"error\": \"Não foi possível criar a Rotina no banco de dados\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"Atributos Obrigatórios - nome, descricao e validade\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testada
//    @Path("/routines")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    public Response updateRoutines(Rotina updateRotina, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
//        UsuarioDAO user = new UsuarioDAO();
//        Usuario userToken = user.buscaUsuarioToken(token);
//        if (userToken != null) {
//            int count
//            if()
//            if (rotina.atualizaRotina(updateRotina) > 0) {
//                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
//                        "    \"resposta\": \"Rotina atualizada com sucesso.\"\n" +
//                        "}").build();
//            }
//            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
//                    "    \"error\": \"\"Rotina não encontrada.\"\n" +
//                    "}").build();
//        }
//        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
//                "    \"error\": \"Autenticação Necessária\"\n" +
//                "}").build();
//    }

    //testada
    @Path("/home/list/{name_home}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchHome(@PathParam("name_home") String name_home, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (name_home != null) {
                List<Casa> casas = casa.buscaCasasNome(name_home);
                if (casas.size() == 0) {
                    casas = casa.buscaCasasEndereco(name_home);
                }
                if (casas.size() > 0) {
                    return Response.ok(casas).build();

                } else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "\"error\": \"Nenhuma casa encontrada de acordo com o nome ou endereço informado\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"Necessário envio do parâmetro nome ou endereço na URL\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/home/{idCasa}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchHomeId(@PathParam("idCasa") String idCasa, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (idCasa != null) {
                Casa home = casa.buscaCasa(Integer.parseInt(idCasa));
                if (home != null) {
                    return Response.ok(home).build();

                }
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "\"error\": \"Nenhuma casa encontrada de acordo com o id informado\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"Necessário envio do parâmetro nome ou endereço na URL\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testada
    @Path("/home")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addHome(Casa newCasa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int count = verificaCamposC(newCasa);
            if (count == 4) {
                int idHome = casa.criaCasa(newCasa);
                if (idHome > 0) {
                    newCasa.setIdCasa(idHome);
                    userToken.setIdCasa(idHome);
                    userToken.setPerfil("responsavel");
                    user.atualizaUsuario(userToken);
                    return Response.status(Response.Status.CREATED).entity(newCasa).build();
                }
                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                        "\"error\": \"Não foi possível criar a Casa no banco de dados\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"Atributos Obrigatórios - nome, endereco, descricao e aluguel\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testada
    @Path("/home")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateHome(Casa updateCasa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int idCasa = updateCasa.getIdCasa();
            if (idCasa != 0) {
                Casa oldCasa = casa.buscaCasa(idCasa);
                if (oldCasa != null) {
                    int idCasaUserToken = userToken.getIdCasa();
                    String pefilUserToken = userToken.getPerfil();
                    if ((idCasa == idCasaUserToken) && (pefilUserToken.compareToIgnoreCase("responsavel") == 0)) {
                        atualizaCamposCasa(updateCasa, oldCasa);
                        if (casa.atualizaCasa(updateCasa) > 0) {
                            return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                                    "    \"resposta\": \"Casa atualizada com sucesso\"\n" +
                                    "}").build();
                        }
                        return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                "\"error\": \"Não foi possível Atualizar a Casa no banco de dados\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.FORBIDDEN).entity("{\n" +
                            "    \"error\": \"Permissão Negada\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "\"error\": \"A casa informada não foi encontrada\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"Atributo idCasa é obrigatório\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                " \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }


    @Path("/account")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchConta(@HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                List<Pagamento> pagamentos = pagamento.buscaPagamento(userToken.getIdUsuario());
                if (pagamentos.size() > 0) return Response.status(Response.Status.OK).entity(pagamentos).build();
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "    \"error\": \"Usuário não possui nenhum pagamento em aberto\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/account")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addAccount(Pagamento newPagamento, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
//        UsuarioDAO user = new UsuarioDAO();
//        Usuario userToken = user.buscaUsuarioToken(token);
//        if (userToken != null) {
//            int count = verificaCamposP(newPagamento);
//            if (count == 3) {
//                if (pagamento.criaPagamento(newPagamento) > 0) {
//                    return Response.status(Response.Status.CREATED).entity(pagamento.buscaPagamento(newPagamento.getIdPagamento(), newPagamento.getIdCredor(), newPagamento.getIdDevedor())).build();
//                }
//                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
//                        "    \"resposta\": \"\"Pagamento não pode ser criado\"\n" +
//                        "}").build();
//            }
//            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
//                    "    \"error\": \"\"Atributos Obrigatórios - IdPagamento, IdCredor e IdDevedor\"\n" +
//                    "}").build();
//        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/account")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateAccount(Pagamento updatepag, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int idPagamento = updatepag.getIdPagamento();
            boolean pago = updatepag.isPago();
            if (idPagamento != 0 || (!pago)) {
                Pagamento oldPagamento = pagamento.buscaPagamentoById(idPagamento);
                if(oldPagamento != null) {
                    atualizaCamposPagamento(updatepag, oldPagamento);
                    if (pagamento.atualizaPagamento(updatepag) > 0) {
                        return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                                "    \"resposta\": \"Pagamento atualizado com sucesso.\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.CONFLICT).entity("{\n" +
                            "\"error\": \"Não foi possível Atualizar a Pagamento no banco de dados\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                        "    \"error\": \"Pagamento não encontrado\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"Campo idPagamento e pago são obrigatórios\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/rules")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRules(@HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                int idCasa = userToken.getIdCasa();
                if (idCasa != 0) {
                    List<Regra> regras = regra.buscaRegrasCasa(userToken.getIdCasa());
                    if (regras.size() > 0) return Response.status(Response.Status.OK).entity(regras).build();
                    return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                            "    \"error\": \"Nenhuma regra encontrada\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Usuário não possui casa\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/rules")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addRule(Regra newRegra, @HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                int idCasa = userToken.getIdCasa();
                if (idCasa != 0) {
                    int count = verificaCamposRules(newRegra);
                    if (count == 3) {
                        if (userToken.getPerfil().compareToIgnoreCase("responsavel") == 0) {
                            newRegra.setEstado(true);
                        } else {
                            newRegra.setEstado(false);
                        }
                        newRegra.setIdCasa(idCasa);
                        newRegra.setIdUsuario(userToken.getIdUsuario());
                        int idRegra = regra.criaRegra(newRegra);
                        if (idRegra > 0) {
                            newRegra.setIdRegra(idRegra);
                            return Response.status(Response.Status.CREATED).entity(newRegra).build();
                        }
                        return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                "    \"error\": \"Não foi possível criar a Regra no banco\"\n" +
                                "}").build();

                    }
                    return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                            "    \"error\": \"Atributos Obrigatórios - nome, descricao e data\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Usuário não possui casa\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();

    }

    @Path("/rules")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateAccount(Regra updateReg, @HeaderParam("token") String token) {
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                if (updateReg.getIdRegra() != 0) {
                    int count = verificaCamposRules(updateReg);
                    if (count != 0) {
                        Regra oldRegra = regra.buscaRegra(updateReg.getIdRegra());
                        if (oldRegra != null) {
                            String userPerfil = userToken.getPerfil();
                            atualizaCamposRegra(updateReg, oldRegra);
                            if ((userPerfil.compareToIgnoreCase("responsavel") == 0)) {
                                updateReg.setEstado(true);
                            } else {
                                updateReg.setEstado(false);
                            }
                            updateReg.setIdUsuario(userToken.getIdUsuario());
                            if (regra.atualizaRegra(updateReg) > 0) {
                                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                                        "    \"resposta\": \"Regra atualizada com sucesso\"\n" +
                                        "}").build();
                            }
                            return Response.status(Response.Status.CONFLICT).entity("{\n" +
                                    "    \"error\": \"Não foi possível atualizar tarefa no banco.\"\n" +
                                    "}").build();

                        }
                        return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                                "    \"error\": \"A Regra não encontrada\"\n" +
                                "}").build();
                    }
                    return Response.status(Response.Status.NOT_MODIFIED).entity("{\n" +
                            "    \"error\": \"Regra sem nenhum campo para alterar.\"\n" +
                            "}").build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"Atributo idRegra obrigatório\"\n" +
                        "}").build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    int verificaCampos(Usuario newUser) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newUser.getIdUsuario() != null) count++;
        if (newUser.getNome() != null) count++;
        if (newUser.getSenha() != null) count++;
        //if (newUser.getSenha().length()!=0) count++;
        if (newUser.getData() != null) count++;
        if (newUser.getGenero() != null) count++;
        if (newUser.getPerfil() != null) count++;
        if (newUser.getTelefone() != null) count++;
        if (newUser.getEmail() != null) count++;
        return count;
    }

    int verificaCamposT(Tarefa newTarefa) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newTarefa.getNome() != null) count++;
        if (newTarefa.getDescricao() != null) count++;
        if (newTarefa.getData() != null) count++;
        if (newTarefa.getValor()!= 0) count++;
        //if (newTarefa.getIdRelator() != null) count++;
        if (newTarefa.getIdResponsavel() != null) count++;
        if (newTarefa.getEstado() != null) count++;
        return count;
    }

    int verificaCamposR(Rotina newRotina) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newRotina.getNome() != null) count++;
        if (newRotina.getValidade() != null) count++;
        if (newRotina.getDescricao() != null) count++;
        return count;
    }

    int verificaCamposC(Casa newCasa) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newCasa.getNome() != null) count++;
        if (newCasa.getAluguel() != 0) count++;
        if (newCasa.getDescricao() != null) count++;
        if (newCasa.getEndereco() != null) count++;
        return count;
    }

    int verificaCamposComentario(Comentario newComentario) {
        int count = 0;
        if (newComentario.getIdTarefa() != 0) count++;
        if (newComentario.getData() != null) count++;
        if (newComentario.getTexto() != null) count++;

        return count;
    }

//    int verificaCamposP(Pagamento newPagamento) {
//        int count = 0;
//        //ve se ele preencheu todas as paradas
//        if (newPagamento.getData() != null) count++;
//        if (!newPagamento.isPago())
//        return count;
//    }

    int verificaCamposRules(Regra newRegra) {
        int count = 0;
        if (newRegra.getDescricao() != null) count++;
        //if (newRegra.getIdUsuario() != null) count++;
        //if (newRegra.getIdCasa() != 0) count++;
        if (newRegra.getData() != null) count++;
        if (newRegra.getNome() != null) count++;
        return count;
    }

    void atualizaCamposCasa(Casa updateCasa, Casa oldCasa) {
        if (updateCasa.getAluguel() == 0) updateCasa.setAluguel(oldCasa.getAluguel());
        if (updateCasa.getDescricao() == null) updateCasa.setDescricao(oldCasa.getDescricao());
        if (updateCasa.getEndereco() == null) updateCasa.setEndereco(oldCasa.getEndereco());
        if (updateCasa.getFoto() == null) updateCasa.setFoto(oldCasa.getFoto());
        if (updateCasa.getNome() == null) updateCasa.setNome(oldCasa.getNome());
    }

    void atualizaCamposTarefa(Tarefa updateTarefa, Tarefa oldTarefa, Usuario userToken) {
        String idResponsavel = updateTarefa.getIdResponsavel();
        if(idResponsavel != null){
            if(idResponsavel.compareToIgnoreCase(oldTarefa.getIdResponsavel()) != 0){
                Usuario userOld = user.buscaUsuario(oldTarefa.getIdResponsavel());
                int totalTarefasUserOld = userOld.getTotalTarefas();
                Usuario userNew = user.buscaUsuario(idResponsavel);
                int totalTarefasUserNew = userOld.getTotalTarefas();
                totalTarefasUserOld--;
                if(totalTarefasUserOld < 0){
                    totalTarefasUserOld = 0;
                }
                totalTarefasUserNew++;
                userNew.setTotalTarefas(totalTarefasUserNew);
                userOld.setTotalTarefas(totalTarefasUserOld);
                user.atualizaUsuario(userNew);
                user.atualizaUsuario(userOld);

                updateTarefa.setIdRelator(userToken.getIdUsuario());
                updateTarefa.setRepasse(true);
                if (updateTarefa.getData() == null) updateTarefa.setData(oldTarefa.getData());
                if (updateTarefa.getDescricao() == null)
                    updateTarefa.setDescricao(oldTarefa.getDescricao());
                if (updateTarefa.getEstado() == null) updateTarefa.setEstado(oldTarefa.getEstado());
                if (updateTarefa.getNome() == null) updateTarefa.setNome(oldTarefa.getNome());
                if (updateTarefa.getValor() == 0) updateTarefa.setValor(oldTarefa.getValor());

            }
            else {
                updateTarefa.setIdRelator(oldTarefa.getIdRelator());
            }
        }
        else{
            updateTarefa.setIdResponsavel(oldTarefa.getIdResponsavel());
            updateTarefa.setRepasse(oldTarefa.isRepasse());
            updateTarefa.setIdRelator(oldTarefa.getIdRelator());
            if (updateTarefa.getData() == null) updateTarefa.setData(oldTarefa.getData());
            if (updateTarefa.getDescricao() == null)
                updateTarefa.setDescricao(oldTarefa.getDescricao());
            if (updateTarefa.getEstado() == null) updateTarefa.setEstado(oldTarefa.getEstado());
            if (updateTarefa.getNome() == null) updateTarefa.setNome(oldTarefa.getNome());
            if (updateTarefa.getValor() == 0) updateTarefa.setValor(oldTarefa.getValor());
        }



    }

    void atualizaCamposComentario(Comentario updateComentario, Comentario oldComentario) {
        if (updateComentario.getTexto() == null) updateComentario.setTexto(oldComentario.getTexto());
        if (updateComentario.getData() == null) updateComentario.setData(oldComentario.getData());
        if (updateComentario.getMidia() == null) updateComentario.setMidia(oldComentario.getMidia());
        updateComentario.setIdComentario(oldComentario.getIdComentario());
        updateComentario.setIdUsuario(oldComentario.getIdUsuario());
        updateComentario.setIdTarefa(oldComentario.getIdTarefa());
    }

    void atualizaCamposRegra(Regra updateReg, Regra oldRegra) {
        if (updateReg.getData() == null) updateReg.setData(oldRegra.getData());
        if (updateReg.getDescricao() == null) updateReg.setDescricao(oldRegra.getDescricao());
        if (updateReg.getNome() == null) updateReg.setNome(oldRegra.getNome());
        if (updateReg.getValor() == 0) updateReg.setValor(oldRegra.getValor());
    }

    void atualizaCamposPagamento(Pagamento updatePag, Pagamento oldPag){
        if (updatePag.getDescricao() == null) updatePag.setDescricao(oldPag.getDescricao());
        if (updatePag.getJuros() == 0) updatePag.setJuros(oldPag.getJuros());
        if (updatePag.getValor() == 0) updatePag.setValor(oldPag.getValor());
        updatePag.setIdCredor(oldPag.getIdCredor());
        updatePag.setIdDevedor(oldPag.getIdDevedor());
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data = sdf.format(date);
        updatePag.setData(data);
    }

    public String getToken(String credentials) {
        String token = null;
        String milis = String.valueOf(System.currentTimeMillis());
        credentials = credentials + milis;
        try {
            MessageDigest m = MessageDigest.getInstance("SHA-1");

            m.update(credentials.getBytes(), 0, credentials.length());

            byte[] digest = m.digest();

            token = new BigInteger(1, digest).toString(16);

            System.out.println("SHA-1: " + token);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;

    }
}
