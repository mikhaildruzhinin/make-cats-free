package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.*;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SuppressWarnings("ReactiveStreamsTooLongSameOperatorsChain")
@Path("/client")
@RolesAllowed("client")
@Produces(MediaType.TEXT_HTML)
public class ClientResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance client(String name, List<TaskDto> tasks);

        public static native TemplateInstance newTask();
    }

    @Inject
    ClientRepository clientRepository;

    @Inject
    WorkerRepository workerRepository;

    @Inject
    TaskRepository taskRepository;

    @GET
    public Uni<TemplateInstance> client(@Context SecurityContext context) {
        String username = context.getUserPrincipal().getName();
        return clientRepository.findByName(username)
                .flatMap(c -> taskRepository.filterByClientId(c.getId()))
                .map(tasks -> Templates.client(username, tasks));
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
                                 @FormParam("task-execution-datetime") String executionTime,
                                 @Context SecurityContext context) {

        String username = context.getUserPrincipal().getName();
        int price = 100;
        Instant executedAt = LocalDateTime.parse(executionTime, DateTimeFormatter.ISO_DATE_TIME).toInstant(ZoneOffset.UTC);
        Uni<Client> client = clientRepository.findByName(username);
        Uni<Worker> worker = workerRepository.findRandom();
        return Uni.combine()
                .all()
                .unis(client, worker)
                .usingConcurrencyOf(1)
                .asTuple()
                .map(t -> new Task(title, description, price, t.getItem1(), t.getItem2(), executedAt))
                .flatMap(task -> taskRepository.persist(task))
                .map(x -> Response.seeOther(URI.create("/client")).build());
    }
}
