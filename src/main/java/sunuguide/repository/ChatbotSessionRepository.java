package sunuguide.repository; // Assurez-vous d'avoir un package 'repository'

import sunuguide.model.ChatbotSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marque l'interface comme un composant de repository Spring
public interface ChatbotSessionRepository extends JpaRepository<ChatbotSession, Long> {

    /**
     * Recherche une session par l'ID de l'utilisateur qui lui est lié.
     * La convention de nommage est : findBy[NomRelation][NomPropriété]
     * Où [NomRelation] est 'user' et [NomPropriété] est 'Id' (référant à userId).
     *
     * Étant donné la relation One-to-One, le retour est Optional<ChatbotSession>.
     */
    Optional<ChatbotSession> findByUserId(Long userId);

}