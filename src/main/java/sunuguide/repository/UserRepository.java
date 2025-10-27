package sunuguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunuguide.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Nécessaire pour l'AuthService, mais utilisé indirectement par le UserService.
     */
    Optional<User> findByEmail(String email);
}