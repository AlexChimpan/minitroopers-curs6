package com.bmw.maintenance.api;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class MaintenanceTaskResourceExceptionMappingTest {

    @InjectMock
    MaintenanceTaskService service;

    // POST /api/maintenance-tasks/
    @Test
    void createTask_whenServiceThrows_400() {
        doThrow(new IllegalArgumentException("VIN invalid"))
                .when(service)
                .createTask(anyString(), any(), any(), any());

        Map<String, Object> req = Map.of(
                "vin", "12345678901234567",
                "type", TaskType.OIL_CHANGE.name(),
                "notes", "test",
                "additionalData", Map.of()
        );

        given()
                .contentType("application/json")
                .body(req)
                .when()
                .post("/api/maintenance-tasks/")
                .then()
                .statusCode(400)
                .body("code", equalTo("BAD_REQUEST"))
                .body("message", equalTo("VIN invalid"));
    }

    // PUT /{taskId}/status
    @Test
    void updateStatus_whenServiceThrows_409() {
        doThrow(new IllegalStateException("Already completed"))
                .when(service)
                .updateTaskStatus(eq("T1"), eq(TaskStatus.COMPLETED));

        Map<String, Object> req = Map.of("status", TaskStatus.COMPLETED.name());

        given()
                .contentType("application/json")
                .body(req)
                .when()
                .put("/api/maintenance-tasks/T1/status")
                .then()
                .statusCode(409)
                .body("code", equalTo("CONFLICT"))
                .body("message", equalTo("Already completed"));
    }

    // PUT /{taskId}/notes
    @Test
    void updateNotes_whenNotFound_404() {
        doThrow(new NotFoundException("Task not found"))
                .when(service)
                .addOrUpdateNotes(eq("NF"), anyString());

        Map<String, Object> req = Map.of("notes", "Some notes");

        given()
                .contentType("application/json")
                .body(req)
                .when()
                .put("/api/maintenance-tasks/NF/notes")
                .then()
                .statusCode(404)
                .body("code", equalTo("NOT_FOUND"))
                .body("message", equalTo("Task not found"));
    }

    // GET /{taskId}
    @Test
    void getTask_whenUnknownError_500() {
        doThrow(new RuntimeException("Boom"))
                .when(service)
                .getTaskById(eq("ERR"));

        given()
                .when()
                .get("/api/maintenance-tasks/ERR")
                .then()
                .statusCode(500)
                .body("code", equalTo("INTERNAL_SERVER_ERROR"))
                .body("message", equalTo("An unexpected error occurred"));
    }
}