package utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtils {

    static String salt = Arrays.toString(new byte[16]);

    /**
     * Generate a secret key from given password
     * HMACSHA1 produce a hash length of 160 bits
     * AES algorithm generates a secret key of 256 bits
     * @param password
     * @return SecretKey originalKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getKeyPassword(String password) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 10000, 256);
            return new SecretKeySpec(secretKeyFactory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converting secret key to a string
     * @param secretKey
     * @return the encoded secret key as a string
     */
    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] data = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(data);
    }
}
