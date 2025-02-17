package ru.mikhaildruzhinin.taskmanagement.manager;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Manager {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Client> clients = new HashSet<>();

    public Manager() {
    }

    public Manager(Long id, String name, Set<Client> clients) {
        this.id = id;
        this.name = name;
        this.clients = clients;
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

    public ManagerDto toDto() {
        return new ManagerDto(id, name, Optional.ofNullable(clients));
    }
}
