package ru.mikhaildruzhinin.mcf.taskmanagement.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(schema = "mcf", name = "users")
@UserDefinition
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Username
    private String username;

    @Password
    private String password;

    @Roles
    private String role;

    @Column(insertable = false)
    private Instant createdAt;

    protected User() {
    }

    public User(String username, String role) {
        this.username = username;
        this.password = UserUtils.createPassword();
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
