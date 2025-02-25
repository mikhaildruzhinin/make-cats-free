package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.Client;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.Worker;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "mcf", name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    final private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    final private Set<Worker> workers = new HashSet<>();

    protected Manager() {
    }

    public Manager(String name) {
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
}
