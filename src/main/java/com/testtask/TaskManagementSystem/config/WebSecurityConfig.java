package com.testtask.TaskManagementSystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEndcoder passwordEncoder;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/auth",
            "/register"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/*"))
                .authorizeHttpRequests(this::customizeRequest)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/*"))
//                .authorizeHttpRequests(this::customizeRequest)
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        try {
            registry.requestMatchers(new AntPathRequestMatcher("/task/**")).authenticated()
                    .requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated()
                    .anyRequest().permitAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
//        try {
//            registry.requestMatchers(new AntPathRequestMatcher("/task/**"))
//                    .hasAnyRole("USER")
//                    .requestMatchers(new AntPathRequestMatcher("/user/**"))
//                    .hasAnyRole("USER")
//                    .anyRequest().permitAll()
////                    .requestMatchers(AUTH_WHITELIST).permitAll()
//            ;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
