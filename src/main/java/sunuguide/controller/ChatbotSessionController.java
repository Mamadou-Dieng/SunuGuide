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

@RestController // Indique que cette classe gère les requêtes REST
@RequestMapping("/api/sessions") // Base de l'URL pour toutes les méthodes de ce contrôleur
public class ChatbotSessionController {

    private final ChatbotSessionService sessionService;

    @Autowired
    public ChatbotSessionController(ChatbotSessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * POST /api/sessions : Crée une nouvelle session de chatbot.
     */
    @PostMapping
    public ResponseEntity<ChatbotSession> createSession(@RequestBody ChatbotSession session) {
        // Le contrôleur appelle le service pour exécuter la logique
        ChatbotSession createdSession = sessionService.createSession(session);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    /**
     * GET /api/sessions/{sessionId} : Récupère les détails d'une session.
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<ChatbotSession> getSession(@PathVariable Long sessionId) {
        ChatbotSession session = sessionService.getSessionById(sessionId);
        // Le service gère l'exception (si non trouvé), le contrôleur gère la réponse HTTP
        return ResponseEntity.ok(session);
    }

    /**
     * GET /api/sessions/{sessionId}/history : Récupère l'historique des messages.
     */
    @GetMapping("/{sessionId}/history")
    public ResponseEntity<List<Message>> getSessionHistory(@PathVariable Long sessionId) {
        List<Message> history = sessionService.getMessageHistory(sessionId);
        return ResponseEntity.ok(history);
    }

    /**
     * POST /api/sessions/{sessionId}/message : Ajoute un nouveau message à la session.
     * Exemple de corps de requête JSON: {"content": "Bonjour !"}
     */
    @PostMapping("/{sessionId}/message")
    public ResponseEntity<ChatbotSession> sendMessage(@PathVariable Long sessionId,
                                                      @RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        if (content == null || content.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ChatbotSession updatedSession = sessionService.addMessageToHistory(sessionId, content);
        return ResponseEntity.ok(updatedSession);
    }
}