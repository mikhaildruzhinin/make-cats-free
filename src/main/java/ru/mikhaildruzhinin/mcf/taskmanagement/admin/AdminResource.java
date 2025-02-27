package ru.mikhaildruzhinin.mcf.taskmanagement.admin;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.annotations.NotFound;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerRepository;

import java.net.URI;
import java.util.List;

@Path("/admin")
@Produces(MediaType.TEXT_HTML)
public class AdminResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance admin(
                List<ManagerDto> managers,
                List<WorkerDto> workers,
                List<ClientDto> clients
        );

        public static native TemplateInstance newManager();

        public static native TemplateInstance editManager(ManagerDto manager);
    }

    @Inject
    ManagerRepository managerRepository;

    @Inject
    WorkerRepository workerRepository;

    @Inject
    ClientRepository clientRepository;

    @GET
    @WithSession
    public Uni<TemplateInstance> get() {
        Uni<List<ManagerDto>> managers = managerRepository.getAll();
        Uni<List<WorkerDto>> workers = workerRepository.getAll();
        Uni<List<ClientDto>> clients = clientRepository.getAll();

        return Uni.combine()
                .all()
                .unis(managers, workers, clients)
                .usingConcurrencyOf(1)
                .asTuple()
                .onItem()
                .transform(tuple -> Templates.admin(tuple.getItem1(), tuple.getItem2(), tuple.getItem3()));
    }

    @GET
    @Path("/manager")
    @WithSession
    public Uni<TemplateInstance> getNewManager() {
        return Uni.createFrom().item(Templates.newManager());
    }

    @POST
    @Path("/manager")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addManager(@FormParam("name") String name) {
        return managerRepository.persist(new Manager(name)).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @GET
    @Path("/manager/{id}")
    @WithSession
    @NotFound
    public Uni<TemplateInstance> getManager(@PathParam("id") Long id) {
        // FIXME io.quarkus.qute.TemplateException:
        return managerRepository.get(id).map(Templates::editManager);

    }

    @POST
    @Path("/manager/{id}")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> updateManager(@PathParam("id") Long id, @FormParam("name") String name) {
        return managerRepository.updateName(id, name).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @POST
    @Path("/manager/{id}/delete")
    @WithSession
    @WithTransaction
    public Uni<Response> deleteManager(@PathParam("id") Long id) {
        // FIXME org.hibernate.exception.ConstraintViolationException
        return managerRepository.deleteById(id).map(x -> Response.seeOther(URI.create("admin")).build());
    }
    }
}
