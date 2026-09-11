package com.constitution.awareness.service;

import com.constitution.awareness.dto.AuthResponse;
import com.constitution.awareness.dto.LoginRequest;
import com.constitution.awareness.dto.RegisterRequest;

import com.constitution.awareness.entity.User;

import com.constitution.awareness.exception.DuplicateResourceException;
import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.UserRepository;
import com.constitution.awareness.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;


    public AuthService(

            UserRepository userRepository,

            PasswordEncoder passwordEncoder,

            AuthenticationManager authenticationManager,

            UserDetailsService userDetailsService,

            JwtService jwtService

    ) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.authenticationManager =
                authenticationManager;

        this.userDetailsService =
                userDetailsService;

        this.jwtService =
                jwtService;
    }


    /*
     * =====================================
     * REGISTER USER
     * =====================================
     */

    public AuthResponse register(
            RegisterRequest request
    ) {


        if (

                userRepository.existsByEmail(
                        request.getEmail()
                )

        ) {

            throw new DuplicateResourceException(
                    "Email is already registered"
            );
        }


        User user = new User(

                request.getName(),

                request.getEmail(),

                passwordEncoder.encode(
                        request.getPassword()
                ),

                request.getRole()
        );


        User savedUser =
                userRepository.save(
                        user
                );


        UserDetails userDetails =

                userDetailsService.loadUserByUsername(

                        savedUser.getEmail()

                );


        String token =

                jwtService.generateToken(
                        userDetails
                );


        return new AuthResponse(

                token,

                savedUser.getId(),

                savedUser.getName(),

                savedUser.getEmail(),

                savedUser.getRole().name()
        );
    }


    /*
     * =====================================
     * LOGIN USER
     * =====================================
     */

    public AuthResponse login(
            LoginRequest request
    ) {


        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(),

                        request.getPassword()

                )
        );


        User user =

                userRepository

                        .findByEmail(
                                request.getEmail()
                        )

                        .orElseThrow(

                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        UserDetails userDetails =

                userDetailsService.loadUserByUsername(

                        request.getEmail()

                );


        String token =

                jwtService.generateToken(
                        userDetails
                );


        return new AuthResponse(

                token,

                user.getId(),

                user.getName(),

                user.getEmail(),

                user.getRole().name()
        );
    }
}