package ru.mikhaildruzhinin.taskmanagement.task;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    @Transactional
    public Task update(Task newTask) {
        Optional<Task> optionalTask = findByIdOptional(newTask.getId());
        optionalTask.map(task -> {
            persist(newTask);
            return newTask;
        });
        return optionalTask.orElseThrow(NotFoundException::new);
    }
}
