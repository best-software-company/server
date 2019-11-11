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
    Usuario user = new Usuario();
    Usuario user2 = new Usuario();

    @Path("/login/{credenciais}")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response index(@PathParam("credenciais") String credenciais) {
        String log[] = credenciais.split(":");
        UsuarioDAO user = new UsuarioDAO();
        Usuario buscarUser = null;
        if(log.length==2){
            buscarUser = user.buscaUsuario(log[0]);
            if(buscarUser.getSenha()==log[1]){
                token = true;
                return Response.ok(buscarUser).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Usuário não encontrado\"\n" +
                "}").build();

    }

    @Path("/users")
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    public Response index() {
        if(token==true){
            user.setNome("Luiza");
            user.setEmail("l.uhzinha@hotmail.com");
            user.setIdUsuario("1");
            List<Usuario> users = new ArrayList<>();
            users.add(user);
            user2.setNome("Felipe");
            user2.setEmail("fpcardoso@hotmail.com");
            user2.setIdUsuario("2");
            users.add(user2);
            return Response.ok(users).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("{\n" +
                "    \"error\": \"Permissão Negada\"\n" +
                "}").build();
    }

    @Path("/users/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampus(@PathParam("name") String name) {
        String teste = "Busca por " + name;
        if (name != null) {

            return Response.ok(teste).build();
        }
        throw new NotFoundException();
    }
}
