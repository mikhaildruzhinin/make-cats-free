package ru.mikhaildruzhinin.mcf.taskmanagement;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.net.URI;

@Path("/")
@RolesAllowed({"admin", "user"})
@Produces(MediaType.TEXT_HTML)
public class IndexResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index();
    }

    @ServerExceptionMapper
    public Uni<Response> mapException(UnauthorizedException e) {
        return Uni.createFrom().item(Response.seeOther(URI.create("login")).build());
    }

    @GET
    public Uni<TemplateInstance> index() {
        return Uni.createFrom().item(Templates.index());
    }

}
