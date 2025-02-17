package ru.mikhaildruzhinin.taskmanagement.manager;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.taskmanagement.ResponseMessage;

import java.util.List;

@Path("/managers")
@Tag(name = "Managers")
public class ManagerResource {

    @Inject
    ManagerRepository repository;

    @GET
    public ManagersDto getManagers() {
        List<ManagerDto> managers =  repository.listAll()
                .stream()
                .map(Manager::toDto)
                .toList();
        return new ManagersDto(managers);
    }

    @GET
    @Path("/{id}")
    public ManagerDto getManager(@PathParam("id") Long id) {
        return repository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new)
                .toDto();
    }

    @POST
    @Transactional
    public ResponseMessage addManager(@Valid ManagerDto managerDto) {
        System.out.println(managerDto.name());
        repository.persist(managerDto.toEntity());
        return new ResponseMessage("Ok");
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public ResponseMessage updateManager(@PathParam("id") Long id, @Valid ManagerDto managerDto) {
        boolean isUpdated = repository.update(id, managerDto);
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
