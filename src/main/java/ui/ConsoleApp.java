package ui;

import domain.*;
import repositories.*;
import services.*;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        // Wiring (manual DI)
        UserRepository userRepo = new InMemoryUserRepository();
        PasswordHasher hasher = new SimplePasswordHasher();
        AuthenticationService authService = new AuthenticationService(userRepo, hasher);
        AuthorizationService authorizationService = new AuthorizationService();

        seedData(userRepo, hasher);

        Scanner sc = new Scanner(System.in);
        User sessionUser = null;

        while (true) {
            System.out.println("\n=== Identity Console ===");
            System.out.println("1) Login");
            System.out.println("2) Who am I?");
            System.out.println("3) Check permission");
            System.out.println("4) Logout");
            System.out.println("0) Exit");
            System.out.print("Select: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Username or email: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();

                    Optional<User> logged = authService.login(u, p);
                    if (logged.isPresent()) {
                        sessionUser = logged.get();
                        System.out.println("Login OK. Welcome, " + sessionUser.getUsername());
                    } else {
                        System.out.println("Login failed.");
                    }
                }
                case "2" -> {
                    if (sessionUser == null) System.out.println("Not logged in.");
                    else System.out.println("Logged as: " + sessionUser.getUsername() + " (" + sessionUser.getEmail() + ")");
                }
                case "3" -> {
                    if (sessionUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.print("Permission code (e.g., CATALOG_UPDATE): ");
                    String code = sc.nextLine();
                    boolean ok = authorizationService.hasPermission(sessionUser, code);
                    System.out.println(ok ? "Permission GRANTED" : "Permission DENIED");
                }
                case "4" -> {
                    sessionUser = null;
                    System.out.println("Logged out.");
                }
                case "0" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void seedData(UserRepository repo, PasswordHasher hasher) {
        Permission pUpdateCatalog = new Permission("CATALOG_UPDATE");
        Role adminRole = new Role("ADMIN");
        adminRole.addPermission(pUpdateCatalog);

        String salt = "SALT123"; // demo only
        String hash = hasher.hash("admin123", salt);
        Credential cred = new Credential(hash, salt);

        User admin = new User("admin", "admin@mail.com", cred);
        admin.addRole(adminRole);

        repo.save(admin);
    }
}
