package sunuguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1. Désactiver la protection CSRF pour les API (Obligatoire pour le POST depuis Swagger)
        http.csrf(AbstractHttpConfigurer::disable);

        // 2. Autoriser l'accès public à l'API du Chatbot, à la Distance, et à Swagger
        http.authorizeHttpRequests(auth -> auth
                // Permet l'accès à TOUS les endpoints /api/chatbot/**
                .requestMatchers("/api/chatbot/**").permitAll()

                // NOUVELLE LIGNE : Autorise l'accès au contrôleur de Distance
                .requestMatchers("/api/distance/**").permitAll()

                .requestMatchers("/api/messages/**").permitAll()

                // Permet l'accès à Swagger UI
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // 3. Sécuriser toutes les autres requêtes par défaut
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
//{
//  "content": "derniere test de scrum-8."
//INSERT INTO chatbot_sessions (language_used) VALUES ('fr');
//}