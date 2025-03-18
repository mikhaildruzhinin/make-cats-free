package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.Task;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.TaskRepository;

import java.net.URI;

@SuppressWarnings("ReactiveStreamsTooLongSameOperatorsChain")
@Path("/client")
@RolesAllowed("client")
@Produces(MediaType.TEXT_HTML)
public class ClientResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance client(String name);

        public static native TemplateInstance newTask();
    }

    @Inject
    ClientRepository clientRepository;

    @Inject
    TaskRepository taskRepository;

    @GET
    public Uni<TemplateInstance> client(@Context SecurityContext context) {
        return Uni.createFrom().item(Templates.client(context.getUserPrincipal().getName()));
    }

    @GET
    @Path("/task")
    @WithSession
    public Uni<TemplateInstance> getNewTask() {
        return Uni.createFrom().item(Templates.newTask());
    }

    @POST
    @Path("/task")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addTask(@FormParam("title") String title,
                                 @FormParam("desc") String description,
                                 @Context SecurityContext context) {

        String username = context.getUserPrincipal().getName();
        return clientRepository.findByName(username)
                .map(client -> new Task(title, description, client))
                .flatMap(task -> taskRepository.persist(task))
                .map(x -> Response.seeOther(URI.create("/client")).build());
    }
}
