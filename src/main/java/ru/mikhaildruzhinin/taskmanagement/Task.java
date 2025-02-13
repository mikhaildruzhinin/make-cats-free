package ru.mikhaildruzhinin.taskmanagement;

public class Task {

  private int id;
  private String title;
  private String description;

  @SuppressWarnings("unused")
  public Task() {
  }

  public Task(int id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }
}
