package com.constitution.awareness.service;


import com.constitution.awareness.dto.UserResponse;
import com.constitution.awareness.dto.UserUpdateRequest;

import com.constitution.awareness.entity.Role;
import com.constitution.awareness.entity.User;

import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


@Service
public class UserManagementService {


    private final UserRepository userRepository;


    public UserManagementService(

            UserRepository userRepository

    ) {

        this.userRepository =
                userRepository;
    }


    /*
     * =====================================
     * GET ALL USERS
     * =====================================
     */

    public Page<UserResponse> getUsers(

            Pageable pageable

    ) {

        validateAdmin();


        return userRepository
                .findAll(pageable)
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * SEARCH USERS
     * =====================================
     */

    public Page<UserResponse> searchUsers(

            String keyword,

            Pageable pageable

    ) {

        validateAdmin();


        return userRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(

                        keyword,

                        keyword,

                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * GET SINGLE USER
     * =====================================
     */

    public UserResponse getUser(

            Long id

    ) {

        validateAdmin();


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User not found"
                                        )
                        );


        return mapToResponse(
                user
        );
    }


    /*
     * =====================================
     * UPDATE USER
     * =====================================
     */

    public UserResponse updateUser(

            Long id,

            UserUpdateRequest request

    ) {

        User currentUser =
                validateAdmin();


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User not found"
                                        )
                        );


        /*
         * Prevent administrator from
         * modifying their own account.
         */

        if (
                user.getId()
                        .equals(
                                currentUser.getId()
                        )
        ) {

            throw new AccessDeniedException(
                    "You cannot modify your own administrator account."
            );
        }


        user.setName(
                request.getName()
        );


        try {

            user.setRole(
                    Role.valueOf(
                            request
                                    .getRole()
                                    .toUpperCase()
                    )
            );

        } catch (
                IllegalArgumentException exception
        ) {

            throw new IllegalArgumentException(
                    "Invalid user role"
            );
        }


        user.setActive(
                request.getActive()
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
     * DELETE USER
     * =====================================
     */

    public void deleteUser(

            Long id

    ) {

        User currentUser =
                validateAdmin();


        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User not found"
                                        )
                        );


        /*
         * Prevent administrator from
         * deleting themselves.
         */

        if (
                user.getId()
                        .equals(
                                currentUser.getId()
                        )
        ) {

            throw new AccessDeniedException(
                    "You cannot delete your own administrator account."
            );
        }


        userRepository.delete(
                user
        );
    }


    /*
     * =====================================
     * VALIDATE ADMIN
     * =====================================
     */

    private User validateAdmin() {

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
                        authentication.getPrincipal()
                )
        ) {

            throw new AccessDeniedException(
                    "User is not authenticated"
            );
        }


        String email =
                authentication.getName();


        User currentUser =
                userRepository
                        .findByEmail(
                                email
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User not found"
                                        )
                        );


        if (
                currentUser.getRole()
                        != Role.ADMIN
        ) {

            throw new AccessDeniedException(
                    "Only administrators can manage users"
            );
        }


        return currentUser;
    }


    /*
     * =====================================
     * MAP ENTITY TO RESPONSE
     * =====================================
     */

    private UserResponse mapToResponse(

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