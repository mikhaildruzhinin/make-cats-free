package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import org.mapstruct.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.MappingConfig;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientMapper;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.TaskMapper;

import java.util.List;

@Mapper(uses = {ClientMapper.class, TaskMapper.class}, config = MappingConfig.class)
public interface ManagerMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "clients", expression = "java(Set.of())")
    Manager toEntity(ManagerRequestDto dto);

    @Named("ManagerIgnoreClients")
    @Mapping(target = "clients", ignore = true)
    ManagerResponseDto toDtoManagerIgnoreClients(Manager manager);

    @IterableMapping(qualifiedByName = "Manager")
    List<ManagerResponseDto> toDtoList(List<Manager> managers);

    @Named("Manager")
    @Mapping(target = "clients", qualifiedByName = "ClientSetIgnoreManager")
    ManagerResponseDto toDto(Manager manager);
}
