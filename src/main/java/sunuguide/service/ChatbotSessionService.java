package sunuguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import sunuguide.model.ChatbotSession;
import sunuguide.model.Message;
import sunuguide.repository.ChatbotSessionRepository;
import sunuguide.repository.MessageRepository;

import java.net.ConnectException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ChatbotSessionService {

    private final ChatbotSessionRepository sessionRepository;
    private final MessageRepository messageRepository;
    private final WebClient webClient;

    // URL de ton modèle IA local (Flask, FastAPI, etc.)
    private static final String MODEL_API_URL = "http://localhost:5000/chat";

    @Autowired
    public ChatbotSessionService(ChatbotSessionRepository sessionRepository,
                                 MessageRepository messageRepository) {
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository;
        this.webClient = WebClient.create();
    }

    public ChatbotSession createAnonymousSession() {
        return sessionRepository.save(new ChatbotSession());
    }

    public ChatbotSession getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NoSuchElementException("Session non trouvée : " + sessionId));
    }

    /**
     * Envoie un message utilisateur au modèle externe, reçoit la réponse IA,
     * et met à jour l'historique dans la base.
     */
    public List<Message> sendMessageAndGetResponse(Long sessionId, String content) {
        ChatbotSession session = getSessionById(sessionId);

        // 1️⃣ Sauvegarde du message utilisateur
        Message userMessage = new Message(session, content, "USER");
        session.addMessage(userMessage);
        messageRepository.save(userMessage);

        // 2️⃣ Appel du modèle IA externe
        ChatResponse response;
        try {
            response = webClient.post()
                    .uri(MODEL_API_URL)
                    .bodyValue(new ChatRequest(content))
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block(); // synchrone
        } catch (WebClientRequestException e) {
            if (e.getRootCause() instanceof ConnectException) {
                System.err.println("ERREUR: Le modèle chatbot (port 5000) est hors ligne.");
                Message errorMessage = new Message(session, "⚠️ Le modèle IA n'est pas disponible pour le moment.", "BOT");
                messageRepository.save(errorMessage);
                return session.getMessageHistory();
            }
            throw e;
        }

        // 3️⃣ Sauvegarde de la réponse du modèle
        String botReply = (response != null && response.getResponse() != null)
                ? response.getResponse()
                : "Je n’ai pas pu générer de réponse.";
        Message botMessage = new Message(session, botReply, "BOT");
        session.addMessage(botMessage);
        messageRepository.save(botMessage);

        // 4️⃣ Retourne tout l’historique
        return session.getMessageHistory();
    }

    public List<Message> getMessageHistory(Long sessionId) {
        ChatbotSession session = getSessionById(sessionId);
        return session.getMessageHistory();
    }

    // --- Objets pour la communication avec l'API du modèle ---
    private record ChatRequest(String prompt) {}
    private static class ChatResponse {
        private String response;
        public String getResponse() { return response; }
        public void setResponse(String response) { this.response = response; }
    }
}
