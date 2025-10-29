package sunuguide.service;

import sunuguide.model.ChatbotSession;
import sunuguide.model.User;
// IMPORTATION CLÉ : Utiliser l'interface standard de Spring Security
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class AuthService {

    private final UserService userService;
    private final ChatbotSessionService sessionService;

    // ANCIEN : private final sunuguide.util.PasswordEncoder passwordEncoder;
    // NOUVEAU : Injection du Bean défini dans SecurityConfig
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, ChatbotSessionService sessionService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Gère l'inscription et attache la session anonyme existante.
     */
    public User registerUser(User newUser, Long anonymousSessionId) throws RuntimeException {
        if (userService.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("L'e-mail est déjà utilisé.");
        }

        // 1. Hachage via le Bean BCrypt injecté
        String encodedPassword = passwordEncoder.encode(newUser.getPasswordHash());
        newUser.setPasswordHash(encodedPassword);

        // ... reste du code ...
        User savedUser = userService.save(newUser);

        if (anonymousSessionId != null) {
            try {
                ChatbotSession session = sessionService.getSessionById(anonymousSessionId);
                session.setUser(savedUser);
                savedUser.setSession(session);
                sessionService.save(session);
            } catch (NoSuchElementException ignored) {}
        }
        return savedUser;
    }

    /**
     * Gère la connexion et vérifie le mot de passe.
     */
    public User authenticate(String email, String rawPassword) throws RuntimeException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Identifiants incorrects."));

        // 2. Vérification via le Bean BCrypt injecté
        if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return user;
        } else {
            throw new RuntimeException("Identifiants incorrects.");
        }
    }
}