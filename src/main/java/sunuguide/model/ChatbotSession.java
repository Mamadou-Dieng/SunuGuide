package sunuguide.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatbot_sessions")
public class ChatbotSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    // REMPLACÉ : L'historique des messages est maintenant une collection d'entités 'Message'
    @OneToMany(
            mappedBy = "session", // Fait référence à l'attribut 'session' dans l'entité Message
            cascade = CascadeType.ALL, // Les opérations se propagent aux messages (ex: suppression de session supprime les messages)
            orphanRemoval = true, // Supprime les messages retirés de cette liste
            fetch = FetchType.LAZY // Les messages ne sont chargés que si vous y accédez
    )
    // Classement : Trie les messages par leur heure d'envoi
    @OrderBy("sendTime ASC")
    private List<Message> messageHistory = new ArrayList<>();

    private String languageUsed;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Méthode utilitaire pour ajouter un message et garantir la cohérence bidirectionnelle
    public void addMessage(Message message) {
        messageHistory.add(message);
        message.setSession(this);
    }

    // Méthode utilitaire pour retirer un message
    public void removeMessage(Message message) {
        messageHistory.remove(message);
        message.setSession(null);
    }

    // Getters et Setters

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    // Le getter retourne maintenant la liste des Messages
    public List<Message> getMessageHistory() { return messageHistory; }
    public void setMessageHistory(List<Message> messageHistory) { this.messageHistory = messageHistory; }

    public String getLanguageUsed() { return languageUsed; }
    public void setLanguageUsed(String languageUsed) { this.languageUsed = languageUsed; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}