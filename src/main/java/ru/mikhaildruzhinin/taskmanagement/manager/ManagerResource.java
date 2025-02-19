package ru.mikhaildruzhinin.taskmanagement.manager;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/managers")
@Tag(name = "Managers")
public class ManagerResource {

    @Inject
    ManagerRepository repository;

    @Inject
    ManagerMapper mapper;

    @GET
    public Response getManagers() {
        List<ManagerResponseDto> managers =  repository.listAll()
                .stream()
                .map(manager -> mapper.toDto(manager))
                .toList();
        ManagersResponseDto dto = new ManagersResponseDto(managers);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response getManager(@PathParam("id") Long id) {
        // TODO use .map()
        Optional<ManagerResponseDto> optionalDto = repository.findByIdOptional(id).map(manager -> mapper.toDto(manager));
        return optionalDto.map(dto -> Response.ok(dto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
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
    public Response updateManager(@PathParam("id") Long id, @Valid ManagerRequestDto dto) {
        boolean isUpdated = repository.update(id, dto, Optional.empty()); // TODO add clients
        if (isUpdated) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteManager(@PathParam("id") Long id) {
        boolean isDeleted = repository.deleteById(id);
        if (isDeleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
