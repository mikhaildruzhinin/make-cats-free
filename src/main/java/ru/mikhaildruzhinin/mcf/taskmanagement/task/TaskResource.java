package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.Client;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientRepository;

import java.util.List;
import java.util.Optional;

@Path("/tasks")
@Tag(name = "Tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

  @Inject
  TaskRepository taskRepository;

  @Inject
  ClientRepository clientRepository;

  @Inject
  TaskMapper mapper;

  @GET
  public Response getTasks() {
    List<TaskResponseDto> tasks = mapper.toDtoList(taskRepository.listAll());
    TasksResponseDto dto = new TasksResponseDto(tasks);
    return Response.ok(dto).build();
  }

  @GET
  @Path("/{id}")
  public Response getTask(@PathParam("id") Long id) {
    Optional<TaskResponseDto> optionalDto = taskRepository.findByIdOptional(id)
            .map(task -> mapper.toDto(task));
    ResponseBuilder rb = optionalDto.map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND));
    return rb.build();
  }

  @POST
  @Transactional
  public Response addTask(TaskRequestDto dto) {
    Task task = mapper.toEntity(dto);
    Optional<Client> optionalClient = clientRepository.findByIdOptional(dto.clientId());
    Optional<Boolean> isSaved = optionalClient.map(client -> {
      task.setClient(client);
      taskRepository.persist(task);
      return true;
    });
    ResponseBuilder rb = isSaved.orElse(false) ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
    return rb.build();
  }

  @PUT
  @Path("/{id}")
  public Response updateTask(@PathParam("id") Long id, TaskRequestDto dto) {
    Optional<Task> optionalTask = taskRepository.findByIdOptional(id);
    Optional<Client> optionalClient = clientRepository.findByIdOptional(dto.clientId());
    Optional<Boolean> isUpdated = optionalTask.flatMap(task -> optionalClient.map(client -> {
      task.setTitle(dto.title());
      task.setDescription(dto.description());
      task.setClient(client);
      return true;
    }));
    ResponseBuilder rb = isUpdated.orElse(false) ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
    return rb.build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteTask(@PathParam("id") Long id) {
    boolean isDeleted = taskRepository.deleteById(id);
    ResponseBuilder rb = isDeleted ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
    return rb.build();
  }
}
