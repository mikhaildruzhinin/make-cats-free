package ru.mikhaildruzhinin.taskmanagement.client;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.mikhaildruzhinin.taskmanagement.ResponseMessage;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.taskmanagement.manager.ManagerRepository;

import java.util.List;
import java.util.Optional;

@Path("/clients")
@Tag(name = "Clients")
public class ClientResource {

    @Inject
    ClientRepository clientRepository;

    @Inject
    ManagerRepository managerRepository;

    @Inject
    ClientMapper clientMapper;

    @GET
    public ClientsResponseDto getClients() {
        List<ClientResponseDto> clients = clientMapper.toDtoList(clientRepository.listAll());
        return new ClientsResponseDto(clients);
    }

    @GET
    @Path("/{id}")
    public ClientResponseDto getClient(@PathParam("id") Long id) {
        return clientRepository.findByIdOptional(id)
                .map(client -> clientMapper.toDto(client))
                .orElseThrow(NotFoundException::new);
    }

    @POST
    public ResponseMessage addClient(ClientRequestDto dto) {
        Client client = clientMapper.toEntity(dto);
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        clientRepository.save(client, optionalManager);
        return new ResponseMessage("Ok");
    }

    @PUT
    @Path("/{id}")
    public ResponseMessage updateClient(@PathParam("id") Long id, ClientRequestDto dto) {
        Optional<Manager> optionalManager = managerRepository.findByIdOptional(dto.managerId());
        boolean isUpdated = clientRepository.update(id, dto);
        return new ResponseMessage(Boolean.toString(isUpdated));
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public ResponseMessage deleteClient(@PathParam("id") Long id) {
        boolean isDeleted = clientRepository.deleteById(id);
        return new ResponseMessage(Boolean.toString(isDeleted));
    }
}
