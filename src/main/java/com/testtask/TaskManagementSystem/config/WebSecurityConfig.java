package com.testtask.TaskManagementSystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private UserDetailsService userService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void setUserService(UserDetailsService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v3/api-docs",
//            "/webjars/**",
//            "/auth",
//            "/register"};

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/*"))
//                .authorizeHttpRequests(this::customizeRequest)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .formLogin(Customizer.withDefaults())
////                .logout(Customizer.withDefaults())
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.ignoringRequestMatchers("/*"))
//                .authorizeHttpRequests(this::customizeRequest)
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

//    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
//        try {
//            registry.requestMatchers(new AntPathRequestMatcher("/task/**")).authenticated()
//                    .requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated()
//                    .anyRequest().permitAll();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                authz -> {authz
                                .antMatchers("/task/**", "/user/**").authenticated()
                                .anyRequest().permitAll()
                                .and()
                                .addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
                });
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
