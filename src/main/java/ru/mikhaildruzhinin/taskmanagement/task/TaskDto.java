package ru.mikhaildruzhinin.taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.Optional;

public record TaskDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
        @NotBlank()
        String title,
        @NotBlank()
        String description,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        Optional<Client> client,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        Long clientId
) {
        public Task toEntity() {
                return new Task(
                        this.id,
                        this.title,
                        this.description,
                        this.client.orElse(null),
                        this.clientId
                );
        }
}
