package sunuguide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "session_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant sendTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatbotSession session;

    // --- Constructeurs ---
    public Message() {
        this.sendTime = Instant.now();
    }

    public Message(String content) {
        this();
        this.content = content;
    }

    // --- Getters et Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getSendTime() { return sendTime; }
    public void setSendTime(Instant sendTime) { this.sendTime = sendTime; }
    @JsonIgnore
    public ChatbotSession getSession() { return session; }
    public void setSession(ChatbotSession session) { this.session = session; }
}