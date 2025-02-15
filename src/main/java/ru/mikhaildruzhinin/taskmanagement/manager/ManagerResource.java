package ru.mikhaildruzhinin.taskmanagement.manager;

import jakarta.inject.Inject;
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
    public List<Manager> getManagers() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Manager getManager(@PathParam("id") Long id) {
        return repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    @POST
    public ResponseMessage addManager(Manager manager) {
        repository.persist(manager);
        return new ResponseMessage("Ok");
    }

    @PUT
    @Path("/{id}")
    public ResponseMessage updateManager(@PathParam("id") Long id, Manager manager) {
        manager.setId(id);
        boolean isUpdated = repository.update(manager);
        return new ResponseMessage(Boolean.toString(isUpdated));
    }

    @DELETE
    @Path("/{id}")
    public ResponseMessage deleteManager(@PathParam("id") Long id) {
        boolean isDeleted = repository.deleteById(id);
        return new ResponseMessage(Boolean.toString(isDeleted));
    }
}
