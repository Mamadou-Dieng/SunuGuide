package sunuguide.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // Email est l'unique identifiant pour les utilisateurs enregistrés.
    // Rendu 'nullable = true' pour les utilisateurs invités (anonymes).
    @Column( nullable = true)
    private String email;

    @Column(nullable = false)
    private boolean isGuest = false;

    private String firstName;
    private String lastName;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String preferredLanguage;
    private String preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatbotSession session; // Assurez-vous d'avoir une classe ChatbotSession correspondante

    // --- Méthodes de cycle de vie JPA ---
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // --- Constructeurs ---
    public User() {}

    // Pour un nouvel invité (anonyme)
    public User(boolean isGuest) {
        this.isGuest = isGuest;
        this.email = null;
    }

    // Pour un nouvel utilisateur enregistré
    public User(String email) {
        this.email = email;
        this.isGuest = false;
    }

    // --- Getters et Setters ---
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isGuest() { return isGuest; }
    public void setGuest(boolean guest) { isGuest = guest; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }

    public List<Route> getRoutes() { return routes; }
    public void setRoutes(List<Route> routes) { this.routes = routes; }

    public ChatbotSession getSession() { return session; }
    public void setSession(ChatbotSession session) {
        this.session = session;
        if (session != null && session.getUser() != this) {
            session.setUser(this);
        }
    }
}