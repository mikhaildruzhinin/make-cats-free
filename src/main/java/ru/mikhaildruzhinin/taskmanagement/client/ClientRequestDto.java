package ru.mikhaildruzhinin.taskmanagement.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record ClientRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank
        String name,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        Optional<Long> managerId
) {
    public Client toEntity() {
        return new Client(name);
    }
}
