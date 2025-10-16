package sunuguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sunuguide.model.Itinerary;
import sunuguide.model.Route;
import sunuguide.repository.ItineraryRepository;
import sunuguide.repository.RouteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public List<Route> getByCriteria(String criteria) {
        return routeRepository.findByChosenCriteria(criteria);
    }

    public Optional<Route> getRoute(Long id) {
        return routeRepository.findById(id);
    }

    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }
}
