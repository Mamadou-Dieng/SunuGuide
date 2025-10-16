package sunuguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunuguide.model.ChatbotSession;

@Repository
public interface ChatbotSessionRepository extends JpaRepository<ChatbotSession, Long> {
}
