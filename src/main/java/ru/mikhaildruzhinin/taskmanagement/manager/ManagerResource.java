package ru.mikhaildruzhinin.taskmanagement.manager;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.taskmanagement.ResponseMessage;

import java.util.List;
import java.util.Optional;

@Path("/managers")
@Tag(name = "Managers")
public class ManagerResource {

    @Inject
    ManagerRepository repository;

    @Inject
    ManagerMapper managerMapper;

    @GET
    public ManagersResponseDto getManagers() {
        List<ManagerResponseDto> managers =  repository.listAll()
                .stream()
                .map(manager -> managerMapper.toDto(manager))
                .toList();
        return new ManagersResponseDto(managers);
    }

    @GET
    @Path("/{id}")
    public ManagerResponseDto getManager(@PathParam("id") Long id) {
        // TODO use .map()
        Manager manager = repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
        return managerMapper.toDto(manager);
    }

    @POST
    @Transactional
    public ResponseMessage addManager(@Valid ManagerRequestDto dto) {
        Manager manager = managerMapper.toEntity(dto);
        repository.persist(manager);
        return new ResponseMessage("Ok");
    }

    @PUT
    @Path("/{id}")
    public ResponseMessage updateManager(@PathParam("id") Long id, @Valid ManagerRequestDto dto) {
        boolean isUpdated = repository.update(id, dto, Optional.empty()); // TODO add clients
        return new ResponseMessage(Boolean.toString(isUpdated));
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public ResponseMessage deleteManager(@PathParam("id") Long id) {
        boolean isDeleted = repository.deleteById(id);
        return new ResponseMessage(Boolean.toString(isDeleted));
    }
}
