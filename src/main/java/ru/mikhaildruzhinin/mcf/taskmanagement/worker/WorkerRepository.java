package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkerRepository implements PanacheRepository<Worker> {
}
