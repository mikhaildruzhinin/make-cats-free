package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkerRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank
        String name,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotNull
        Long managerId
) {
}
