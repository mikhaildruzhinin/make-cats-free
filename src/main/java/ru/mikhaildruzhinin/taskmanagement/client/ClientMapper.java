package ru.mikhaildruzhinin.taskmanagement.client;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.mikhaildruzhinin.taskmanagement.manager.ManagerMapper;
import ru.mikhaildruzhinin.taskmanagement.task.TaskMapper;

import java.util.List;
import java.util.Set;

@Mapper(uses = {ManagerMapper.class, TaskMapper.class} ,componentModel = "cdi")
public interface ClientMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "manager", expression = "java(null)")
    @Mapping(target = "tasks", expression = "java(Set.of())")
    Client toEntity(ClientRequestDto dto);

    @Named("ClientSetIgnoreManager")
    @IterableMapping(qualifiedByName = "ClientIgnoreManager")
    Set<ClientResponseDto> toDtoClientSetIgnoreManager(Set<Client> clients);

    @Named("ClientIgnoreManager")
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "tasks", qualifiedByName = "TaskSetIgnoreClient")
    ClientResponseDto toDtoClientIgnoreManager(Client client);

    @Named("ClientIgnoreTasks")
    @Mapping(target = "manager", qualifiedByName = "ManagerIgnoreClients")
    @Mapping(target = "tasks", ignore = true)
    ClientResponseDto toDtoClientIgnoreTasks(Client client);

    @IterableMapping(qualifiedByName = "Client")
    List<ClientResponseDto> toDtoList(List<Client> client);

    @Named("Client")
    @Mapping(target = "manager", qualifiedByName = "ManagerIgnoreClients")
    @Mapping(target = "tasks", qualifiedByName = "TaskSetIgnoreClient")
    ClientResponseDto toDto(Client client);
}
