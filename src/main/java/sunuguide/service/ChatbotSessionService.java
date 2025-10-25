package sunuguide.service;

import sunuguide.model.ChatbotSession;
import sunuguide.model.Message;
import sunuguide.repository.ChatbotSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ChatbotSessionService {

    // Renommé pour la cohérence : sessionRepository
    private final ChatbotSessionRepository sessionRepository;

    @Autowired
    public ChatbotSessionService(ChatbotSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Crée et sauvegarde une nouvelle session anonyme.
     * C'est la méthode à utiliser par l'AuthController lors de la première visite.
     */
    public ChatbotSession createAnonymousSession() {
        return sessionRepository.save(new ChatbotSession());
    }

    /**
     * Sauvegarde ou met à jour une session.
     * Utilisé principalement par l'AuthService pour attacher/fusionner un utilisateur.
     */
    public ChatbotSession save(ChatbotSession session) {
        return sessionRepository.save(session);
    }

    /**
     * Récupère une session par son ID.
     */
    public ChatbotSession getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
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
        return sessionRepository.save(session);
    }

    /**
     * Récupère l'historique des messages d'une session.
     */
    public List<Message> getMessageHistory(Long sessionId) {
        ChatbotSession session = getSessionById(sessionId);
        // L'annotation @OrderBy assure que l'historique est déjà trié
        return session.getMessageHistory();
    }
}