package sunuguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import sunuguide.model.Itinerary;
import sunuguide.model.Route;
import sunuguide.repository.RouteRepository;
import sunuguide.service.PredictionResponse;
import sunuguide.service.PredictionSegment;
import sunuguide.repository.ItineraryRepository;

import java.net.ConnectException;
import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    private RouteRepository routeRepository;


    @Autowired
    private ItineraryRepository itineraryRepository;

    private final WebClient webClient;
    private static final String MODEL_API_URL = "https://unsubmissive-lulu-semibald.ngrok-free.dev/predict";

    public ItineraryService() {
        this.webClient = WebClient.create();
    }

    /**
     * Calcule un itinéraire via le modèle externe et retourne un objet Itinerary.
     */
    public Itinerary calculerItineraire(String depart, String arrivee) {
        PredictionRequest request =
                new PredictionRequest(depart, arrivee);

        try {
            // Appel au modèle externe
            PredictionResponse apiResponse = webClient.post()
                    .uri(MODEL_API_URL)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PredictionResponse.class)
                    .block();

            if (apiResponse == null || apiResponse.getResults() == null || apiResponse.getResults().isEmpty()) {
                return new Itinerary(); // Retourne vide si pas de données



            }

            List<PredictionSegment> segments = apiResponse.getResults();

            // Agrégation des données
            double totalDuration = segments.stream()
                    .mapToDouble(PredictionSegment::getDureeMin)
                    .sum();

            double totalCost = segments.stream()
                    .mapToDouble(PredictionSegment::getPrixEstime)
                    .sum();

            double totalDistance = segments.stream()
                    .mapToDouble(PredictionSegment::getDistanceKm)
                    .sum();

            String route = segments.get(0).getDepart() + " - " + segments.get(0).getArrivee();

            // Création de l'objet Itinerary
            Itinerary itinerary = new Itinerary();
            itinerary.setItineraryId(5L); // tu peux générer ou récupérer dynamiquement
            itinerary.setEstimatedDurationMinutes((long) totalDuration);
            itinerary.setEstimatedCost(totalCost);
            itinerary.setTotalDistance(totalDistance);



            // CORRECTION ICI : CRÉATION ET AFFECTATION DE L'ENTITÉ ROUTE
            Route routeEntity = new Route();
            routeEntity.setDeparturePoint(depart);
            routeEntity.setArrivalPoint(arrivee);
            // Vous pouvez ajouter une logique pour sauvegarder la route si elle n'existe pas,
            // ou la chercher. Pour l'instant, nous la créons.
//            itinerary.setRoute(route);


            itinerary.setRoute(routeEntity); // <--- DECOMMENTEZ ET UTILISEZ L'ENTITÉ
            itinerary.setSegments(segments);

            Route savedRoute = routeRepository.save(routeEntity); // <--- AJOUTER CETTE LIGNE








            itinerary.setRoute(savedRoute); // Utiliser l'objet Route sauvegardé

            // Optionnel : sauvegarder dans la base de données
            // itineraryRepository.save(itinerary);

            return itinerary;

        } catch (WebClientRequestException e) {
            if (e.getRootCause() instanceof ConnectException) {
                System.err.println("ERREUR: Le modèle externe n'est pas démarré. Connexion refusée.");
                return new Itinerary(); // Retourne un objet vide si le service est hors ligne
            }
            throw e; // Relance toute autre exception WebClient
        }


    }

}