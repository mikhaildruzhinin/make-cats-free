package ru.mikhaildruzhinin.mcf.taskmanagement.admin;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("admin")
@Produces(MediaType.TEXT_HTML)
public class AdminResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance admin();
    }

    @GET
    public TemplateInstance get() {
        return Templates.admin();
    }
}
