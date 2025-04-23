package com.cryxie.config;

import com.cryxie.config.filter.SecurityFilter;
import com.cryxie.models.User.Role;
import com.cryxie.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, AppUtils.authEndpoint.concat("/**")).permitAll()
                        .requestMatchers(HttpMethod.POST, AppUtils.userEndpoint.concat("/**")).authenticated()
                        .requestMatchers(HttpMethod.POST, AppUtils.votoEndpoint.concat("/**")).authenticated()

                        .requestMatchers(HttpMethod.PUT, AppUtils.userEndpoint.concat("/**")).authenticated()

                        .requestMatchers(HttpMethod.GET, AppUtils.movieEndpoint.concat("/{id}")).permitAll()
                        .requestMatchers(HttpMethod.GET, AppUtils.movieEndpoint.concat("/search")).permitAll()

                        .requestMatchers(HttpMethod.GET, AppUtils.userEndpoint.concat("/**"))
                        .hasAuthority(Role.USUARIO.name())

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
