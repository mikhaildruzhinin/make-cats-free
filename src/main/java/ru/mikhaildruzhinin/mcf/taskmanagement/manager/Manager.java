package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.Client;
import ru.mikhaildruzhinin.mcf.taskmanagement.user.User;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.Worker;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "mcf", name = "managers")
public class Manager {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "manager")
    final private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    final private Set<Worker> workers = new HashSet<>();

    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    protected Manager() {
    }

    public Manager(Long id, String name, User user) {
        this.id = id;
        this.name = name;
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

    public Set<Client> getClients() {
        return clients;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addClients(Set<Client> clients) {
        this.clients.addAll(clients);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void removeClients(Set<Client> clients) {
        this.clients.removeAll(clients);
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void addWorkers(Set<Worker> workers) {
        this.workers.addAll(workers);
    }

    public void removeWorker(Worker worker) {
        workers.remove(worker);
    }

    public void removeWorkers(Set<Worker> workers) {
        this.workers.removeAll(workers);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
