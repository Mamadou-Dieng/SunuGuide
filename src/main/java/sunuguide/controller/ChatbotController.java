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
     * POST /api/chatbot/{sessionId}/message : Ajoute un nouveau message et reçoit la réponse du bot.
     * C'est l'endpoint clé pour la conversation.
     */
    @PostMapping("/{sessionId}/message")
    public ResponseEntity<List<Message>> sendMessageAndGetHistory(@PathVariable Long sessionId,
                                                                  @RequestBody Map<String, String> payload) {
        String content = payload.get("content");

        if (content == null || content.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            // LIGNE CORRIGÉE : Utilise la méthode qui gère le cycle complet (message utilisateur + réponse bot).
            List<Message> updatedHistory = sessionService.sendMessageAndGetResponse(sessionId, content);

            // Retourne l'historique mis à jour (qui contient maintenant le message utilisateur ET la réponse du bot).
            return ResponseEntity.ok(updatedHistory);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/chatbot/{sessionId}/history : Récupère l'historique complet des messages.
     */
    @GetMapping("/{sessionId}/history")
    public ResponseEntity<List<Message>> getSessionHistory(@PathVariable Long sessionId) {
        try {
            // Il est préférable d'utiliser une méthode de service dédiée si elle existe,
            // ou l'ancienne logique simplifiée du service si le service est bien structuré.
            // Vu les corrections précédentes, on peut utiliser le getMessageHistory du service.
            List<Message> history = sessionService.getMessageHistory(sessionId);
            return ResponseEntity.ok(history);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * POST /api/chatbot/session : Crée une nouvelle session de chatbot anonyme.
     */
    @PostMapping("/session")
    public ResponseEntity<ChatbotSession> createSession() {
        ChatbotSession newSession = sessionService.createAnonymousSession();
        // MODIFIÉ : Retourne HttpStatus.OK (200) au lieu de HttpStatus.CREATED (201)
        return new ResponseEntity<>(newSession, HttpStatus.OK);
    }
}
