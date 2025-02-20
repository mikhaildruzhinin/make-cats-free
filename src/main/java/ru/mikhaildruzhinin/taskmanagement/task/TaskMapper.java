package ru.mikhaildruzhinin.taskmanagement.task;

import org.mapstruct.*;
import ru.mikhaildruzhinin.taskmanagement.MappingConfig;
import ru.mikhaildruzhinin.taskmanagement.client.ClientMapper;
import ru.mikhaildruzhinin.taskmanagement.manager.ManagerMapper;

import java.util.List;
import java.util.Set;

@Mapper(uses = {ClientMapper.class, ManagerMapper.class}, config = MappingConfig.class)
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

    @IterableMapping(qualifiedByName = "Task")
    List<TaskResponseDto> toDtoList(List<Task> tasks);

    @Named("Task")
    @Mapping(target = "client", qualifiedByName = "ClientIgnoreTasks")
    TaskResponseDto toDto(Task task);
}
