package sunuguide.controller;

import sunuguide.model.ChatbotSession;
import sunuguide.model.Message;
import sunuguide.service.ChatbotSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController // Gère les requêtes REST
@RequestMapping("/api/chatbot") // Base de l'URL pour les interactions du Chatbot
public class ChatbotController {

    private final ChatbotSessionService sessionService;

    @Autowired
    public ChatbotController(ChatbotSessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * GET /api/chatbot/{sessionId} : Récupère les détails de la session (principalement pour débogage ou vérification).
     * Anciennement : GET /api/sessions/{sessionId}
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<ChatbotSession> getSessionDetails(@PathVariable Long sessionId) {
        try {
            ChatbotSession session = sessionService.getSessionById(sessionId);
            return ResponseEntity.ok(session);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST /api/chatbot/{sessionId}/message : Ajoute un nouveau message et reçoit potentiellement la réponse du bot.
     * C'est l'endpoint clé pour la conversation.
     * Anciennement : POST /api/sessions/{sessionId}/message
     */
    @PostMapping("/{sessionId}/message")
    public ResponseEntity<List<Message>> sendMessageAndGetHistory(@PathVariable Long sessionId,
                                                                  @RequestBody Map<String, String> payload) {
        String content = payload.get("content");

        if (content == null || content.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            // 1. Sauvegarde le message de l'utilisateur
            sessionService.addMessageToHistory(sessionId, content);

            // 2. LOGIQUE DU CHATBOT (SIMULÉE) : Ici, vous appelleriez le service IA pour générer une réponse
            // Cette réponse serait ensuite ajoutée à l'historique par une autre méthode de service.

            // 3. Retourne l'historique complet après l'interaction (pour mettre à jour l'interface React)
            ChatbotSession updatedSession = sessionService.getSessionById(sessionId);
            return ResponseEntity.ok(updatedSession.getMessageHistory());

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/chatbot/{sessionId}/history : Récupère l'historique complet des messages.
     * Anciennement : GET /api/sessions/{sessionId}/history
     */
    @GetMapping("/{sessionId}/history")
    public ResponseEntity<List<Message>> getSessionHistory(@PathVariable Long sessionId) {
        try {
            ChatbotSession session = sessionService.getSessionById(sessionId);
            // Retourne directement la liste ordonnée des messages
            return ResponseEntity.ok(session.getMessageHistory());
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}