package ru.mikhaildruzhinin.taskmanagement.task;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/tasks")
@Tag(name = "Tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

  @Inject
  TaskRepository repository;

  @Inject
  TaskMapper mapper;

  @GET
  public TasksResponseDto getTasks() {
    List<TaskResponseDto> tasks = repository.listAll()
            .stream()
            .map(task -> mapper.toDto(task))
            .toList();
    return new TasksResponseDto(tasks);
  }

  @GET
  @Path("/{id}")
  public Response getTask(@PathParam("id") Long id) {
    Task task = repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    return Response.ok(mapper.toDto(task)).build();
  }

  @POST
  @Transactional
  public Response addTask(TaskRequestDto dto) {
    Task task = mapper.toEntity(dto);
    repository.persist(task);
    return Response.noContent().build();
  }

  @PUT
  @Path("/{id}")
  public Response updateTask(@PathParam("id") Long id, TaskRequestDto dto) {
    Task task = mapper.toEntity(dto);
    boolean isUpdated = repository.update(id, task);
    if (isUpdated) {
      return Response.ok().build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteTask(@PathParam("id") Long id) {
    boolean isDeleted = repository.deleteById(id);
    if (isDeleted) {
      return Response.ok().build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }
}
