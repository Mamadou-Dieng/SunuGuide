package sunuguide.dto;

import sunuguide.model.User; // Nécessaire pour la méthode fromEntity

public class UserResponseDTO {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String preferredLanguage;
    private String preferences;
    // Le passwordHash est volontairement omis ici

    // --- Méthode de Mapping Statique ---
    public static UserResponseDTO fromEntity(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPreferredLanguage(user.getPreferredLanguage());
        dto.setPreferences(user.getPreferences());
        return dto;
    }

    // --- Getters et Setters ---

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}