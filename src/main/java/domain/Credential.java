package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Credential {
    private final String passwordHash;
    private final String salt;
    private final LocalDateTime lastChangedAt;

    public Credential(String passwordHash, String salt) {
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.salt = Objects.requireNonNull(salt);
        this.lastChangedAt = LocalDateTime.now();
    }

    public String getPasswordHash() { return passwordHash; }
    public String getSalt() { return salt; }
    public LocalDateTime getLastChangedAt() { return lastChangedAt; }
}
