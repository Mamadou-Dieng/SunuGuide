package sunuguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sunuguide.model.Itinerary;
import sunuguide.model.Route;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    // Example method that posts route data to an external Python AI engine and returns an Itinerary
    public Itinerary requestOptimizedItinerary(Route route) {
        // TODO: adapt URL to your Python AI engine (Flask/FastAPI)
        String url = "http://localhost:5000/optimize";

        Map<String, Object> payload = new HashMap<>();
        payload.put("departure", route.getDeparturePoint());
        payload.put("arrival", route.getArrivalPoint());
        payload.put("criteria", route.getChosenCriteria());

        // simple POST - expects the AI to return a JSON matching Itinerary fields
        try {
            Itinerary result = restTemplate.postForObject(url, payload, Itinerary.class);
            return result;
        } catch (Exception e) {
            // on failure, return a basic empty itinerary - you should improve error handling
            Itinerary fallback = new Itinerary();
            fallback.setEstimatedCost(0.0);
            fallback.setEstimatedDurationMinutes(0L);
            fallback.setTotalDistance(0.0);
            return fallback;
        }
    }
}
