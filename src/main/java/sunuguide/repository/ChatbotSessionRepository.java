package sunuguide.repository; // Assurez-vous d'avoir un package 'repository'

import sunuguide.model.ChatbotSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marque l'interface comme un composant de repository Spring
public interface ChatbotSessionRepository extends JpaRepository<ChatbotSession, Long> {

    // Exemple de méthode personnalisée : trouver une session par l'ID de l'utilisateur
    Optional<ChatbotSession> findByUserId(Long userId);

}