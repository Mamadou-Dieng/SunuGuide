package sunuguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunuguide.model.Itinerary;
import sunuguide.service.ItineraryService;

@RestController
@RequestMapping("/api/itineraires")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @GetMapping("/calculer")
    public ResponseEntity<Itinerary> calculerItineraire(
            @RequestParam("lat1") Double lat1,
            @RequestParam("lon1") Double lon1,
            @RequestParam("lat2") Double lat2,
            @RequestParam("lon2") Double lon2,
            @RequestParam(value = "mode", defaultValue = "BUS") String mode
    ) {
        Itinerary itin = itineraryService.calculerItineraire(lat1, lon1, lat2, lon2, mode);
        return ResponseEntity.ok(itin);
    }
}
