package sunuguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sunuguide.model.Itinerary;
import sunuguide.repository.ItineraryRepository;

/**
 * Service métier pour le calcul et la gestion des itinéraires.
 * Il contient les algorithmes de calcul de distance, de durée et de coût.
 */
@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    /**
     * Calcule la distance, la durée et le coût d’un itinéraire
     * puis enregistre le résultat dans la base de données.
     */
    public Itinerary calculerItineraire(Double lat1, Double lon1, Double lat2, Double lon2, String transportMode) {
        Itinerary itin = new Itinerary();

        // Calcul de la distance en km
        double distanceKm = calculerDistance(lat1, lon1, lat2, lon2);
        itin.setTotalDistance(distanceKm);

        // Calcul de la durée estimée en minutes
        long dureeMinutes = Math.round((distanceKm / getVitesseMoyenne(transportMode)) * 60);
        itin.setEstimatedDurationMinutes(dureeMinutes);

        // Calcul du coût estimé
        double cout = calculerCout(distanceKm, transportMode);
        itin.setEstimatedCost(cout);

        // Sauvegarde de l'itinéraire
        return itineraryRepository.save(itin);
    }

    /**
     * Formule de Haversine : calcule la distance entre deux points GPS.
     */
    private double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Retourne une vitesse moyenne (km/h) selon le mode de transport.
     */
    private double getVitesseMoyenne(String mode) {
        if (mode == null) return 30.0;
        return switch (mode.toUpperCase()) {
            case "BUS" -> 40.0;
            case "CAR" -> 60.0;
            case "TAXI" -> 55.0;
            case "WALK" -> 5.0;
            default -> 30.0;
        };
    }

    /**
     * Retourne un coût estimé selon le mode de transport.
     */
    private double calculerCout(double distanceKm, String mode) {
        if (mode == null) return 0;
        return switch (mode.toUpperCase()) {
            case "BUS" -> distanceKm * 50;
            case "CAR" -> distanceKm * 100;
            case "TAXI" -> distanceKm * 150;
            default -> 0;
        };
    }
}
