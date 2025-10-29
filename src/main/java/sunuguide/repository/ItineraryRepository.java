package sunuguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunuguide.model.Itinerary;

/**
 * Repository pour l’entité Itinerary.
 * Fournit automatiquement les opérations CRUD grâce à JpaRepository.
 */
@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
}
