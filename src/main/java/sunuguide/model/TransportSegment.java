package sunuguide.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transport_segments")
public class TransportSegment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long segmentId;

    private Double segmentCost;
    private Long segmentDurationMinutes;
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    // getters and setters
    public Long getSegmentId() { return segmentId; }
    public void setSegmentId(Long segmentId) { this.segmentId = segmentId; }
    public Double getSegmentCost() { return segmentCost; }
    public void setSegmentCost(Double segmentCost) { this.segmentCost = segmentCost; }
    public Long getSegmentDurationMinutes() { return segmentDurationMinutes; }
    public void setSegmentDurationMinutes(Long segmentDurationMinutes) { this.segmentDurationMinutes = segmentDurationMinutes; }
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
    public Itinerary getItinerary() { return itinerary; }
    public void setItinerary(Itinerary itinerary) { this.itinerary = itinerary; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
