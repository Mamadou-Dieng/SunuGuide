package sunuguide.model;

import jakarta.persistence.*;
import java.time.Instant; // Pour l'horodatage

@Entity
@Table(name = "session_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Contenu du message (peut être long, donc @Lob est approprié)
    @Lob
    @Column(nullable = false)
    private String content;

    // Horodatage de l'envoi
    @Column(nullable = false)
    private Instant sendTime;

    // Lien vers la session parente (Clé étrangère)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatbotSession session;

    // --- Constructeurs ---
    public Message() {
        this.sendTime = Instant.now(); // Initialiser le temps lors de la création
    }

    public Message(String content) {
        this(); // Appelle le constructeur par défaut pour initialiser sendTime
        this.content = content;
    }

    // --- Getters et Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getSendTime() { return sendTime; }
    public void setSendTime(Instant sendTime) { this.sendTime = sendTime; }
    public ChatbotSession getSession() { return session; }
    public void setSession(ChatbotSession session) { this.session = session; }
}