package sunuguide.model;

import jakarta.persistence.*;
import sunuguide.service.PredictionSegment;

import java.util.List;

@Entity
@Table(name = "itineraries")
public class Itinerary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itineraryId;

    // store duration as minutes
    private Long estimatedDurationMinutes;
    private Double estimatedCost;
    private Double totalDistance;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route; // <--- CORRECTION 1: Changé de String à Route

    @ElementCollection // <--- CORRECTION 2: Changé de @OneToMany à @ElementCollection
    @CollectionTable(name = "segment_details", joinColumns = @JoinColumn(name = "itinerary_id"))
    private List<PredictionSegment> segments;

    // getters and setters
    public Long getItineraryId() { return itineraryId; }
    public void setItineraryId(Long itineraryId) { this.itineraryId = itineraryId; }
    public Long getEstimatedDurationMinutes() { return estimatedDurationMinutes; }
    public void setEstimatedDurationMinutes(Long estimatedDurationMinutes) { this.estimatedDurationMinutes = estimatedDurationMinutes; }
    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
    public Double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(Double totalDistance) { this.totalDistance = totalDistance; }
    public Route getRoute() { return route; } // Note : vous devrez aussi changer le type de retour dans le getter/setter
    public void setRoute(Route route) { this.route = route; } // Note : vous devrez aussi changer le type du paramètre dans le setter
    public List<PredictionSegment> getSegments() { return segments; }
    public void setSegments(List<PredictionSegment> segments) { this.segments = segments; }
}