package sunuguide.model;

import jakarta.persistence.*;

@Entity
@Table(name = "chatbot_sessions")
public class ChatbotSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @Lob
    private String messageHistory;
    //tableau de string
    //time (l'heur d'envoi du message  en time stamp)
    //ajouter le model message et iniatialiser message history comme un tableau de message
    //table message
    //spring boot  entities

    private String languageUsed;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // getters and setters
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public String getMessageHistory() { return messageHistory; }
    public void setMessageHistory(String messageHistory) { this.messageHistory = messageHistory; }
    public String getLanguageUsed() { return languageUsed; }
    public void setLanguageUsed(String languageUsed) { this.languageUsed = languageUsed; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
