package sunuguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisé pour d'autres besoins potentiels, mais non pour l'OTP
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 0. Autoriser CORS pour les requêtes multi-origines (souvent nécessaire)
        http.cors(Customizer.withDefaults());

        // 1. Désactiver la protection CSRF (Cause des erreurs 403 sur les requêtes POST/API)
        http.csrf(AbstractHttpConfigurer::disable);

        // 2. Définir les autorisations d'accès aux URL
        http.authorizeHttpRequests(auth -> auth
                // Autoriser l'accès public à TOUS les endpoints d'authentification (invité, OTP request, OTP validate)
                .requestMatchers("/api/auth/**").permitAll()

                // Autoriser l'accès public aux autres services (Chatbot, Distance, Messages, Users)
                .requestMatchers("/api/chatbot/**", "/api/distance/**", "/api/messages/**", "/api/users/**").permitAll()

                .requestMatchers("/api/itineraires/**").permitAll()

                // Permet l'accès à Swagger UI
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // 3. Sécuriser toutes les autres requêtes par défaut (nécessite un JWT/Session)
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
//{
//  "content": "derniere test de scrum-8."
//INSERT INTO chatbot_sessions (language_used) VALUES ('fr');
//}

//{
//  "startLatitude": 48.8584,
//  "startLongitude": 2.2945,
//  "endLatitude": 40.7128,
//  "endLongitude": -74.0060,
//  "transportMode": "CAR"
//}
