package model;
import jakarta.persistence.*;

/**
 * Entity class representing a resonance in the system.
 */
@Entity
@Table(name = "resonances")
public class Resonance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resonance_id")
    private Long resonance_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public void setResonanceId(Long id) {
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

    public Long getResonance_id() {
        return resonance_id;
    }

    public void setResonance_id(Long resonance_id) {
        this.resonance_id = resonance_id;
    }
}
