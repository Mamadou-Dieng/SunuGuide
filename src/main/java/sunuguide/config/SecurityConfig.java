// sunuguide.config.SecurityConfig.java

package sunuguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1. Désactiver la protection CSRF pour les API (Obligatoire pour le POST depuis Swagger)
        http.csrf(AbstractHttpConfigurer::disable);

        // 2. Autoriser l'accès public à l'API du Chatbot et à Swagger
        http.authorizeHttpRequests(auth -> auth
                // Permet l'accès à TOUS les endpoints /api/chatbot/** (pour les tests POST/GET)
                .requestMatchers("/api/chatbot/**").permitAll()
                // Permet l'accès à Swagger UI
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // 3. Sécuriser toutes les autres requêtes (si besoin)
                .anyRequest().authenticated()
        );

        // Cette ligne est optionnelle si vous ne voulez pas de l'authentification Basic Auth par défaut
        // http.httpBasic(Customizer.withDefaults()); 

        return http.build();
    }
}