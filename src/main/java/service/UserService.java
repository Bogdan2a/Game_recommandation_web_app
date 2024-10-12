package service;

import model.User;
import java.util.List;

/**
 * The UserService interface defines operations related to user management.
 */
public interface UserService {

    /**
     * Registers a new user.
     * @param user The user to register.
     */
    void registerUser(User user);

    /**
     * Logs in a user and returns a JWT token.
     * @param user The user to log in.
     * @return The JWT token.
     */
    String loginUser(User user);

    /**
     * Creates a new user.
     * @param user The user to create.
     */
    void createUser(User user);

    /**
     * Updates an existing user.
     * @param userId The ID of the user to update.
     * @param user The updated user object.
     */
    void updateUser(Long userId, User user);

    /**
     * Deletes a user by ID.
     * @param userId The ID of the user to delete.
     */
    void deleteUser(Long userId);

    /**
     * Retrieves all users.
     * @return A list of all users.
     */
    List<User> getAllUsers();

    /**
     * Validates user credentials.
     * @param username The username.
     * @param password The password.
     * @return True if the credentials are valid, false otherwise.
     */
    boolean validateUserCredentials(String username, String password);

    /**
     * Retrieves the role of a user by username.
     * @param username The username.
     * @return The role of the user.
     */
    String getUserRole(String username);

    /**
     * Retrieves a user by ID.
     * @param userId The ID of the user to retrieve.
     * @return The user object.
     */
    User getUserById(Long userId);

    /**
     * Retrieves the ID of a user by username.
     * @param username The username.
     * @return The ID of the user.
     */
    Long getUserIdByUsername(String username);

}
