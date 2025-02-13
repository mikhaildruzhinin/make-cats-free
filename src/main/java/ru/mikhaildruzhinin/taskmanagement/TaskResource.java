package ru.mikhaildruzhinin.taskmanagement;


import jakarta.ws.rs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tasks")
public class TaskResource {

  private final List<Task> tasks = new ArrayList<>();

  public TaskResource() {
    tasks.add(new Task(1, "task1", "desc1"));
    tasks.add(new Task(2, "task2", "desc2"));
    tasks.add(new Task(3, "task3", "desc3"));
  }

  @GET
  public List<Task> getTasks() {
    return tasks;
  }

  @GET
  @Path("/{id}")
  public Task getTask(@PathParam("id") int id) {
    return tasks.stream()
            .filter(task -> task.id == id)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Not Found"));
  }

  @POST
  public List<Task> addTask(Task task) {
    tasks.add(task);
    return tasks;
  }

  @PUT
  @Path("/{id}")
  public List<Task> updateTask(@SuppressWarnings("unused") @PathParam("id") int id) {
    // TODO
    return tasks;
  }

  @DELETE
  @Path("/{id}")
  public List<Task> deleteTask(@PathParam("id") int id) {
    return tasks.stream()
            .dropWhile(task -> task.id == id)
            .collect(Collectors.toList());
  }
}
