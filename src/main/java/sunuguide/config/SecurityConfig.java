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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS using our CorsConfigurationSource bean
                .cors(Customizer.withDefaults())
                // Disable CSRF for APIs
                .csrf(csrf -> csrf.disable())
                // Permit all requests (for testing)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

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
