package com.constitution.awareness.service;


import com.constitution.awareness.dto.ChangePasswordRequest;
import com.constitution.awareness.dto.ProfileUpdateRequest;
import com.constitution.awareness.dto.UserResponse;

import com.constitution.awareness.entity.User;

import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.UserRepository;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


@Service
public class ProfileService {


    private final UserRepository
            userRepository;


    private final PasswordEncoder
            passwordEncoder;


    public ProfileService(

            UserRepository
                    userRepository,

            PasswordEncoder
                    passwordEncoder

    ) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;

    }


    /*
     * =====================================
     * GET CURRENT PROFILE
     * =====================================
     */

    public UserResponse
    getProfile() {

        User user =
                getCurrentUser();


        return mapToResponse(
                user
        );

    }


    /*
     * =====================================
     * UPDATE PROFILE
     * =====================================
     */

    public UserResponse
    updateProfile(

            ProfileUpdateRequest request

    ) {

        User user =
                getCurrentUser();


        user.setName(

                request
                        .getName()
                        .trim()

        );


        User updatedUser =
                userRepository.save(
                        user
                );


        return mapToResponse(
                updatedUser
        );

    }


    /*
     * =====================================
     * CHANGE PASSWORD
     * =====================================
     */

    public void
    changePassword(

            ChangePasswordRequest request

    ) {

        User user =
                getCurrentUser();


        /*
         * =====================================
         * VERIFY CURRENT PASSWORD
         * =====================================
         */

        boolean passwordMatches =

                passwordEncoder.matches(

                        request
                                .getCurrentPassword(),

                        user
                                .getPassword()

                );


        if (

                !passwordMatches

        ) {

            throw new IllegalArgumentException(

                    "Current password is incorrect"

            );

        }


        /*
         * =====================================
         * PREVENT SAME PASSWORD
         * =====================================
         */

        boolean samePassword =

                passwordEncoder.matches(

                        request
                                .getNewPassword(),

                        user
                                .getPassword()

                );


        if (

                samePassword

        ) {

            throw new IllegalArgumentException(

                    "New password must be different from your current password"

            );

        }


        /*
         * =====================================
         * ENCODE NEW PASSWORD
         * =====================================
         */

        String encodedPassword =

                passwordEncoder.encode(

                        request
                                .getNewPassword()

                );


        user.setPassword(

                encodedPassword

        );


        userRepository.save(

                user

        );

    }


    /*
     * =====================================
     * GET CURRENT AUTHENTICATED USER
     * =====================================
     */

    private User
    getCurrentUser() {

        Authentication authentication =

                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (

                authentication == null

                ||

                !authentication.isAuthenticated()

                ||

                "anonymousUser".equals(

                        authentication
                                .getPrincipal()

                )

        ) {

            throw new AccessDeniedException(

                    "User is not authenticated"

            );

        }


        String email =

                authentication
                        .getName();


        return userRepository
                .findByEmail(
                        email
                )
                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "User not found"

                                )

                );

    }


    /*
     * =====================================
     * MAP ENTITY TO RESPONSE
     * =====================================
     */

    private UserResponse
    mapToResponse(

            User user

    ) {

        UserResponse response =

                new UserResponse();


        response.setId(

                user.getId()

        );


        response.setName(

                user.getName()

        );


        response.setEmail(

                user.getEmail()

        );


        response.setRole(

                user.getRole()
                        .name()

        );


        response.setActive(

                user.isActive()

        );


        return response;

    }

}