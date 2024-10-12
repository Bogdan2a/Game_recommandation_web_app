package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The UserServiceImpl class implements the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public String loginUser(User user) {
        return "JWT_TOKEN";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean validateUserCredentials(String username, String password) {

        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public String getUserRole(String username) {

        String userRole = userRepository.getUserRoleByUsername(username);
        if (userRole != null && !userRole.isEmpty()) {
            return userRole;
        } else {
            return "DEFAULT_ROLE";
        }
    }

    /**
     * Creates a new user.
     * @param user The user to create.
     */
    public void createUser(User user) {
        userRepository.save(user);
    }

    /**
     * Updates an existing user.
     * @param userId The ID of the user to update.
     * @param user The updated user object.
     */
    public void updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getUsername() != null && !Objects.equals(user.getUsername(), "")) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !Objects.equals(user.getPassword(), "")){
            existingUser.setPassword(user.getPassword());
        }
        if (user.getUser_type() != null && !Objects.equals(user.getUser_type(), "")){
            existingUser.setUser_type(user.getUser_type());
        }

        userRepository.save(existingUser);
    }

    /**
     * Retrieves the ID of a user by username.
     * @param username The username.
     * @return The ID of the user.
     */
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getUser_id();
    }

    /**
     * Deletes a user by ID.
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves a user by ID.
     * @param userId The ID of the user to retrieve.
     * @return The user object.
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
