package domain;

import java.util.*;

public class Role {
    private final UUID id = UUID.randomUUID();
    private final String name;
    private final Set<Permission> permissions = new HashSet<>();

    public Role(String name) { this.name = Objects.requireNonNull(name); }

    public String getName() { return name; }
    public Set<Permission> getPermissions() { return Collections.unmodifiableSet(permissions); }

    public void addPermission(Permission p) { permissions.add(p); }
}
