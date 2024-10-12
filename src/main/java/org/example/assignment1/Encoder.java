package org.example.assignment1;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Encoder class provides methods for encoding and decoding passwords.
 * It follows the Singleton design pattern.
 */
public class Encoder {

    // Private constructor to prevent instantiation from outside
    private Encoder() {}

    // Singleton instance
    private static final Encoder INSTANCE = new Encoder();

    /**
     * Returns the singleton instance of Encoder.
     * @return The singleton instance of Encoder.
     */
    public static Encoder getInstance() {
        return INSTANCE;
    }

    /**
     * Encodes a password by shifting each character's ASCII value by 1.
     * @param password The password to encode.
     * @return The encoded password.
     */
    public static String encodingPassword(String password) {
        StringBuilder encodedPassword = new StringBuilder();
        for (char c : password.toCharArray()) {
            encodedPassword.append((char) (c + 1));
        }
        return encodedPassword.toString();
    }

    /**
     * Decodes a password that was encoded using the encodingPassword method.
     * @param password The password to decode.
     * @return The decoded password.
     */
    public static String decodingPassword(String password) {
        StringBuilder decodedPassword = new StringBuilder();
        for (char c : password.toCharArray()) {
            decodedPassword.append((char) (c - 1));
        }
        return decodedPassword.toString();
    }
}