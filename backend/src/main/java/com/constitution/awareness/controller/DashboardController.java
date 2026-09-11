package com.constitution.awareness.controller;

import com.constitution.awareness.dto.DashboardStatsResponse;

import com.constitution.awareness.service.DashboardService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(
        origins = "http://localhost:5173"
)
public class DashboardController {

    private final DashboardService
            dashboardService;


    public DashboardController(

            DashboardService
                    dashboardService

    ) {

        this.dashboardService =
                dashboardService;
    }


    @GetMapping("/stats")
    public ResponseEntity<
            DashboardStatsResponse
            > getStats() {

        return ResponseEntity.ok(

                dashboardService.getStats()
        );
    }
}