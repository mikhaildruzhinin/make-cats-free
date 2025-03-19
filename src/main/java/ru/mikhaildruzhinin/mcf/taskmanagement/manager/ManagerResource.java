package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.*;

import java.util.List;

@Path("/manager")
@RolesAllowed("manager")
@Produces(MediaType.TEXT_HTML)
public class ManagerResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance manager(List<Worker> workers, List<Client> clients);
    }

    @Inject
    ManagerRepository managerRepository;

    @Inject
    WorkerRepository workerRepository;

    @Inject
    ClientRepository clientRepository;

    @GET
    public Uni<TemplateInstance> manager(@Context SecurityContext context) {
        String username = context.getUserPrincipal().getName();
        Uni<Manager> manager = managerRepository.findByName(username);
        Uni<List<Worker>> workers = manager.flatMap(m -> workerRepository.findByManagerId(m.getId()));
        Uni<List<Client>> clients = manager.flatMap(m -> clientRepository.findByManagerId(m.getId()));
        return Uni.combine()
                .all()
                .unis(workers, clients)
                .usingConcurrencyOf(1)
                .asTuple()
                .map(t -> Templates.manager(t.getItem1(), t.getItem2()));
    }
}
