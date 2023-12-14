package com.tabihoudai.tabihoudai_api.config;

import com.tabihoudai.tabihoudai_api.jwt.filter.JwtAuthenticationFilter;
import com.tabihoudai.tabihoudai_api.jwt.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/api/board/list").permitAll()
                        .requestMatchers("/api/board/search").permitAll()
                        .requestMatchers("/api/board/view").permitAll()
                        .requestMatchers("/api/board/write").hasRole("USER")
                        .requestMatchers("/api/board/delete").hasRole("USER")
                        .requestMatchers("/api/board/modify").hasRole("USER")
                        .requestMatchers("/api/board/report").hasRole("USER")
                        .requestMatchers("/sample/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}
