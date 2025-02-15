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
  public List<Task> getTasks() {
    return repository.listAll();
  }

  @GET
  @Path("/{id}")
  public Task getTask(@PathParam("id") Long id) {
    return repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
  }

  @POST
  @Transactional
  public ResponseMessage addTask(Task task) {
    repository.persist(task);
    return new ResponseMessage("Ok");
  }

  @PUT
  @Path("/{id}")
  public Task updateTask(@PathParam("id") Long id, Task task) {
    task.setId(id);
    return repository.update(task);
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public ResponseMessage deleteTask(@PathParam("id") Long id) {
    boolean isDeleted = repository.deleteById(id);
    return new ResponseMessage(Boolean.toString(isDeleted));
  }
}
