package services;

public interface PasswordHasher {
    String hash(String plain, String salt);
    boolean verify(String plain, String salt, String expectedHash);
}
