package ru.mikhaildruzhinin.taskmanagement.client;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
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
        ClientsResponseDto dto = new ClientsResponseDto(clients);
        return Response.ok(dto).build();
    }

    @GET
    @Path("/{id}")
    public Response getClient(@PathParam("id") Long id) {
        Optional<ClientResponseDto> optionalDto = clientRepository.findByIdOptional(id)
                .map(client -> mapper.toDto(client));
        ResponseBuilder rb = optionalDto.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND));
        return rb.build();
    }

    @POST
    public Response addClient(ClientRequestDto dto) {
        Client client = mapper.toEntity(dto);
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        Optional<Boolean> isSaved = optionalManager.map(manager -> {
            client.setManager(manager);
            clientRepository.persist(client);
            return true;
        });
        ResponseBuilder rb = isSaved.orElse(false) ? Response.noContent() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }

    @PUT
    @Path("/{id}")
    public Response updateClient(@PathParam("id") Long id, ClientRequestDto dto) {
        Optional<Client> optionalClient = clientRepository.findByIdOptional(id);
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        Optional<Boolean> isUpdated = optionalClient.flatMap(client -> optionalManager.map(manager -> {
            client.setName(dto.name());
            client.setManager(manager);
            // TODO tasks
            return true;
        }));
        ResponseBuilder rb = isUpdated.orElse(false) ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteClient(@PathParam("id") Long id) {
        boolean isDeleted = clientRepository.deleteById(id);
        ResponseBuilder rb = isDeleted ? Response.ok() : Response.status(Response.Status.NOT_FOUND);
        return rb.build();
    }
}
