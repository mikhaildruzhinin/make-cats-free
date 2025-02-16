package ru.mikhaildruzhinin.taskmanagement.task;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.Optional;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @Column(name = "client_id")
    private Long clientId;

    public Task() {
    }

    public Task(Long id, String title, String description, Client client, Long clientId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.client = client;
        this.clientId = clientId;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Client getClient() {
        return client;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public TaskDto toDto() {
        return new TaskDto(
                this.id,
                this.title,
                this.description,
                Optional.ofNullable(this.client)
        );
    }
}
