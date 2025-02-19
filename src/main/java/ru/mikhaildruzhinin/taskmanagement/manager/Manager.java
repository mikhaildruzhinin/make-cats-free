package ru.mikhaildruzhinin.taskmanagement.manager;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import ru.mikhaildruzhinin.taskmanagement.client.Client;
import ru.mikhaildruzhinin.taskmanagement.client.ClientResponseDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Manager {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    final private Set<Client> clients = new HashSet<>();

    public Manager() {
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

    public ManagerResponseDto toDto() {
        Set<ClientResponseDto> clients = this.clients.stream()
                .map(ClientResponseDto::new)
                .collect(Collectors.toSet());
        return new ManagerResponseDto(id, name, clients);
    }
}
