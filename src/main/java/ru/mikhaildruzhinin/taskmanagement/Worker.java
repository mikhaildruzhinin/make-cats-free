package ru.mikhaildruzhinin.taskmanagement;

public class Worker {

  private int id;
  private String name;

  public Worker() {
  }

  public Worker(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
