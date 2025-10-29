package sunuguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sunuguide.dto.ItineraryRequestDTO;
import sunuguide.model.Itinerary;
import sunuguide.service.ItineraryService;

/**
 * Contrôleur REST pour la gestion des itinéraires.
 * Permet de calculer et de sauvegarder un itinéraire
 * à partir des coordonnées géographiques et du mode de transport.
 */
@RestController
@RequestMapping("/api/itineraires")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    /**
     * Endpoint principal : calculer un itinéraire entre deux points.
     * Exemple : POST /api/itineraires/calculer
     */
    @PostMapping("/calculer")
    public ResponseEntity<Itinerary> calculer(@Valid @RequestBody ItineraryRequestDTO dto) {
        Itinerary itin = itineraryService.calculerItineraire(
                dto.getStartLatitude(),
                dto.getStartLongitude(),
                dto.getEndLatitude(),
                dto.getEndLongitude(),
                dto.getTransportMode()
        );
        return ResponseEntity.ok(itin);
    }

    /**
     * Endpoint simple pour tester le contrôleur.
     * Exemple : GET /api/itineraires
     */
    @GetMapping
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("Utilisez POST /api/itineraires/calculer pour générer un nouvel itinéraire.");
    }
}
