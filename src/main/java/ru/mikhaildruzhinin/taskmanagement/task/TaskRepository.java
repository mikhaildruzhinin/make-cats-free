package ru.mikhaildruzhinin.taskmanagement.task;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

    @Transactional
    public boolean update(Long id, Task newTask) {
        newTask.setId(id);
        Optional<Task> optionalTask = findByIdOptional(newTask.getId());
        Optional<Boolean> optionalUpdated = optionalTask.map(task -> {
            task.setTitle(newTask.getTitle());
            task.setDescription(newTask.getDescription());
            task.setClient(newTask.getClient());
            return true;
        });
        return optionalUpdated.orElse(false);
    }
}
