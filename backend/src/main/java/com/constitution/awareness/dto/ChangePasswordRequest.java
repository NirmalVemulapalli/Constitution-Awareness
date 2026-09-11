package com.constitution.awareness.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class ChangePasswordRequest {


    /*
     * =====================================
     * CURRENT PASSWORD
     * =====================================
     */

    @NotBlank(
            message = "Current password is required"
    )
    private String currentPassword;


    /*
     * =====================================
     * NEW PASSWORD
     * =====================================
     */

    @NotBlank(
            message = "New password is required"
    )
    @Size(
            min = 6,
            max = 100,
            message =
                    "New password must be between 6 and 100 characters"
    )
    private String newPassword;


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public String getCurrentPassword() {

        return currentPassword;

    }


    public String getNewPassword() {

        return newPassword;

    }


    /*
     * =====================================
     * SETTERS
     * =====================================
     */

    public void setCurrentPassword(
            String currentPassword
    ) {

        this.currentPassword =
                currentPassword;

    }


    public void setNewPassword(
            String newPassword
    ) {

        this.newPassword =
                newPassword;

    }

}