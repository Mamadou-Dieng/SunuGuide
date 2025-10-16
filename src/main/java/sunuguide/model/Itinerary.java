package sunuguide.model;

import jakarta.persistence.*;
import java.time.Duration;
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
    private Route route;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL)
    private List<TransportSegment> segments;

    // getters and setters
    public Long getItineraryId() { return itineraryId; }
    public void setItineraryId(Long itineraryId) { this.itineraryId = itineraryId; }
    public Long getEstimatedDurationMinutes() { return estimatedDurationMinutes; }
    public void setEstimatedDurationMinutes(Long estimatedDurationMinutes) { this.estimatedDurationMinutes = estimatedDurationMinutes; }
    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
    public Double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(Double totalDistance) { this.totalDistance = totalDistance; }
    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }
    public java.util.List<TransportSegment> getSegments() { return segments; }
    public void setSegments(java.util.List<TransportSegment> segments) { this.segments = segments; }
}
