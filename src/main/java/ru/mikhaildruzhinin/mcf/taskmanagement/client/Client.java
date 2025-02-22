package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.Task;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "mcf", name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    final private Set<Task> tasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    protected Client() {
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
}
