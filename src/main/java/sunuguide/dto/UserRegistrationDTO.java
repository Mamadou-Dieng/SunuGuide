package sunuguide.dto;

// Note: L'importation de sunuguide.model.User n'est pas nécessaire ici,
// car les DTOs ne devraient dépendre que des types Java de base.

public class UserRegistrationDTO {
    private String email;
    private String password; // Mot de passe en clair envoyé par le client (sera haché par le service)
    private String firstName;
    private String lastName;
    private Long anonymousSessionId; // Pour la fusion de l'historique anonyme

    // --- Getters et Setters ---

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAnonymousSessionId() {
        return anonymousSessionId;
    }

    public void setAnonymousSessionId(Long anonymousSessionId) {
        this.anonymousSessionId = anonymousSessionId;
    }
}