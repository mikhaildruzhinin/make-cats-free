package ru.mikhaildruzhinin.taskmanagement.task;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/tasks")
@Tag(name = "Tasks")
public class TaskResource {

  @Inject
  TaskRepository repository;

  @GET
  public TasksDto getTasks() {
    List<TaskDto> tasks = repository.listAll()
            .stream()
            .map(Task::toDto)
            .toList();
    return new TasksDto(tasks);
  }

  @GET
  @Path("/{id}")
  public Response getTask(@PathParam("id") Long id) {
    Task task = repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    return Response.ok(task.toDto()).build();
  }

  @POST
  @Transactional
  public Response addTask(TaskDto taskDto) {
    Task task = taskDto.toEntity();
    repository.persist(task);
    return Response.noContent().build();
  }

  @PUT
  @Path("/{id}")
  public Response updateTask(@PathParam("id") Long id, TaskDto taskDto) {
    Task task = taskDto.toEntity();
    task.setId(id);
    boolean isUpdated = repository.update(task);
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
