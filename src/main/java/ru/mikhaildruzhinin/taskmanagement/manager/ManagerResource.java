package ru.mikhaildruzhinin.taskmanagement.manager;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Path("/managers")
@Tag(name = "Managers")
public class ManagerResource {

    private List<Manager> managers = new ArrayList<>();

    @GET
    public List<Manager> getManagers() {
        return managers;
    }

    @GET
    @Path("/{id}")
    public Manager getManager(@PathParam("id") int id) {
        return managers.stream()
                .filter(manager -> manager.id() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @POST
    public List<Manager> addManager(Manager manager) {
        managers = Stream.concat(managers.stream(), Stream.of(manager)).toList();
        return managers;
    }

    @PUT
    @Path("/{id}")
    public List<Manager> updateManager(@PathParam("id") int id) {
        // TODO
        return managers;
    }

    @DELETE
    @Path("/{id}")
    public List<Manager> deleteManager(@PathParam("id") int id) {
        managers = managers.stream()
                .dropWhile(manager -> manager.id() == id)
                .toList();
        return managers;
    }
}
