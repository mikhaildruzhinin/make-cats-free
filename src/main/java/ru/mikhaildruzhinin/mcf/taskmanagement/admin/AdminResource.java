package ru.mikhaildruzhinin.mcf.taskmanagement.admin;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.Client;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.user.User;
import ru.mikhaildruzhinin.mcf.taskmanagement.user.UserRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.Worker;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerRepository;

import java.net.URI;
import java.util.List;

@Path("/admin")
@RolesAllowed("admin")
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

        public static native TemplateInstance newWorker(List<ManagerDto> managers);

        public static native TemplateInstance editWorker(List<ManagerDto> managers, WorkerDto worker);

        public static native TemplateInstance newClient(List<ManagerDto> managers);

        public static native TemplateInstance editClient(List<ManagerDto> managers, ClientDto client);
    }

    @Inject
    UserRepository userRepository;

    @Inject
    ManagerRepository managerRepository;

    @Inject
    WorkerRepository workerRepository;

    @Inject
    ClientRepository clientRepository;

    // TODO addReturningId exception mapper

    @GET
    @WithSession
    public Uni<TemplateInstance> admin() {
        Uni<List<ManagerDto>> managers = managerRepository.getAll();
        Uni<List<WorkerDto>> workers = workerRepository.getAll();
        Uni<List<ClientDto>> clients = clientRepository.getAll();

        return Uni.combine()
                .all()
                .unis(managers, workers, clients)
                .usingConcurrencyOf(1)
                .asTuple()
                .map(tuple -> Templates.admin(tuple.getItem1(), tuple.getItem2(), tuple.getItem3()));
    }

    @GET
    @Path("/manager")
    @WithSession
    public Uni<TemplateInstance> getNewManager() {
        return Uni.createFrom().item(Templates.newManager());
    }

    @SuppressWarnings("ReactiveStreamsTooLongSameOperatorsChain")
    @POST
    @Path("/manager")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addManager(@FormParam("name") String name) {
        User user = new User(name, "manager");
        return userRepository.persist(user)
                .map(u -> new Manager(u.getId(), name, u))
                .flatMap(manager -> managerRepository.persist(manager))
                .map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @GET
    @Path("/manager/{id}")
    @WithSession
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
        return managerRepository.update(id, name).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @POST
    @Path("/manager/{id}/delete")
    @WithSession
    @WithTransaction
    public Uni<Response> deleteManager(@PathParam("id") Long id) {
        // FIXME org.hibernate.exception.ConstraintViolationException
        return managerRepository.deleteById(id).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @GET
    @Path("/worker")
    @WithSession
    public Uni<TemplateInstance> getNewWorker() {
        return managerRepository.getAll().map(Templates::newWorker);
    }

    @SuppressWarnings("ReactiveStreamsTooLongSameOperatorsChain")
    @POST
    @Path("/worker")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addWorker(@FormParam("name") String name, @FormParam("manager_id") Long managerId) {

        Uni<User> user = userRepository.persist(new User(name, "worker"));
        Uni<Manager> manager = managerRepository.getEntity(managerId);

        return user.flatMap(u -> manager.map(m -> new Worker(u.getId(), name, m, u))
                .chain(worker -> workerRepository.persist(worker))
                .map(x -> Response.seeOther(URI.create("admin")).build()));

    }

    @GET
    @Path("/worker/{id}")
    @WithSession
    public Uni<TemplateInstance> getWorker(@PathParam("id") Long id) {
        // FIXME io.quarkus.qute.TemplateException
        Uni<List<ManagerDto>> managers = managerRepository.getAll();
        Uni<WorkerDto> worker = workerRepository.get(id);

        return Uni.combine()
                .all()
                .unis(managers, worker)
                .usingConcurrencyOf(1)
                .asTuple()
                .map(tuple -> Templates.editWorker(tuple.getItem1(), tuple.getItem2()));
    }

    @POST
    @Path("/worker/{id}")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> updateWorker(
            @PathParam("id") Long id,
            @FormParam("name") String name,
            @FormParam("manager_id") Long managerId) {
        return workerRepository.update(id, name, managerId).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @POST
    @Path("/worker/{id}/delete")
    @WithSession
    @WithTransaction
    public Uni<Response> deleteWorker(@PathParam("id") Long id) {
        // FIXME org.hibernate.exception.ConstraintViolationException
        return workerRepository.deleteById(id).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @GET
    @Path("/client")
    @WithSession
    public Uni<TemplateInstance> getNewClient() {
        return managerRepository.getAll().map(Templates::newClient);
    }

    @POST
    @Path("/client")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> addClient(@FormParam("name") String name, @FormParam("manager_id") Long managerId) {

        Uni<User> user = userRepository.persist(new User(name, "client"));
        Uni<Manager> manager = managerRepository.getEntity(managerId);

        return user.flatMap(u -> manager.map(m -> new Client(u.getId(), name, m, u))
                .chain(client -> clientRepository.persist(client))
                .map(x -> Response.seeOther(URI.create("admin")).build()));
    }

    @GET
    @Path("/client/{id}")
    @WithSession
    public Uni<TemplateInstance> getClient(@PathParam("id") Long id) {
        // FIXME io.quarkus.qute.TemplateException
        Uni<List<ManagerDto>> managers = managerRepository.getAll();
        Uni<ClientDto> client = clientRepository.get(id);

        return Uni.combine()
                .all()
                .unis(managers, client)
                .usingConcurrencyOf(1)
                .asTuple()
                .map(tuple -> Templates.editClient(tuple.getItem1(), tuple.getItem2()));
    }

    @POST
    @Path("/client/{id}")
    @WithSession
    @WithTransaction
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> updateClient(
            @PathParam("id") Long id,
            @FormParam("name") String name,
            @FormParam("manager_id") Long managerId) {
        return clientRepository.update(id, name, managerId).map(x -> Response.seeOther(URI.create("admin")).build());
    }

    @POST
    @Path("/client/{id}/delete")
    @WithSession
    @WithTransaction
    public Uni<Response> deleteClient(@PathParam("id") Long id) {
        // FIXME org.hibernate.exception.ConstraintViolationException
        return clientRepository.deleteById(id).map(x -> Response.seeOther(URI.create("admin")).build());
    }
}
