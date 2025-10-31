package sunuguide.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunuguide.model.User;
import sunuguide.model.VerificationCode;
import sunuguide.repository.UserRepository;
import sunuguide.repository.VerificationCodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VerificationCodeRepository codeRepository;
    private final MailService mailService; // L'objet MailService réel

    private static final int CODE_EXPIRY_MINUTES = 5;

    // Mise à jour du constructeur pour l'injection
    public UserService(UserRepository userRepository, VerificationCodeRepository codeRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.mailService = mailService; // Initialisation du service d'e-mail
    }

    // ==========================================================
    // A. GESTION DES INVITÉS (ACCÈS ANONYME)
    // ==========================================================
    @Transactional
    public User createGuestUser() {
        User guest = new User(true);
        return userRepository.save(guest);
    }

    // ==========================================================
    // B. AUTHENTIFICATION SÉCURISÉE (PAR E-MAIL/OTP)
    // ==========================================================

    @Transactional
    public User requestLoginCode(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'e-mail est requis pour l'authentification sécurisée.");
        }

        // 1. Trouver ou créer l'utilisateur
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewRegisteredUser(email));

        // 2. Générer et Sauvegarder le code
        String code = generateNumericCode(6);
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(CODE_EXPIRY_MINUTES);

        VerificationCode verificationCode = new VerificationCode(code, expiryDate, user);
        codeRepository.save(verificationCode);

        // 4. APPEL RÉEL DU SERVICE D'ENVOI D'E-MAIL
        // Si cette méthode lève une exception (échec de l'envoi), toute la transaction est annulée.
        mailService.sendVerificationCode(user.getEmail(), code);

        return user;
    }

    @Transactional
    public Optional<User> validateLoginCode(Long userId, String submittedCode) {
        // ... (Logique de validation inchangée) ...
        Optional<VerificationCode> codeOpt = codeRepository.findByUser_UserIdAndCode(userId, submittedCode);

        if (codeOpt.isPresent()) {
            VerificationCode code = codeOpt.get();

            if (code.isExpired()) {
                codeRepository.delete(code);
                return Optional.empty();
            }

            codeRepository.delete(code);
            return Optional.of(code.getUser());
        }

        return Optional.empty();
    }

    // ==========================================================
    // C. HELPERS INTERNES (Inchangés)
    // ==========================================================

    private User createNewRegisteredUser(String email) {
        User registeredUser = new User(email);
        registeredUser.setGuest(false);
        return userRepository.save(registeredUser);
    }

    private String generateNumericCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}