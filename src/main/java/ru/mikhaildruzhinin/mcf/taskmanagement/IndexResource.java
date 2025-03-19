package ru.mikhaildruzhinin.mcf.taskmanagement;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.net.URI;

@Path("/")
@RolesAllowed({"admin", "manager", "worker", "client"})
@Produces(MediaType.TEXT_HTML)
public class IndexResource {

    @ServerExceptionMapper
    public Uni<Response> mapException(UnauthorizedException e) {
        return Uni.createFrom().item(Response.seeOther(URI.create("login")).build());
    }

    @GET
    public Uni<Response> index(@Context SecurityContext context) {
         URI uri = switch (context) {
            case SecurityContext c when c.isUserInRole("admin") -> URI.create("admin");
            case SecurityContext c when c.isUserInRole("client") -> URI.create("client");
            case SecurityContext c when c.isUserInRole("manager") -> URI.create("manager");
            default -> throw new NotFoundException();
        };
        return Uni.createFrom().item(Response.seeOther(uri).build());
    }
}
