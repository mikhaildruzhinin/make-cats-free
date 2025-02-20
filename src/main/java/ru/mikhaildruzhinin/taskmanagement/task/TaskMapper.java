package ru.mikhaildruzhinin.taskmanagement.task;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.mikhaildruzhinin.taskmanagement.client.ClientMapper;

import java.util.Set;

@Mapper(uses = ClientMapper.class, componentModel = "cdi")
public interface TaskMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "client", expression = "java(null)")
    Task toEntity(TaskRequestDto dto);

    @Named("TaskSetIgnoreClient")
    @IterableMapping(qualifiedByName = "TaskIgnoreClient")
    Set<TaskResponseDto> toDtoTaskSetIgnoreClient(Set<Task> tasks);

    @Named("TaskIgnoreClient")
    @Mapping(target = "client", ignore = true)
    TaskResponseDto toDtoTaskIgnoreClient(Task task);

    @Named("Task")
    @Mapping(target = "client", qualifiedByName = "ClientIgnoreTasks")
    TaskResponseDto toDto(Task task);
}
