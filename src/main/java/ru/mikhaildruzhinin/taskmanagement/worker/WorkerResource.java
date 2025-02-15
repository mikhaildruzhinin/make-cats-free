package ru.mikhaildruzhinin.taskmanagement.worker;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@Path("/workers")
@Tag(name = "Worker")
public class WorkerResource {

    private List<Worker> workers = new ArrayList<>();

    @GET
    public List<Worker> getWorkers() {
        return workers;
    }

    @GET
    @Path("/{id}")
    public Worker getWorker(@PathParam("id") int id) {
        return workers.stream()
                .filter(worker -> worker.id() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @POST
    public List<Worker> addWorker(Worker worker) {
        workers.add(worker);
        return workers;
    }

    @PUT
    @Path("/{id}")
    public List<Worker> updateWorker(@PathParam("id") int id) {
        // TODO
        return workers;
    }

    @DELETE
    @Path("/{id}")
    public List<Worker> deleteWorker(@PathParam("id") int id) {
        workers = workers.stream()
                .dropWhile(worker -> worker.id() == id)
                .toList();
        return workers;
    }
}
