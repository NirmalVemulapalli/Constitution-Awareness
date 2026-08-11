package com.constitution.awareness.controller;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthControllerTest {

    @Test
    void healthCheck_returnsUpStatusAndRunningMessage() {
        HealthController controller = new HealthController();

        Map<String, String> response = controller.healthCheck();

        assertEquals(2, response.size());
        assertEquals("UP", response.get("status"));
        assertEquals("Constitution Awareness API is running", response.get("message"));
    }
}
