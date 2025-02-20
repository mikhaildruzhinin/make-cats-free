package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import org.mapstruct.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.MappingConfig;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.TaskMapper;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerMapper;

import java.util.List;
import java.util.Set;

@Mapper(uses = {ManagerMapper.class, TaskMapper.class}, config = MappingConfig.class)
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
