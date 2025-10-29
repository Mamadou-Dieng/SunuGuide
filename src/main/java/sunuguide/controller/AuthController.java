package sunuguide.controller;

import sunuguide.dto.UserRegistrationDTO;
import sunuguide.model.ChatbotSession;
import sunuguide.model.User;
import sunuguide.service.AuthService;
import sunuguide.service.ChatbotSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ChatbotSessionService sessionService;

    @Autowired
    public AuthController(AuthService authService, ChatbotSessionService sessionService) {
        this.authService = authService;
        this.sessionService = sessionService;
    }

    /**
     * POST /api/auth/session/start : Démarre une nouvelle session anonyme.
     */
    @PostMapping("/session/start")
    public ResponseEntity<Long> startSession() {
        ChatbotSession session = sessionService.createAnonymousSession();
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.CREATED);
    }

    /**
     * POST /api/auth/register : Enregistrement de l'utilisateur avec fusion de session.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDto) {
        try {
            User newUser = new User();
            newUser.setEmail(registrationDto.getEmail());
            newUser.setPasswordHash(registrationDto.getPassword());
            newUser.setFirstName(registrationDto.getFirstName());
            newUser.setLastName(registrationDto.getLastName());

            authService.registerUser(newUser, registrationDto.getAnonymousSessionId());

            return ResponseEntity.status(HttpStatus.CREATED).body("Inscription réussie. Vous pouvez maintenant vous connecter.");
        } catch (RuntimeException e) {
            // Gère l'exception "Email déjà utilisé"
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * POST /api/auth/login : Connexion.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRegistrationDTO loginDto) {
        try {
            authService.authenticate(loginDto.getEmail(), loginDto.getPassword());
            // En production, cette méthode retournerait un JWT ou un token de session.
            return ResponseEntity.ok("Connexion réussie.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}