package ru.mikhaildruzhinin.taskmanagement;

public class Manager {

  private int id;
  private String name;

  public Manager() {
  }

  public Manager(int id, String name) {
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
