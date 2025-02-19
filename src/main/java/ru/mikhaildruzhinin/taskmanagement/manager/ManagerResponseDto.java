package ru.mikhaildruzhinin.taskmanagement.manager;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ru.mikhaildruzhinin.taskmanagement.client.ClientResponseDto;

import java.util.Set;

public record ManagerResponseDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Schema(defaultValue = "0")
        Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Schema(defaultValue = "John Doe")
        String name,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonManagedReference
        Set<ClientResponseDto> clients
) {
}
