package com.ucad.m2SIR.SenBook.configuration;

import com.ucad.m2SIR.SenBook.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserAuthService userAuthService;
    private final PasswordConfig passwordConfig;
    private final JwtFilter jwtFilter;
    private final LogoutHandler logoutHandler;


    public SecurityConfig(UserAuthService userAuthService, PasswordConfig passwordConfig, JwtFilter jwtFilter, LogoutService logoutHandler) {
        this.userAuthService = userAuthService;
        this.passwordConfig = passwordConfig;
        this.jwtFilter = jwtFilter;
        this.logoutHandler = logoutHandler;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordConfig.getEncoder());
        auth.setUserDetailsService(userAuthService);
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/register", "/api/livres/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/logout"))
                .logout(logout -> logout.addLogoutHandler(logoutHandler))
                .logout(logout -> logout.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }
}
