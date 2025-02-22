package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("api/managers")
@Tag(name = "Managers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManagerResource {

    @Inject
    ManagerRepository repository;

    @Inject
    ManagerMapper mapper;

    @GET
    public Response getManagers() {
        List<ManagerResponseDto> managers = mapper.toDtoList(repository.listAll());
        ManagersResponseDto dto = new ManagersResponseDto(managers);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response getManager(@PathParam("id") Long id) {
        Optional<ManagerResponseDto> optionalDto = repository.findByIdOptional(id)
                .map(manager -> mapper.toDto(manager));
        ResponseBuilder rb = optionalDto.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND));
        return rb.build();
    }

    @POST
    @Transactional
    public Response addManager(@Valid ManagerRequestDto dto) {
        Manager manager = mapper.toEntity(dto);
        repository.persist(manager);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateManager(@PathParam("id") Long id, @Valid ManagerRequestDto dto) {
        Optional<Manager> optionalManager = repository.findByIdOptional(id);
        Optional<Boolean> isUpdated = optionalManager.map(manager -> {
            manager.setName(dto.name());
            return true;
        });
        ResponseBuilder rb = isUpdated.orElse(false) ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteManager(@PathParam("id") Long id) {
        boolean isDeleted = repository.deleteById(id);
        ResponseBuilder rb = isDeleted ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }
}
