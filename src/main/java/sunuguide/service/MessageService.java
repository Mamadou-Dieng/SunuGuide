package sunuguide.service;

import sunuguide.model.Message;
import sunuguide.repository.MessageRepository; // Vous aurez besoin de créer cette interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Récupère un message par son ID.
     */
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message non trouvé avec l'ID: " + messageId));
    }

    /**
     * Sauvegarde ou met à jour un message.
     * (Bien que l'ajout se fasse souvent via ChatbotSessionService, cette méthode est utile pour la modification ou le test)
     */
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Exemple de logique métier : Trouver tous les messages d'un contenu spécifique.
     */
    public List<Message> findMessagesByContentKeyword(String keyword) {
        // Cela nécessiterait une méthode personnalisée dans le MessageRepository
        // return messageRepository.findByContentContainingIgnoreCase(keyword);
        return messageRepository.findAll(); // Exemple simple
    }
}