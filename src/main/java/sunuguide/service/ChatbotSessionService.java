package sunuguide.service;

import sunuguide.model.ChatbotSession;
import sunuguide.model.Message;
import sunuguide.repository.ChatbotSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import sunuguide.repository.MessageRepository; // NOUVEAU

@Service
@Transactional
public class ChatbotSessionService {

    // Renommé pour la cohérence : sessionRepository
    private final ChatbotSessionRepository sessionRepository;
    private final MessageRepository messageRepository; // LIGNE À AJOUTER : Déclaration de la variable

    @Autowired
    // MODIFIER la signature pour inclure MessageRepository
    public ChatbotSessionService(ChatbotSessionRepository sessionRepository, MessageRepository messageRepository) {
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository; // CORRECTION : 'messageRepository' en minuscule pour correspondre à la variable déclarée
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

    // SUPPRIMER ICI L'ANCIENNE MÉTHODE addMessageToHistory

    /**
     * Gère l'intégralité d'un tour de conversation (message utilisateur + réponse bot simulée).
     * @param sessionId L'ID de la session cible.
     * @param content Le contenu du message utilisateur.
     * @return L'historique complet mis à jour.
     */
    public List<Message> sendMessageAndGetResponse(Long sessionId, String content) {
        ChatbotSession session = getSessionById(sessionId);

        // 1. Enregistrement du message de l'utilisateur (Sender: "USER")
        Message userMessage = new Message(session, content, "USER");
        session.addMessage(userMessage);
        messageRepository.save(userMessage); // Utilisation correcte de messageRepository

        // 2. LOGIQUE DE RÉPONSE DU BOT (Simulée)
        String botResponseContent = "SunuGuide vous répond : Nous sommes là pour vous aider avec vos itinéraires au Sénégal ! Vous avez demandé : '" + content + "'. Par où souhaitez-vous commencer votre voyage ?";

        // 3. Enregistrement du message du Bot (Sender: "BOT")
        Message botMessage = new Message(session, botResponseContent, "BOT");
        session.addMessage(botMessage);
        messageRepository.save(botMessage); // Utilisation correcte de messageRepository

        // 4. Retourne l'historique complet
        return session.getMessageHistory();
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
