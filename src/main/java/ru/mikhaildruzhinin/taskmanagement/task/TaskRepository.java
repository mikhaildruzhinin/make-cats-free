package ru.mikhaildruzhinin.taskmanagement.task;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    @Transactional
    public Task update(Task newTask) {
        Optional<Task> optionalTask = findByIdOptional(newTask.id());
        optionalTask.map(task -> {
            persist(newTask);
            return newTask;
        });
        return optionalTask.orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
