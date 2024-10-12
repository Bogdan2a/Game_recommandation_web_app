package repository;

import model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing Reaction entities.
 */
@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    /**
     * Retrieves a reaction by its name.
     * @param name The name of the reaction.
     * @return The Reaction entity with the specified name, if found; otherwise, null.
     */
    Reaction findByName(String name);
}