package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import jakarta.persistence.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.Client;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    protected Task() {
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
}
