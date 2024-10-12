package model;
import jakarta.persistence.*;

/**
 * Entity class representing a reaction in the system.
 */
@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long reaction_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public void setReactionId(Long id) {
    }

    public Long getReaction_id() {
        return reaction_id;
    }

    public void setReaction_id(Long reaction_id) {
        this.reaction_id = reaction_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}