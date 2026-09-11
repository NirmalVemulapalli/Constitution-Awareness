package com.constitution.awareness.controller;


import com.constitution.awareness.dto.ChangePasswordRequest;
import com.constitution.awareness.dto.ProfileUpdateRequest;
import com.constitution.awareness.dto.UserResponse;

import com.constitution.awareness.service.ProfileService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {


    private final ProfileService
            profileService;


    public ProfileController(

            ProfileService
                    profileService

    ) {

        this.profileService =
                profileService;

    }


    /*
     * =====================================
     * GET CURRENT PROFILE
     * =====================================
     */

    @GetMapping
    public ResponseEntity<UserResponse>
    getProfile() {

        return ResponseEntity.ok(

                profileService
                        .getProfile()
        );

    }


    /*
     * =====================================
     * UPDATE PROFILE
     * =====================================
     */

    @PutMapping
    public ResponseEntity<UserResponse>
    updateProfile(

            @Valid
            @RequestBody
            ProfileUpdateRequest request

    ) {

        return ResponseEntity.ok(

                profileService
                        .updateProfile(
                                request
                        )
        );

    }


    /*
     * =====================================
     * CHANGE PASSWORD
     * =====================================
     */

    @PutMapping("/change-password")
    public ResponseEntity<Void>
    changePassword(

            @Valid
            @RequestBody
            ChangePasswordRequest request

    ) {

        profileService
                .changePassword(
                        request
                );


        return ResponseEntity
                .noContent()
                .build();

    }

}