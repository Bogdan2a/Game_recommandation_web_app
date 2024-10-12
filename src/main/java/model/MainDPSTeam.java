package model;
import jakarta.persistence.*;

/**
 * Represents a recommended team composition with a main DPS character and three sub characters.
 */
@Entity
@Table(name = "recommended_teams")
public class MainDPSTeam {
    @Id
    @Column(name = "maindpscharacter_id")
    private Long maindpscharacter_id;

    @Column(name = "subcharacter1")
    private Long subcharacter1;

    @Column(name = "subcharacter2")
    private Long subcharacter2;

    @Column(name = "subcharacter3")
    private Long subcharacter3;

    /**
     * Sets the ID of the main DPS character.
     * @param id The ID of the main DPS character.
     */
    public void setMainDPSCharacterId(Long id) {
    }

    // Getters and setters for sub characters...
}

