package sunuguide.service;

import sunuguide.model.ChatbotSession;
import sunuguide.model.Message;
import sunuguide.repository.ChatbotSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service // Marque la classe comme un service Spring
@Transactional // Assure que toutes les méthodes sont exécutées dans une transaction
public class ChatbotSessionService {

    private final ChatbotSessionRepository chatbootRepository;

    @Autowired
    public ChatbotSessionService(ChatbotSessionRepository chatbootRepository) {
        this.chatbootRepository = chatbootRepository;
    }

    /**
     * Crée et sauvegarde une nouvelle session.
     */
    public ChatbotSession createSession(ChatbotSession session) {
        // Logique métier avant la sauvegarde (ex: validation)
        return chatbootRepository.save(session);
    }

    /**
     * Récupère une session par son ID.
     */
    public ChatbotSession getSessionById(Long sessionId) {
        return chatbootRepository.findById(sessionId)
                .orElseThrow(() -> new NoSuchElementException("Session non trouvée avec l'ID: " + sessionId));
    }

    /**
     * Ajoute un message à l'historique d'une session.
     * @param sessionId L'ID de la session cible.
     * @param content Le contenu du message à ajouter.
     * @return La session mise à jour.
     */
    public ChatbotSession addMessageToHistory(Long sessionId, String content) {
        ChatbotSession session = getSessionById(sessionId);

        // Crée le nouveau message
        Message newMessage = new Message(content);

        // La méthode utilitaire gère la relation bidirectionnelle
        session.addMessage(newMessage);

        // Le repository sauvegarde la session et, grâce à CascadeType.ALL, le nouveau message
        return chatbootRepository.save(session);
    }

    /**
     * Récupère l'historique des messages d'une session.
     */
    public List<Message> getMessageHistory(Long sessionId) {
        ChatbotSession session = getSessionById(sessionId);
        // L'annotation @OrderBy assure que l'historique est déjà trié
        return session.getMessageHistory();
    }

    // Ajoutez d'autres méthodes de logique métier ici (ex: terminer la session, supprimer, etc.)
}