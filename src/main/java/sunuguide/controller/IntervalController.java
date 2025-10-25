package sunuguide.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunuguide.service.IntervalService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/interval")
public class IntervalController {

    private final IntervalService intervalService;
    Map<String, Object> response = new HashMap<>();

    public IntervalController(IntervalService intervalService) {
        this.intervalService = intervalService;
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
