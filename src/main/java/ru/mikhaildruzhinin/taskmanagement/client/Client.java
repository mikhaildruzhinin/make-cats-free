package ru.mikhaildruzhinin.taskmanagement.client;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.taskmanagement.task.Task;

import java.util.*;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    final private Set<Task> tasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", insertable = false, updatable = false)
    private Manager manager;

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTasks(Set<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void removeTasks(Set<Task> tasks) {
        this.tasks.removeAll(tasks);
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ClientResponseDto toDto() {
        return new ClientResponseDto(id, name, tasks, Optional.ofNullable(manager));
    }
}
