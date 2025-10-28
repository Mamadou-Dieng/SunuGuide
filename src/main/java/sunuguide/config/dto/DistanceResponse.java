package sunuguide.config.dto;

public class DistanceResponse {
    private double distance;
    private String unit = "km"; // Bonne pratique d'ajouter l'unité

    // Constructeur pour l'initialisation
    public DistanceResponse(double distance) {
        this.distance = distance;
    }

    // Getters et Setters (nécessaires pour la sérialisation JSON)
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}