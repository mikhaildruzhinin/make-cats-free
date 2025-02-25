package ru.mikhaildruzhinin.mcf.taskmanagement.admin;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerRepository;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerRepository;

import java.util.List;

@Path("admin")
@Produces(MediaType.TEXT_HTML)
public class AdminResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance admin(AdminResponseDto dto);
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
                .transform(tuple -> new AdminResponseDto(tuple.getItem1(), tuple.getItem3(), tuple.getItem2()))
                .onItem()
                .transform(Templates::admin);
    }
}
