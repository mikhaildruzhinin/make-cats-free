package ru.mikhaildruzhinin.taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.Optional;

public record TaskRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        String title,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        String description,
        Optional<Client> client,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        Long clientId
) {
}
