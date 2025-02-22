package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import org.mapstruct.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.MappingConfig;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientMapper;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerMapper;

import java.util.List;

@Mapper(uses = {ClientMapper.class, WorkerMapper.class}, config = MappingConfig.class)
public interface ManagerMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "clients", expression = "java(Set.of())")
    @Mapping(target = "workers", expression = "java(Set.of())")
    Manager toEntity(ManagerRequestDto dto);

    @Named("ManagerIgnoreWorkersClients")
    @Mapping(target = "workers", ignore = true)
    @Mapping(target = "clients", ignore = true)
    ManagerResponseDto toDtoManagerIgnoreWorkersClients(Manager manager);

    @IterableMapping(qualifiedByName = "Manager")
    List<ManagerResponseDto> toDtoList(List<Manager> managers);

    @Named("Manager")
    @Mapping(target = "clients", qualifiedByName = "ClientSetIgnoreManagerTasks")
    @Mapping(target = "workers", qualifiedByName = "WorkerSetIgnoreManagerTasks")
    ManagerResponseDto toDto(Manager manager);
}
