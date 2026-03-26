package services;

import domain.User;

public class AuthorizationService {
    public boolean hasPermission(User user, String permissionCode) {
        return user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .anyMatch(p -> p.getCode().equals(permissionCode));
    }
}
