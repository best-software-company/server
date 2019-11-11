package com.bsc.hometasks.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.bsc.hometasks.db.dao.UsuarioDAO;
import com.bsc.hometasks.pojo.*;
import com.bsc.hometasks.db.*;
import com.bsc.hometasks.rest.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class HomeTasks {
    Boolean token = false;
    UsuarioDAO user = new UsuarioDAO();

    @Path("/login/{credenciais}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response login(@PathParam("credenciais") String credenciais) {
        String log[] = credenciais.split(":");

        Usuario buscarUser = null;
        if(log.length==2){
            buscarUser = user.buscaUsuario(log[0]);
            if( buscarUser!= null){
                if(buscarUser.getSenha()==log[1]){
                    token = true;
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
    @Path("/users/{newUser}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addUser(Usuario newUser, @Context UriInfo uriInfo) {
        UsuarioDAO user = new UsuarioDAO();
        if(newUser.getNome()!=null & newUser.getIdUsuario()!=null & newUser.getSenha()!=null){
            if(user.buscaUsuariosId(newUser.getIdUsuario())!=null){
                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                        "    \"error\": \"\"Login já existe\"\n" +
                        "}").build();
            }

            if(user.criaUsuario(newUser)){
                return Response.status(Response.Status.CREATED).entity(newUser).build();
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"\"Atributos Obrigatórios - full_name, cpf, login e password\"\n" +
                "}").build();

    }

    @Path("/users/{nome}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response searchUsers(@PathParam("nome") String nome) {
        if(token==true){
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

    /*@Path("/users/{updateUser}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(Usuario updateUser, @Context UriInfo uriInfo) {
        UsuarioDAO user = new UsuarioDAO();
        if(updateUser.getNome()!=null & newUser.getIdUsuario()!=null & newUser.getSenha()!=null){
            if(user.buscaUsuariosId(newUser.getIdUsuario())!=null){
                return Response.status(Response.Status.CONFLICT).entity("{\n" +
                        "    \"error\": \"\"Login já existe\"\n" +
                        "}").build();
            }

            if(user.criaUsuario(newUser)){
                return Response.status(Response.Status.CREATED).entity(newUser).build();
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"\"Atributos Obrigatórios - full_name, cpf, login e password\"\n" +
                "}").build();

    }*/

}
