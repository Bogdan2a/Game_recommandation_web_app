
package repository;

import model.SavedTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing SavedTeam entities.
 */
@Repository
public interface SavedTeamRepository extends JpaRepository<SavedTeam, Long> {

    /**
     * Retrieves a list of saved teams belonging to a specific user.
     * @param userId The ID of the user.
     * @return A list of SavedTeam entities associated with the specified user ID.
     */
    @Query("SELECT st FROM SavedTeam st WHERE st.user_id = :userId")
    List<SavedTeam> findAllByUser_id(Long userId);
}