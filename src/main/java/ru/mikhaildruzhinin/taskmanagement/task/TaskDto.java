package ru.mikhaildruzhinin.taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.Optional;

public record TaskDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
        @NotBlank(message="Title may not be blank")
        String title,
        @NotBlank(message="Description may not be blank")
        String description,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        Optional<Client> client
) {
        public Task toEntity() {
                return new Task(
                        this.id,
                        this.title,
                        this.description,
                        this.client.orElse(null),
                        this.client.map(Client::getId).orElse(null)
                );
        }
}
