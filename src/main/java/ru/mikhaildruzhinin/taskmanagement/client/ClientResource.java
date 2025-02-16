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
    public List<Client> getClients() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Client getClient(@PathParam("id") Long id) {
        return repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    public ResponseMessage addClient(Client client) {
        repository.persist(client);
        return new ResponseMessage("Ok");
    }

    @PUT
    @Path("/{id}")
    public ResponseMessage updateClient(@PathParam("id") Long id, Client client) {
        client.setId(id);
        boolean isUpdated = repository.update(client);
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
