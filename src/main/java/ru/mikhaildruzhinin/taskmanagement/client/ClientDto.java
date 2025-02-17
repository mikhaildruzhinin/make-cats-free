package ru.mikhaildruzhinin.taskmanagement.client;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.taskmanagement.task.Task;

import java.util.Optional;
import java.util.Set;

public record ClientDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        @NotBlank
        String name,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonManagedReference
        Optional<Set<Task>> tasks,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        Manager manager,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull
        Long managerId
) {
    public Client toEntity() {
        return new Client(id, name, tasks.orElse(null), manager);
    }
}
