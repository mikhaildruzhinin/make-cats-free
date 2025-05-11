package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    public Uni<List<TaskDto>> filterByClientId(Long clientId) {
        return find("select id, title, description, price from Task where client.id = ?1", clientId)
                .project(TaskDto.class)
                .list();
    }
}
