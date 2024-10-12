package org.example.assignment1;

import model.User;
import model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UserRepository;
import service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize all mocks
    }

    @Test
    public void testRegisterUser() {
        // Given
        User user = new User();
        user.setUser_id(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setUser_type(UserType.Normal);

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        userService.registerUser(user);

        // Then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoginUser() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        // When
        String token = userService.loginUser(user);

        // Then
        assertEquals("JWT_TOKEN", token);
    }

    @Test
    public void testGetAllUsers() {
        // Given
        User user1 = new User();
        user1.setUser_id(1L);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setUser_type(UserType.Normal);

        User user2 = new User();
        user2.setUser_id(2L);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setUser_type(UserType.Admin);

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> retrievedUsers = userService.getAllUsers();

        // Then
        assertEquals(2, retrievedUsers.size());
        assertTrue(retrievedUsers.contains(user1));
        assertTrue(retrievedUsers.contains(user2));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testValidateUserCredentials() {
        // Given
        String username = "testuser";
        String password = "password";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUser_type(UserType.Normal);

        when(userRepository.findByUsername(username)).thenReturn(user);

        // When
        boolean isValid = userService.validateUserCredentials(username, password);

        // Then
        assertTrue(isValid);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUserRole() {
        // Given
        String username = "testuser";
        String userRole = "PLAYER";

        when(userRepository.getUserRoleByUsername(username)).thenReturn(userRole);

        // When
        String retrievedUserRole = userService.getUserRole(username);

        // Then
        assertEquals(userRole, retrievedUserRole);
        verify(userRepository, times(1)).getUserRoleByUsername(username);
    }

    @Test
    public void testCreateUser() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setUser_type(UserType.Normal);

        // When
        userService.createUser(user);

        // Then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setUser_id(userId);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setUser_type(UserType.Normal);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        updatedUser.setPassword("updatedpassword");
        updatedUser.setUser_type(UserType.Admin);

        // When
        userService.updateUser(userId, updatedUser);

        // Then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserIdByUsername() {
        // Given
        String username = "testuser";
        Long userId = 1L;

        User user = new User();
        user.setUser_id(userId);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        // When
        Long retrievedUserId = userService.getUserIdByUsername(username);

        // Then
        assertEquals(userId, retrievedUserId);
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testGetUserById_UserExists() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setUser_id(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User retrievedUser = userService.getUserById(userId);

        // Then
        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getUser_id());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserNotExists() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}
