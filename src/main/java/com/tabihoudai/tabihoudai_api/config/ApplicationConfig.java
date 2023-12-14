package com.tabihoudai.tabihoudai_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){//시큐리티에서 사용하는 사용자 정보를 가져오는 서비스
//        UserDetails user = User.builder()
//                .username("user1")//username은 id
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")//권한
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
