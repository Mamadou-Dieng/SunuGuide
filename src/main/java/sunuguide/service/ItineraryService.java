package sunuguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException; // Import nécessaire
import sunuguide.model.Itinerary;
import sunuguide.repository.ItineraryRepository;
import reactor.core.publisher.Mono;

import java.net.ConnectException; // Import nécessaire

/**
 * Service métier pour l'obtention des itinéraires via un modèle externe (IA / API).
 */
@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    private final WebClient webClient;
    private static final String MODEL_API_URL = "https://unsubmissive-lulu-semibald.ngrok-free.dev/predict";

    public ItineraryService() {
        this.webClient = WebClient.create();
    }

    /**
     * Appelle le modèle externe. Gère l'échec de connexion en retournant un objet vide
     * ou en lançant une exception personnalisée si nécessaire.
     * http://127.0.0.1:8000/predict
     */
    public Itinerary calculerItineraire(String depart, String arrivee ) {
        var request = new PredictionRequest(depart, arrivee);
        PredictionResponse response;

        try {
            // Appel au modèle (API externe)
            response = webClient.post()
                    .uri(MODEL_API_URL)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PredictionResponse.class)
                    .block(); // synchrone
        } catch (WebClientRequestException e) {
            // Vérifier spécifiquement si la cause est 'Connection refused'
            if (e.getRootCause() instanceof ConnectException) {
                System.err.println("ERREUR: Le modèle d'itinéraire externe (port 5000) n'est pas démarré. Connexion refusée.");

                // Option 1: Renvoyer un objet Itinerary vide pour que le contrôleur réponde 200 OK
                // C'est l'approche la plus souple pour le moment.
                return new Itinerary();

                // Option 2: Lancer une exception personnalisée que le contrôleur gérera
                // (Ex: throw new ModelNotAvailableException("Le service de modèle est hors ligne.");)
            }
            // Si c'est une autre erreur WebClient (timeout, 4xx, 5xx), la relancer
            throw e;
        }

        // Construction et Sauvegarde de l’objet Itinerary
        Itinerary itin = new Itinerary();
        if (response != null) {
            itin.setTotalDistance(response.getDistance());
            itin.setEstimatedDurationMinutes(response.getDuration());
            itin.setEstimatedCost(response.getCost());
        }

        // Sauvegarde en base (même si les valeurs sont 0/null si le modèle est hors ligne)
        return itineraryRepository.save(itin);
    }

    // ... (Petites classes internes PredictionRequest et PredictionResponse inchangées) ...

    private record PredictionRequest(String depart, String arrivee) {}
    private static class PredictionResponse {
        private Double distance;
        private Long duration;
        private Double cost;

        public Double getDistance() { return distance; }
        public Long getDuration() { return duration; }
        public Double getCost() { return cost; }

        public void setDistance(Double distance) { this.distance = distance; }
        public void setDuration(Long duration) { this.duration = duration; }
        public void setCost(Double cost) { this.cost = cost; }
    }
}