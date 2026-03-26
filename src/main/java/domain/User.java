package domain;

import java.time.LocalDateTime;
import java.util.*;

public class User {
    private final UUID id;
    private final String username;
    private final String email;
    private boolean active;
    private final LocalDateTime createdAt;

    // Composition: User "owns" Credential
    private final Credential credential;

    // Roles (many-to-many) simplified as a Set
    private final Set<Role> roles = new HashSet<>();

    public User(String username, String email, Credential credential) {
        this.id = UUID.randomUUID();
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.credential = Objects.requireNonNull(credential);
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }
    public Credential getCredential() { return credential; }
    public Set<Role> getRoles() { return Collections.unmodifiableSet(roles); }

    public void addRole(Role role) { roles.add(role); }
    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }
}
