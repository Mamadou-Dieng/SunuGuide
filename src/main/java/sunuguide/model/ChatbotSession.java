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

    @OneToMany(
            mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("sendTime ASC")
    private List<Message> messageHistory = new ArrayList<>();

    private String languageUsed;

    @OneToOne
    @JoinColumn(name = "user_id") // user_id peut être NULL (session anonyme)
    private User user;

    // --- Méthode utilitaire ---
    public void addMessage(Message message) {
        messageHistory.add(message);
        message.setSession(this);
    }

    // --- Constructeurs ---
    public ChatbotSession() {}

    // --- Getters et Setters ---
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public List<Message> getMessageHistory() { return messageHistory; }
    public void setMessageHistory(List<Message> messageHistory) { this.messageHistory = messageHistory; }
    public String getLanguageUsed() { return languageUsed; }
    public void setLanguageUsed(String languageUsed) { this.languageUsed = languageUsed; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}