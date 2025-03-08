package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/client")
@RolesAllowed("client")
@Produces(MediaType.TEXT_HTML)
public class ClientResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance client();
    }

    @Inject
    ClientRepository clientRepository;

    @GET
    public Uni<TemplateInstance> client() {
        return Uni.createFrom().item(Templates.client());
    }
}
