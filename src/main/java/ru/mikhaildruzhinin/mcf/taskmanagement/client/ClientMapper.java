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

    @Named("ClientSetIgnoreManagerTasks")
    @IterableMapping(qualifiedByName = "ClientIgnoreManagerTasks")
    Set<ClientResponseDto> toDtoClientSetIgnoreManagerTasks(Set<Client> clients);

    @Named("ClientIgnoreManagerTasks")
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    ClientResponseDto toDtoClientIgnoreManagerTasks(Client client);

    @IterableMapping(qualifiedByName = "Client")
    List<ClientResponseDto> toDtoList(List<Client> client);

    @Named("Client")
    @Mapping(target = "manager", qualifiedByName = "ManagerIgnoreWorkersClients")
    @Mapping(target = "tasks", qualifiedByName = "TaskSetIgnoreWorkerClient")
    ClientResponseDto toDto(Client client);
}
