package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The UserRepository interface provides methods to perform CRUD operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     * @param username The username to search for.
     * @return The user with the specified username, or null if not found.
     */
    User findByUsername(String username);

    /**
     * Retrieves the role of a user by username using a JPQL query.
     * @param username The username of the user.
     * @return The role of the user.
     */
    @Query("SELECT u.user_type FROM User u WHERE u.username = :username")
    String getUserRoleByUsername(String username);

}

