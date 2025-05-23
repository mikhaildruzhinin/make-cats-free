package ru.mikhaildruzhinin.mcf.taskmanagement.user;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Uni<User> findByRole(String role) {
        return find("select u from User u where role = ?1", role).firstResult();
    }
}
