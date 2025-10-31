package sunuguide.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunuguide.dto.LoginRequest;
import sunuguide.controller.dto.ValidationRequest;
import sunuguide.model.User;
import sunuguide.service.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================================================
    // FLUX 1: CRÉATION D'UN INVITÉ ANONYME
    // ==========================================================

    /**
     * Crée un utilisateur temporaire (invité) pour commencer à utiliser l'application.
     * Accessible par une requête GET/POST simple au démarrage de l'application.
     * POST /api/auth/guest
     */
    @PostMapping("/guest")
    public ResponseEntity<Map<String, Long>> createGuestUser() {
        User guest = userService.createGuestUser();

        // Retourne seulement l'ID (le "token" temporaire) du nouvel invité
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("userId", guest.getUserId())
        );
    }

    // ==========================================================
    // FLUX 2: CONNEXION SÉCURISÉE (OTP) - ÉTAPE 1: DEMANDE DE CODE
    // ==========================================================

    /**
     * L'utilisateur fournit son e-mail. Le service crée/trouve l'utilisateur
     * et envoie le code OTP par e-mail.
     * POST /api/auth/login/request
     */
    @PostMapping("/login/request")
    public ResponseEntity<?> requestLoginCode(@RequestBody LoginRequest request) {
        try {
            User user = userService.requestLoginCode(request.getEmail());

            // La réponse indique que l'opération a réussi et fournit l'ID de l'utilisateur.
            // Le client devra utiliser cet ID pour l'étape de validation.
            return ResponseEntity.ok(Map.of(
                    "message", "Code OTP envoyé à l'e-mail.",
                    "userId", user.getUserId()
            ));

        } catch (IllegalArgumentException e) {
            // Gérer les e-mails vides
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Gérer les autres erreurs (e.g., échec de l'envoi d'e-mail)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Échec de l'envoi du code."));
        }
    }

    // ==========================================================
    // FLUX 3: CONNEXION SÉCURISÉE (OTP) - ÉTAPE 2: VALIDATION DU CODE
    // ==========================================================

    /**
     * L'utilisateur fournit l'ID reçu à l'étape 1 et le code OTP.
     * POST /api/auth/login/validate
     */
    @PostMapping("/login/validate")
    public ResponseEntity<?> validateLoginCode(@RequestBody ValidationRequest request) {

        // Tente de valider le code
        return userService.validateLoginCode(request.getUserId(), request.getCode())
                .map(user -> {
                    // Succès : Le code est valide. L'utilisateur est connecté.
                    // En production, vous retournerez ici un JWT (JSON Web Token)
                    // ou un autre jeton de session.
                    return ResponseEntity.ok(Map.of(
                            "message", "Connexion réussie.",
                            "userEmail", user.getEmail(),
                            "userToken", "JWT_TOKEN_POUR_" + user.getUserId()
                    ));
                })
                .orElseGet(() ->
                        // Échec : Code incorrect ou expiré
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                                Map.of("error", "Code de vérification invalide ou expiré.")
                        )
                );
    }
}