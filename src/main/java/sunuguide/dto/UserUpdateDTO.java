package sunuguide.dto;

public class UserUpdateDTO {
    private String preferredLanguage;
    private String preferences;

    // --- Getters et Setters ---

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