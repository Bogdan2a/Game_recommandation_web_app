package repository;

import model.MainDPSTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing MainDPSTeam entities.
 */
@Repository
public interface MainDPSTeamRepository extends JpaRepository<MainDPSTeam, Long> {

    /**
     * Retrieves the ID of the subcharacter 1 associated with a main DPS character.
     * @param mainDPSId The ID of the main DPS character.
     * @return The ID of the subcharacter 1 associated with the specified main DPS character.
     */
    @Query("SELECT m.subcharacter1 FROM MainDPSTeam m WHERE m.maindpscharacter_id = :mainDPSId")
    Long findSubCharacter1IdByMainDPSId(Long mainDPSId);

    /**
     * Retrieves the ID of the subcharacter 2 associated with a main DPS character.
     * @param mainDPSId The ID of the main DPS character.
     * @return The ID of the subcharacter 2 associated with the specified main DPS character.
     */
    @Query("SELECT m.subcharacter2 FROM MainDPSTeam m WHERE m.maindpscharacter_id = :mainDPSId")
    Long findSubCharacter2IdByMainDPSId(Long mainDPSId);

    /**
     * Retrieves the ID of the subcharacter 3 associated with a main DPS character.
     * @param mainDPSId The ID of the main DPS character.
     * @return The ID of the subcharacter 3 associated with the specified main DPS character.
     */
    @Query("SELECT m.subcharacter3 FROM MainDPSTeam m WHERE m.maindpscharacter_id = :mainDPSId")
    Long findSubCharacter3IdByMainDPSId(Long mainDPSId);
}