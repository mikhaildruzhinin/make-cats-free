package ru.mikhaildruzhinin.taskmanagement.client;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.taskmanagement.manager.ManagerRepository;

import java.util.List;
import java.util.Optional;

@Path("/clients")
@Tag(name = "Clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientRepository clientRepository;

    @Inject
    ManagerRepository managerRepository;

    @Inject
    ClientMapper mapper;

    @GET
    public Response getClients() {
        List<ClientResponseDto> clients = mapper.toDtoList(clientRepository.listAll());
        Object dto = new ClientsResponseDto(clients);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response getClient(@PathParam("id") Long id) {
        Optional<ClientResponseDto> optionalDto = clientRepository.findByIdOptional(id)
                .map(client -> mapper.toDto(client));
        return optionalDto.map(dto -> Response.ok(dto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response addClient(ClientRequestDto dto) {
        Client client = mapper.toEntity(dto);
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        return optionalManager.map(manager -> {
            clientRepository.save(client, manager);
            return Response.noContent().build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    public Response updateClient(@PathParam("id") Long id, ClientRequestDto dto) {
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        Optional<Boolean> isUpdated = optionalManager.map(manager -> clientRepository.update(id, dto, manager));
        if (isUpdated.orElse(false)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteClient(@PathParam("id") Long id) {
        boolean isDeleted = clientRepository.deleteById(id);
        if (isDeleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
