package sunuguide.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO servant à transférer les coordonnées et le mode de transport
 * pour le calcul d’un itinéraire.
 */
public class ItineraryRequestDTO {

    @NotNull(message = "La latitude de départ est obligatoire")
    @Min(value = -90, message = "Latitude invalide (min -90)")
    @Max(value = 90, message = "Latitude invalide (max 90)")
    private Double startLatitude;

    @NotNull(message = "La longitude de départ est obligatoire")
    @Min(value = -180, message = "Longitude invalide (min -180)")
    @Max(value = 180, message = "Longitude invalide (max 180)")
    private Double startLongitude;

    @NotNull(message = "La latitude d’arrivée est obligatoire")
    @Min(value = -90, message = "Latitude invalide (min -90)")
    @Max(value = 90, message = "Latitude invalide (max 90)")
    private Double endLatitude;

    @NotNull(message = "La longitude d’arrivée est obligatoire")
    @Min(value = -180, message = "Longitude invalide (min -180)")
    @Max(value = 180, message = "Longitude invalide (max 180)")
    private Double endLongitude;

    @NotNull(message = "Le mode de transport est obligatoire")
    private String transportMode;

    // --- Getters et Setters ---
    public Double getStartLatitude() { return startLatitude; }
    public void setStartLatitude(Double startLatitude) { this.startLatitude = startLatitude; }

    public Double getStartLongitude() { return startLongitude; }
    public void setStartLongitude(Double startLongitude) { this.startLongitude = startLongitude; }

    public Double getEndLatitude() { return endLatitude; }
    public void setEndLatitude(Double endLatitude) { this.endLatitude = endLatitude; }

    public Double getEndLongitude() { return endLongitude; }
    public void setEndLongitude(Double endLongitude) { this.endLongitude = endLongitude; }

    public String getTransportMode() { return transportMode; }
    public void setTransportMode(String transportMode) { this.transportMode = transportMode; }
}
