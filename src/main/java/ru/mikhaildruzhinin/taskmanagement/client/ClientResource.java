package ru.mikhaildruzhinin.taskmanagement.client;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.taskmanagement.ResponseMessage;

import java.util.List;

@Path("/clients")
@Tag(name = "Clients")
public class ClientResource {

    @Inject
    ClientRepository repository;

    @GET
    public ClientsResponseDto getClients() {
        List<ClientResponseDto> clients = repository.listAll()
                .stream()
                .map(Client::toDto)
                .toList();
        return new ClientsResponseDto(clients);
    }

    @GET
    @Path("/{id}")
    public ClientResponseDto getClient(@PathParam("id") Long id) {
        return repository.findByIdOptional(id)
                .map(Client::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    public ResponseMessage addClient(ClientRequestDto dto) {
        // TODO get manager
        repository.persist(dto.toEntity());
        return new ResponseMessage("Ok");
    }

    @PUT
    @Path("/{id}")
    public ResponseMessage updateClient(@PathParam("id") Long id, ClientRequestDto dto) {
        // TODO get manager
        boolean isUpdated = repository.update(id, dto);
        return new ResponseMessage(Boolean.toString(isUpdated));
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public ResponseMessage deleteClient(@PathParam("id") Long id) {
        boolean isDeleted = repository.deleteById(id);
        return new ResponseMessage(Boolean.toString(isDeleted));
    }
}
