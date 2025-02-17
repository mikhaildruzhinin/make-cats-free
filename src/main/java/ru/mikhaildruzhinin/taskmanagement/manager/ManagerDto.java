package ru.mikhaildruzhinin.taskmanagement.manager;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.List;

public record ManagerDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Schema(defaultValue = "0")
        Long id,
        @NotBlank
        @Schema(defaultValue = "John Doe")
        String name,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonManagedReference
        List<Client> clients
) {
    public Manager toEntity() {
        return new Manager(id, name, clients);
    }
}
