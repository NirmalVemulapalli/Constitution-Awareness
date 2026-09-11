package com.constitution.awareness.config;

import com.constitution.awareness.security.CustomUserDetailsService;
import com.constitution.awareness.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /*
     * =====================================
     * DEPENDENCIES
     * =====================================
     */

    private final JwtAuthenticationFilter
            jwtAuthenticationFilter;

    private final CustomUserDetailsService
            userDetailsService;

    private final PasswordEncoder
            passwordEncoder;


    /*
     * =====================================
     * CONSTRUCTOR
     * =====================================
     */

    public SecurityConfig(

            JwtAuthenticationFilter
                    jwtAuthenticationFilter,

            CustomUserDetailsService
                    userDetailsService,

            PasswordEncoder
                    passwordEncoder

    ) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;

        this.userDetailsService =
                userDetailsService;

        this.passwordEncoder =
                passwordEncoder;
    }


    /*
     * =====================================
     * SECURITY FILTER CHAIN
     * =====================================
     */

    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {

        http


                /*
                 * =====================================
                 * DISABLE CSRF
                 * =====================================
                 */

                .csrf(
                        csrf -> csrf.disable()
                )


                /*
                 * =====================================
                 * ENABLE CORS
                 * =====================================
                 */

                .cors(
                        cors -> cors.configurationSource(
                                corsConfigurationSource()
                        )
                )


                /*
                 * =====================================
                 * STATELESS SESSION
                 * =====================================
                 */

                .sessionManagement(

                        session ->

                                session.sessionCreationPolicy(

                                        SessionCreationPolicy.STATELESS

                                )
                )


                /*
                 * =====================================
                 * AUTHORIZE REQUESTS
                 * =====================================
                 */

                .authorizeHttpRequests(

                        auth -> auth


                                /*
                                 * =====================================
                                 * PUBLIC AUTH ENDPOINTS
                                 * =====================================
                                 */

                                .requestMatchers(

                                        "/api/auth/**",

                                        "/api/health"

                                ).permitAll()


                                /*
                                 * =====================================
                                 * ARTICLE MANAGEMENT
                                 *
                                 * IMPORTANT:
                                 * Management routes must come before
                                 * public article routes.
                                 * =====================================
                                 */


                                /*
                                 * GET MY ARTICLES
                                 *
                                 * ADMIN + EDUCATOR
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/articles/my"

                                ).hasAnyRole(

                                        "EDUCATOR",

                                        "ADMIN"

                                )


                                /*
                                 * ARTICLE MANAGEMENT
                                 *
                                 * ADMIN:
                                 * Can view all articles.
                                 *
                                 * EDUCATOR:
                                 * Can manage own articles.
                                 *
                                 * Includes drafts.
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/articles/manage/**"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * =====================================
                                 * PUBLIC ARTICLE ENDPOINTS
                                 *
                                 * Service layer ensures only
                                 * published articles are returned.
                                 * =====================================
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/articles/**",

                                        "/api/categories/**",

                                        "/api/parts/**"

                                ).permitAll()


                                /*
                                 * =====================================
                                 * QUIZ
                                 * =====================================
                                 */


                                /*
                                 * =====================================
                                 * QUIZ MANAGEMENT
                                 *
                                 * ADMIN + EDUCATOR
                                 *
                                 * IMPORTANT:
                                 * These routes must come before the
                                 * public /api/quiz/questions route.
                                 * =====================================
                                 */


                                /*
                                 * Get manageable quiz questions
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/quiz/manage/questions"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * Get quiz attempts
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/quiz/manage/attempts"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * Create quiz questions
                                 */

                                .requestMatchers(

                                        HttpMethod.POST,

                                        "/api/quiz/questions"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * Update quiz questions
                                 */

                                .requestMatchers(

                                        HttpMethod.PUT,

                                        "/api/quiz/questions/**"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * Delete quiz questions
                                 */

                                .requestMatchers(

                                        HttpMethod.DELETE,

                                        "/api/quiz/questions/**"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * Public quiz questions
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/quiz/questions"

                                ).permitAll()


                                /*
                                 * Submit quiz answers
                                 */

                                .requestMatchers(

                                        HttpMethod.POST,

                                        "/api/quiz/submit"

                                ).hasRole(

                                        "CITIZEN"

                                )


                                /*
                                 * Citizen quiz progress
                                 */

                                .requestMatchers(

                                        HttpMethod.GET,

                                        "/api/quiz/progress"

                                ).hasRole(

                                        "CITIZEN"

                                )


                                /*
                                 * =====================================
                                 * DASHBOARD
                                 * =====================================
                                 */

                                .requestMatchers(

                                        "/api/dashboard/**"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * =====================================
                                 * ADMIN ENDPOINTS
                                 * =====================================
                                 */

                                .requestMatchers(

                                        "/api/admin/**"

                                ).hasRole(

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * EDUCATOR ENDPOINTS
                                 * =====================================
                                 */

                                .requestMatchers(

                                        "/api/educator/**"

                                ).hasAnyRole(

                                        "EDUCATOR",

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * CITIZEN ENDPOINTS
                                 * =====================================
                                 */

                                .requestMatchers(

                                        "/api/citizen/**"

                                ).hasAnyRole(

                                        "CITIZEN",

                                        "EDUCATOR",

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * ARTICLE / CONTENT CREATION
                                 * =====================================
                                 */

                                .requestMatchers(

                                        HttpMethod.POST,

                                        "/api/articles",

                                        "/api/parts",

                                        "/api/categories"

                                ).hasAnyRole(

                                        "EDUCATOR",

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * ARTICLE / CONTENT UPDATE
                                 * =====================================
                                 */

                                .requestMatchers(

                                        HttpMethod.PUT,

                                        "/api/articles/**",

                                        "/api/parts/**",

                                        "/api/categories/**"

                                ).hasAnyRole(

                                        "EDUCATOR",

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * PUBLISH / UNPUBLISH ARTICLES
                                 * =====================================
                                 */

                                .requestMatchers(

                                        HttpMethod.PATCH,

                                        "/api/articles/**"

                                ).hasAnyRole(

                                        "EDUCATOR",

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * DELETE ARTICLES
                                 *
                                 * ADMIN:
                                 * Can delete any article.
                                 *
                                 * EDUCATOR:
                                 * Can delete only own articles.
                                 *
                                 * Ownership validation happens
                                 * in the service layer.
                                 * =====================================
                                 */

                                .requestMatchers(

                                        HttpMethod.DELETE,

                                        "/api/articles/**"

                                ).hasAnyRole(

                                        "ADMIN",

                                        "EDUCATOR"

                                )


                                /*
                                 * =====================================
                                 * DELETE PARTS / CATEGORIES
                                 *
                                 * ADMIN ONLY
                                 * =====================================
                                 */

                                .requestMatchers(

                                        HttpMethod.DELETE,

                                        "/api/parts/**",

                                        "/api/categories/**"

                                ).hasRole(

                                        "ADMIN"

                                )


                                /*
                                 * =====================================
                                 * EVERYTHING ELSE
                                 * =====================================
                                 */

                                .anyRequest()

                                .authenticated()
                )


                /*
                 * =====================================
                 * EXCEPTION HANDLING
                 * =====================================
                 */

                .exceptionHandling(

                        exception -> exception


                                .authenticationEntryPoint(

                                        (

                                                request,

                                                response,

                                                authException

                                        ) ->

                                                response.setStatus(

                                                        HttpStatus.UNAUTHORIZED.value()

                                                )
                                )


                                .accessDeniedHandler(

                                        (

                                                request,

                                                response,

                                                accessDeniedException

                                        ) ->

                                                response.setStatus(

                                                        HttpStatus.FORBIDDEN.value()

                                                )
                                )
                )


                /*
                 * =====================================
                 * AUTHENTICATION PROVIDER
                 * =====================================
                 */

                .authenticationProvider(

                        authenticationProvider()

                )


                /*
                 * =====================================
                 * JWT FILTER
                 * =====================================
                 */

                .addFilterBefore(

                        jwtAuthenticationFilter,

                        UsernamePasswordAuthenticationFilter.class

                );


        return http.build();
    }


    /*
     * =====================================
     * AUTHENTICATION PROVIDER
     * =====================================
     */

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =

                new DaoAuthenticationProvider(

                        userDetailsService

                );


        provider.setPasswordEncoder(

                passwordEncoder

        );


        return provider;
    }


    /*
     * =====================================
     * AUTHENTICATION MANAGER
     * =====================================
     */

    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration configuration

    ) throws Exception {

        return configuration
                .getAuthenticationManager();
    }


    /*
     * =====================================
     * CORS CONFIGURATION
     * =====================================
     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {


        CorsConfiguration configuration =

                new CorsConfiguration();


        configuration.setAllowedOriginPatterns(

                List.of(

                        "http://localhost:*"

                )
        );


        configuration.setAllowedMethods(

                List.of(

                        "GET",

                        "POST",

                        "PUT",

                        "PATCH",

                        "DELETE",

                        "OPTIONS"

                )
        );


        configuration.setAllowedHeaders(

                List.of("*")

        );


        configuration.setAllowCredentials(

                true

        );


        configuration.setMaxAge(

                3600L

        );


        UrlBasedCorsConfigurationSource source =

                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(

                "/**",

                configuration

        );


        return source;
    }
}