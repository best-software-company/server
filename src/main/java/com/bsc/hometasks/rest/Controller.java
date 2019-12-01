package com.bsc.hometasks.rest;

import com.bsc.hometasks.db.dao.*;
import com.bsc.hometasks.pojo.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Path("/")
public class Controller {
    UsuarioDAO user = new UsuarioDAO();
    TarefaDAO tarefa = new TarefaDAO();
    RotinaDAO rotina = new RotinaDAO();
    CasaDAO casa = new CasaDAO();
    PagamentoDAO pagamento = new PagamentoDAO();
    RegraDAO regra = new RegraDAO();

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

            }
            else return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "\"error\": \"Usuário não encontrado\"\n" +
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
        UsuarioDAO user = new UsuarioDAO();
        if (token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                List<Tarefa> tarefas = tarefa.buscaTarefasUsuarioEstado(userToken.getIdUsuario(), estado);
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
    public Response addUser(Tarefa newTarefa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        if(token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if (userToken != null) {
                int count = verificaCamposT(newTarefa);
                if (count == 5) {
                    newTarefa.setIdRelator(userToken.getIdUsuario());
                    int idTarefa = tarefa.criaTarefa(newTarefa);
                    if (idTarefa > 0) {
                        newTarefa.setIdTarefa(idTarefa);
                        return Response.status(Response.Status.CREATED).entity(newTarefa).build();
                    }
                    return Response.status(Response.Status.CONFLICT).entity("{\n" +
                            "    \"error\": \"Não foi possível criar a tarefa no banco. idResponsavel inválido\"\n" +
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
    public Response updateTasks(Tarefa updateTarefa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        if(token != null) {
            Usuario userToken = user.buscaUsuarioToken(token);
            if(userToken != null) {
                if(updateTarefa.getIdTarefa() != 0) {
                    int count = verificaCamposT(updateTarefa);
                    if (count != 0) {
                        Tarefa oldTarefa = tarefa.buscaTarefa(updateTarefa.getIdTarefa());
                        if (oldTarefa != null) {
                            String usuario = userToken.getIdUsuario();
                            String responsavel = oldTarefa.getIdResponsavel();
                            String relator = oldTarefa.getIdRelator();
                            if ((usuario.compareTo(responsavel) == 0) || (usuario.compareTo(relator) == 0)){
                                atualizaCamposTarefa(updateTarefa, oldTarefa);
                                updateTarefa.setIdRelator(relator);
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

    //testada
    @Path("/routines/{idUsuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRoutines(@PathParam("idUsuario") String idUsuario, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            List<Rotina> rotinas = rotina.buscaRotinasUsuario(idUsuario);
            if (rotinas.size() > 0)
                return Response.status(Response.Status.OK).entity(rotinas).build();

            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhuma rotina encontrada\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testada
    ///de alguma forma, cria todos os jsons e caso o usuario nao preencha volta uma string vazia
    @Path("/routines")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addRoutines(Rotina newRotina, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int count = verificaCamposR(newRotina);
            if (count == 3) {
                if (rotina.criaRotina(newRotina) > 0) {
                    return Response.status(Response.Status.CREATED).entity(rotina.buscaRotina(rotina.criaRotina(newRotina))).build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"error\": \"\"Rotina não pode ser criada\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"\"Atributos Obrigatórios - Nome e validade\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testada
    @Path("/routines")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateRoutines(Rotina updateRotina, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (rotina.atualizaRotina(updateRotina) > 0) {
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        "    \"resposta\": \"Rotina atualizada com sucesso.\"\n" +
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

    //testada
    @Path("/home/{name_home}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchHome(@PathParam("name_home") int name_home, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (casa.buscaCasa(name_home) != null)
                return Response.status(Response.Status.OK).entity(casa.buscaCasa(name_home)).build();

            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhuma casa encontrada\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
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
                if (casa.criaCasa(newCasa) > 0) {
                    return Response.status(Response.Status.CREATED).entity(casa.buscaCasa(casa.criaCasa(newCasa))).build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"resposta\": \"Casa não pode ser criada\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"\"Atributos Obrigatórios - Nome, descrição, aluguel e endereço\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    //testada
    @Path("/home")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateRoutines(Casa updateCasa, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (casa.atualizaCasa(updateCasa) > 0) {
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        " \"resposta\": \"Casa atualizada com sucesso.\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"\"Casa não encontrada.\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/account/{pagCredDev}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchConta(@PathParam("pagCredDev") String pagCredDev, @HeaderParam("token") String token) {
        String log[] = pagCredDev.split(":");
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (pagamento.buscaPagamento(Integer.parseInt(log[0]), log[1], log[2]) != null)
                return Response.status(Response.Status.OK).entity(pagamento.buscaPagamento(Integer.parseInt(log[0]), log[1], log[2])).build();

            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhum pagamento encontrado\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/account")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addAccount(Pagamento newPagamento, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int count = verificaCamposP(newPagamento);
            if (count == 3) {
                if (pagamento.criaPagamento(newPagamento) > 0) {
                    return Response.status(Response.Status.CREATED).entity(pagamento.buscaPagamento(newPagamento.getIdPagamento(), newPagamento.getIdCredor(), newPagamento.getIdDevedor())).build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"resposta\": \"\"Pagamento não pode ser criado\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"\"Atributos Obrigatórios - IdPagamento, IdCredor e IdDevedor\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/account")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateAccount(Pagamento updatepag, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (pagamento.atualizaPagamento(updatepag) > 0) {
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        "    \"resposta\": \"\"Pagamento atualizado com sucesso.\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"\"Pagamento não encontrado.\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/rules/{idRegraCasa}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRules(@PathParam("idRegraCasa") String idRegraCasa, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            String log[] = idRegraCasa.split(":");

            if (regra.buscaRegra(Integer.parseInt(log[0]), Integer.parseInt(log[1])) != null)
                return Response.status(Response.Status.OK).entity(regra.buscaRegra(Integer.parseInt(log[0]), Integer.parseInt(log[1]))).build();

            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"Nenhuma regra encontrada\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/rules")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addRule(Regra newRegra, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            int count = verificaCamposRules(newRegra);
            if (count == 5) {
                if (regra.criaRegra(newRegra) > 0) {
                    return Response.status(Response.Status.CREATED).entity(newRegra).build();
                }
                return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                        "    \"resposta\": \"\"Regra não pode ser criada\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                    "    \"error\": \"\"Atributos Obrigatórios faltando\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Autenticação Necessária\"\n" +
                "}").build();
    }

    @Path("/rules")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateAccount(Regra updateReg, @Context UriInfo uriInfo, @HeaderParam("token") String token) {
        UsuarioDAO user = new UsuarioDAO();
        Usuario userToken = user.buscaUsuarioToken(token);
        if (userToken != null) {
            if (regra.atualizaRegra(updateReg) > 0) {
                return Response.status(Response.Status.NO_CONTENT).entity("{\n" +
                        "    \"resposta\": \"\"Regra atualizada com sucesso.\"\n" +
                        "}").build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("{\n" +
                    "    \"error\": \"\"Regra não encontrada.\"\n" +
                    "}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
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
        //if (newTarefa.getValor()!=0) count++;
        //if (newTarefa.getIdRelator() != null) count++;
        if (newTarefa.getIdResponsavel() != null) count++;
        if (newTarefa.getEstado() != null) count++;
        return count;
    }

    int verificaCamposR(Rotina newRotina) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newRotina.getTarefa().getIdTarefa() != 0) count++;
        if (newRotina.getValidade().length() != 0) count++;
        if (verificaCamposT(newRotina.getTarefa()) == 6) count++;
        return count;
    }

    int verificaCamposC(Casa newCasa) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newCasa.getNome().length() != 0) count++;
        if (newCasa.getAluguel() != 0) count++;
        if (newCasa.getDescricao().length() != 0) count++;
        if (newCasa.getEndereco().length() != 0) count++;
        return count;
    }

    int verificaCamposP(Pagamento newPagamento) {
        int count = 0;
        //ve se ele preencheu todas as paradas
        if (newPagamento.getData() != null) count++;
        if (newPagamento.getIdCredor().length() != 0) count++;
        if (newPagamento.getIdDevedor().length() != 0) count++;

        return count;
    }

    int verificaCamposRules(Regra newRegra) {
        int count = 0;
        if (newRegra.getDescricao().length() != 0) count++;
        if (newRegra.getIdUsuario().length() != 0) count++;
        if (newRegra.getIdCasa() != 0) count++;
        if (newRegra.getData().length() != 0) count++;
        if (newRegra.getNome().length() != 0) count++;
        return count;
    }
    void atualizaCamposTarefa(Tarefa updateTarefa, Tarefa oldTarefa){
        if (updateTarefa.getData() == null) updateTarefa.setData(oldTarefa.getData());
        if (updateTarefa.getIdResponsavel() == null)
            updateTarefa.setIdResponsavel(oldTarefa.getIdResponsavel());
        if (updateTarefa.getDescricao() == null)
            updateTarefa.setDescricao(oldTarefa.getDescricao());
        if (updateTarefa.getEstado() == null) updateTarefa.setDescricao(oldTarefa.getEstado());
        if (updateTarefa.getNome() == null) updateTarefa.setNome(oldTarefa.getNome());
        if (updateTarefa.getValor() == 0) updateTarefa.setValor(oldTarefa.getValor());
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
