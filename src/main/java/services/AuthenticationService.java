package services;

import domain.User;
import repositories.UserRepository;

import java.util.Optional;

public class AuthenticationService {
    private final UserRepository users;
    private final PasswordHasher hasher;

    public AuthenticationService(UserRepository users, PasswordHasher hasher) {
        this.users = users;
        this.hasher = hasher;
    }

    public Optional<User> login(String usernameOrEmail, String password) {
        Optional<User> userOpt = users.findByUsernameOrEmail(usernameOrEmail);
        if (userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();
        if (!user.isActive()) return Optional.empty();

        boolean ok = hasher.verify(password, user.getCredential().getSalt(), user.getCredential().getPasswordHash());
        return ok ? Optional.of(user) : Optional.empty();
    }
}
