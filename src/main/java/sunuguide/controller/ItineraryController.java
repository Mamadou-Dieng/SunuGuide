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
            @RequestParam("depart") String depart,
            @RequestParam("arrivee") String arrivee

    ) {
        Itinerary itin = itineraryService.calculerItineraire(depart, arrivee);
        return ResponseEntity.ok(itin);
    }
}
