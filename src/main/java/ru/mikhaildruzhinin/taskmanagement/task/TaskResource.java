package ru.mikhaildruzhinin.taskmanagement.task;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.taskmanagement.ResponseMessage;

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
  public TaskDto getTask(@PathParam("id") Long id) {
    Task task = repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    return task.toDto();
  }

  @POST
  @Transactional
  public ResponseMessage addTask(TaskDto taskDto) {
    Task task = taskDto.toEntity();
    repository.persist(task);
    return new ResponseMessage("Ok");
  }

  @PUT
  @Path("/{id}")
  public ResponseMessage updateTask(@PathParam("id") Long id, TaskDto taskDto) {
    Task task = taskDto.toEntity();
    task.setId(id);
    boolean isUpdated = repository.update(task);
    return new ResponseMessage(Boolean.toString(isUpdated));
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public ResponseMessage deleteTask(@PathParam("id") Long id) {
    boolean isDeleted = repository.deleteById(id);
    return new ResponseMessage(Boolean.toString(isDeleted));
  }
}
