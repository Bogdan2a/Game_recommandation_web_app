package repository;

import model.Resonance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing Resonance entities.
 */
@Repository
public interface ResonanceRepository extends JpaRepository<Resonance, Long> {

    /**
     * Retrieves a resonance by its name.
     * @param name The name of the resonance.
     * @return The Resonance entity with the specified name, if found; otherwise, null.
     */
    Resonance findByName(String name);
}