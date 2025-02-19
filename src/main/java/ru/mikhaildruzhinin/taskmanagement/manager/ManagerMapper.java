package ru.mikhaildruzhinin.taskmanagement.manager;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.mikhaildruzhinin.taskmanagement.client.ClientMapper;

@Mapper(uses = ClientMapper.class, componentModel = "cdi")
public interface ManagerMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "clients", expression = "java(Set.of())")
    Manager toEntity(ManagerRequestDto dto);

    @Named("ManagerIgnoreClients")
    @Mapping(target = "clients", ignore = true)
    ManagerResponseDto toDtoManagerIgnoreClients(Manager manager);

    @Mapping(target = "clients", qualifiedByName = "ClientSetIgnoreManager")
    ManagerResponseDto toDto(Manager manager);
}
