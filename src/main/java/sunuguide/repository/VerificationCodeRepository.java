package sunuguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunuguide.model.VerificationCode;
import java.util.Optional;

// Repository pour l'entité VerificationCode
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    // Recherche le code par ID utilisateur et code
    Optional<VerificationCode> findByUser_UserIdAndCode(Long userId, String code);
}