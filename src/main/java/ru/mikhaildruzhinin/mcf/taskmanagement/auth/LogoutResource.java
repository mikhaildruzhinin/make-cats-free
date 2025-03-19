package ru.mikhaildruzhinin.mcf.taskmanagement.auth;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/logout")
@PermitAll
public class LogoutResource {

    @POST
    public Uni<Response> logout() {
        return Uni.createFrom().item(Response.seeOther(URI.create("/login")).build());
    }
}
