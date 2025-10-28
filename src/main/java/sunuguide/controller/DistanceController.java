package sunuguide.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunuguide.config.dto.DistanceResponse;
import sunuguide.service.DistanceService;

@RestController
@RequestMapping("/api/distance")
public class DistanceController {

    private final DistanceService distanceService;

    // Supprimez la ligne : Map<String, Object> response = new HashMap<>();

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping("/distance")
    // ✅ Changez le type de retour pour retourner ResponseEntity<DistanceResponse>
    public ResponseEntity<DistanceResponse> getDistance(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2) {

        double distance = distanceService.calculateDistance(lat1, lon1, lat2, lon2);

        // Créez le DTO qui encapsule le résultat
        DistanceResponse responseDto = new DistanceResponse(distance);

        // ✅ Retournez le DTO. Spring le convertira en JSON structuré.
        return ResponseEntity.ok(responseDto);
    }
}