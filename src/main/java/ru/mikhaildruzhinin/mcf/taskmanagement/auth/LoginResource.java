package ru.mikhaildruzhinin.mcf.taskmanagement.auth;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
@PermitAll
@Produces(MediaType.TEXT_HTML)
public class LoginResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance login();

        public static native TemplateInstance error();
    }

    @GET
    @WithSession
    public Uni<TemplateInstance> login() {
        return Uni.createFrom().item(Templates.login());
    }

    @GET
    @Path("/error")
    @WithSession
    public Uni<TemplateInstance> error() {
        return Uni.createFrom().item(Templates.error());
    }
}
