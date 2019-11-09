package com.bsc.hometasks.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
public class HomeTasks {

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
