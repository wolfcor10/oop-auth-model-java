package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class SimplePasswordHasher implements PasswordHasher {
    @Override
    public String hash(String plain, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest((salt + plain).getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Hash error", e);
        }
    }

    @Override
    public boolean verify(String plain, String salt, String expectedHash) {
        return hash(plain, salt).equals(expectedHash);
    }
}
