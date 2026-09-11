package com.constitution.awareness.controller;


import com.constitution.awareness.dto.UserResponse;
import com.constitution.awareness.dto.UserUpdateRequest;

import com.constitution.awareness.service.UserManagementService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/users")
public class UserManagementController {


    private final UserManagementService
            userManagementService;


    public UserManagementController(

            UserManagementService
                    userManagementService

    ) {

        this.userManagementService =
                userManagementService;
    }


    /*
     * =====================================
     * GET ALL USERS
     * =====================================
     */

    @GetMapping
    public ResponseEntity<Page<UserResponse>>
    getUsers(

            Pageable pageable

    ) {

        return ResponseEntity.ok(

                userManagementService
                        .getUsers(
                                pageable
                        )
        );
    }


    /*
     * =====================================
     * SEARCH USERS
     * =====================================
     */

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>>
    searchUsers(

            @RequestParam String keyword,

            Pageable pageable

    ) {

        return ResponseEntity.ok(

                userManagementService
                        .searchUsers(

                                keyword,

                                pageable
                        )
        );
    }


    /*
     * =====================================
     * GET SINGLE USER
     * =====================================
     */

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>
    getUser(

            @PathVariable Long id

    ) {

        return ResponseEntity.ok(

                userManagementService
                        .getUser(id)
        );
    }


    /*
     * =====================================
     * UPDATE USER
     * =====================================
     */

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse>
    updateUser(

            @PathVariable Long id,

            @Valid
            @RequestBody
            UserUpdateRequest request

    ) {

        return ResponseEntity.ok(

                userManagementService
                        .updateUser(

                                id,

                                request
                        )
        );
    }


    /*
     * =====================================
     * DELETE USER
     * =====================================
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteUser(

            @PathVariable Long id

    ) {

        userManagementService
                .deleteUser(id);


        return ResponseEntity
                .noContent()
                .build();
    }
}