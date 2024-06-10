package org.markmcguire.cardcollectors.config;

import org.markmcguire.cardcollectors.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/css/**", "/js/**", "/images/**", "/player/register", "/player/login").permitAll()
                .anyRequest().hasRole("USER")

        )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/player/login")
                .loginProcessingUrl("/player/login")
                .defaultSuccessUrl("/player/profile", true)
                .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/player/login").permitAll()
        )
        .userDetailsService(userDetailsService);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
