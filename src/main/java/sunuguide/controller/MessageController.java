package sunuguide.controller;

import sunuguide.model.Message;
import sunuguide.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages") // Endpoint pour la gestion individuelle des messages
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * GET /api/messages/{messageId} : Récupère un message individuel par son ID.
     */
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable Long messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    // Un POST n'est généralement pas nécessaire ici car les messages sont ajoutés via ChatbotSessionController
    // Mais un DELETE ou un PUT (pour éditer un message) pourrait être implémenté ici.

    /**
     * DELETE /api/messages/{messageId} : Supprime un message (nécessite d'être implémenté dans le service et le repository).
     */
    // @DeleteMapping("/{messageId}")
    // public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
    //     messageService.deleteMessage(messageId);
    //     return ResponseEntity.noContent().build();
    // }
}