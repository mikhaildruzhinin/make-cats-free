package ru.mikhaildruzhinin.taskmanagement;

public class Task {

  public int id;
  public String title;
  public String description;

  @SuppressWarnings("unused")
  public Task() {}

  public Task(int id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }
}
