package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.Task;
import ru.mikhaildruzhinin.mcf.taskmanagement.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "mcf", name = "workers")
public class Worker {

    @Id
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "worker")
    final private Set<Task> tasks = new HashSet<>();

    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    protected Worker() {
    }

    public Worker(Long id, String name, Manager manager, User user) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.user = user;
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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
