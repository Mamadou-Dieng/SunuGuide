package sunuguide.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String preferredLanguage;
    private String preferences; // comma-separated: fast,cheap,comfy

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Route> routes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ChatbotSession session;

    // getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }
    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
    public List<Route> getRoutes() { return routes; }
    public void setRoutes(List<Route> routes) { this.routes = routes; }
    public ChatbotSession getSession() { return session; }
    public void setSession(ChatbotSession session) { this.session = session; }

}
