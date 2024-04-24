package engine.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic(Customizer.withDefaults())
            .csrf { it.disable() } // for tests to pass
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/register").permitAll()
                    .requestMatchers("/actuator/shutdown").permitAll()
                    .requestMatchers("/api/quizzes/**").hasAuthority("USER")
                    .anyRequest().denyAll()
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
