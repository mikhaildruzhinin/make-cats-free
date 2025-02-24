package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {
}
