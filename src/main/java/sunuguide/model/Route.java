package sunuguide.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;

    private String departurePoint;
    private String arrivalPoint;
    private String chosenCriteria;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Itinerary> itineraries;

    // getters and setters
    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }
    public String getDeparturePoint() { return departurePoint; }
    public void setDeparturePoint(String departurePoint) { this.departurePoint = departurePoint; }
    public String getArrivalPoint() { return arrivalPoint; }
    public void setArrivalPoint(String arrivalPoint) { this.arrivalPoint = arrivalPoint; }
    public String getChosenCriteria() { return chosenCriteria; }
    public void setChosenCriteria(String chosenCriteria) { this.chosenCriteria = chosenCriteria; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<Itinerary> getItineraries() { return itineraries; }
    public void setItineraries(List<Itinerary> itineraries) { this.itineraries = itineraries; }
}
