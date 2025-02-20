package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {
}
