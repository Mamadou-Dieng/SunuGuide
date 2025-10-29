package sunuguide.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunuguide.service.DistanceService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/interval")
public class DistanceController {

    private final DistanceService intervalService;
    Map<String, Object> response = new HashMap<>();

    public DistanceController(DistanceService distanceService) {
        this.intervalService = distanceService;
    }

    @GetMapping("/distance")
    public ResponseEntity<Double> getDistance(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2) {
        double distance = intervalService.calculateDistance(lat1, lon1, lat2, lon2);

        response.put("distance", distance);

        return ResponseEntity.ok(distance);
    }
}
