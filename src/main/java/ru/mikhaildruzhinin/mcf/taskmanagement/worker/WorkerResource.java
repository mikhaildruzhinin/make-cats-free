package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerRepository;

import java.util.List;
import java.util.Optional;

@Path("api/workers")
@Tag(name = "Workers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkerResource {

    @Inject
    WorkerRepository workerRepository;

    @Inject
    ManagerRepository managerRepository;

    @Inject
    WorkerMapper mapper;

    @GET
    public Response getWorkers() {
        List<WorkerResponseDto> workers = mapper.toDtoList(workerRepository.listAll());
        WorkersResponseDto dto = new WorkersResponseDto(workers);
        return Response.ok(dto).build();

    }

    @GET
    @Path("/{id}")
    public Response getWorker(@PathParam("id") Long id) {
        Optional<WorkerResponseDto> optionalDto = workerRepository.findByIdOptional(id)
                .map(worker -> mapper.toDto(worker));
        ResponseBuilder rb = optionalDto.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND));
        return rb.build();
    }

    @POST
    @Transactional
    public Response addWorker(WorkerRequestDto dto) {
        Worker worker = mapper.toEntity(dto);
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        Optional<Boolean> isSaved = optionalManager.map(manager -> {
            worker.setManager(manager);
            workerRepository.persist(worker);
            return true;
        });
        ResponseBuilder rb = isSaved.orElse(false) ? Response.noContent() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateWorker(@PathParam("id") Long id, WorkerRequestDto dto) {
        Optional<Worker> optionalWorker = workerRepository.findByIdOptional(id);
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        Optional<Boolean> isUpdated = optionalWorker.flatMap(worker -> optionalManager.map(manager -> {
            worker.setName(dto.name());
            worker.setManager(manager);
            return true;
        }));
        ResponseBuilder rb = isUpdated.orElse(false) ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteWorker(@PathParam("id") Long id) {
        boolean isDeleted = workerRepository.deleteById(id);
        ResponseBuilder rb = isDeleted ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }
}
