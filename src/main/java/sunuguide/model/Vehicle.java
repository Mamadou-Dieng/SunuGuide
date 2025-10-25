package sunuguide.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private Integer capacity;
    private Boolean isAvailable;
    private String type; // Bus, Taxi, TER, Car Rapide

    @OneToMany(mappedBy = "vehicle")
    private List<TransportSegment> segments;

    // getters and setters
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public java.util.List<TransportSegment> getSegments() { return segments; }
    public void setSegments(java.util.List<TransportSegment> segments) { this.segments = segments; }
}
