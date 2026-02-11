package com.bmw.maintenance.api;

import com.bmw.maintenance.domain.*;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class MaintenanceTaskResourceSuccessTest {

    @InjectMock
    MaintenanceTaskService service;

    // --------------------------------------------------------------------
    //   1. POST /api/maintenance-tasks/  (Success for each TaskType)
    // --------------------------------------------------------------------

    @Test
    void createTask_oilChange_success() {
        when(service.createTask(anyString(), eq(TaskType.OIL_CHANGE), any(), any()))
                .thenReturn(1L);

        Map<String, Object> body = Map.of(
                "vin", "12345678901234567",
                "type", TaskType.OIL_CHANGE.name(),
                "notes", "notes",
                "additionalData", Map.of("foo", "bar")
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/maintenance-tasks/")
                .then()
                .statusCode(201)
                .body(equalTo("1"));
    }

    @Test
    void createTask_brakeInspection_success() {
        when(service.createTask(anyString(), eq(TaskType.BRAKE_INSPECTION), any(), any()))
                .thenReturn(2L);

        Map<String, Object> body = Map.of(
                "vin", "12345678901234567",
                "type", TaskType.BRAKE_INSPECTION.name(),
                "notes", "notes",
                "additionalData", Map.of()
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/maintenance-tasks/")
                .then()
                .statusCode(201)
                .body(equalTo("2"));
    }

    @Test
    void createTask_tireService_success() {
        when(service.createTask(anyString(), eq(TaskType.TIRE_SERVICE), any(), any()))
                .thenReturn(3L);

        Map<String, Object> body = Map.of(
                "vin", "12345678901234567",
                "type", TaskType.TIRE_SERVICE.name(),
                "notes", "some notes",
                "additionalData", Map.of("x", "y")
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/maintenance-tasks/")
                .then()
                .statusCode(201)
                .body(equalTo("3"));
    }

    @Test
    void createTask_diagnosticScan_success() {
        when(service.createTask(anyString(), eq(TaskType.DIAGNOSTIC_SCAN), any(), any()))
                .thenReturn(4L);

        Map<String, Object> body = Map.of(
                "vin", "12345678901234567",
                "type", TaskType.DIAGNOSTIC_SCAN.name(),
                "notes", "scan notes",
                "additionalData", Map.of("scanMode", "fast")
        );

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/maintenance-tasks/")
                .then()
                .statusCode(201)
                .body(equalTo("4"));
    }

    // --------------------------------------------------------------------
    //   2. PUT /{taskId}/status (Success for each TaskStatus)
    // --------------------------------------------------------------------

    @Test
    void updateStatus_created_success() {
        doNothing().when(service).updateTaskStatus("T1", TaskStatus.CREATED);

        Map<String, Object> body = Map.of("status", TaskStatus.CREATED.name());

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/api/maintenance-tasks/T1/status")
                .then()
                .statusCode(204);
    }

    @Test
    void updateStatus_inProgress_success() {
        doNothing().when(service).updateTaskStatus("T1", TaskStatus.IN_PROGRESS);

        Map<String, Object> body = Map.of("status", TaskStatus.IN_PROGRESS.name());

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/api/maintenance-tasks/T1/status")
                .then()
                .statusCode(204);
    }

    @Test
    void updateStatus_completed_success() {
        doNothing().when(service).updateTaskStatus("T1", TaskStatus.COMPLETED);

        Map<String, Object> body = Map.of("status", TaskStatus.COMPLETED.name());

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/api/maintenance-tasks/T1/status")
                .then()
                .statusCode(204);
    }

    // --------------------------------------------------------------------
    //   3. PUT /{taskId}/notes (Success)
    // --------------------------------------------------------------------

    @Test
    void updateNotes_success() {
        doNothing().when(service).addOrUpdateNotes("T1", "Updated notes");

        Map<String, Object> body = Map.of("notes", "Updated notes");

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .put("/api/maintenance-tasks/T1/notes")
                .then()
                .statusCode(204);
    }

    // --------------------------------------------------------------------
    //   4. GET /{taskId} (Full MaintenanceTask JSON)
    // --------------------------------------------------------------------

    @Test
    void getTaskById_success() {
        MaintenanceTask task = MaintenanceTask.reconstitute(
                1L,
                "VIN12345678901234",
                TaskType.OIL_CHANGE,
                TaskStatus.IN_PROGRESS,
                "Some notes",
                TirePosition.FRONT_LEFT,
                new String[]{"E001", "E002"},
                ScannerType.ADVANCED
        );

        when(service.getTaskById("T1")).thenReturn(task);

        given()
                .when()
                .get("/api/maintenance-tasks/T1")
                .then()
                .statusCode(200)
                .body("taskId", equalTo(1))
                .body("vin", equalTo("VIN12345678901234"))
                .body("type", equalTo("OIL_CHANGE"))
                .body("status", equalTo("IN_PROGRESS"))
                .body("notes", equalTo("Some notes"))
                .body("tirePosition", equalTo("FRONT_LEFT"))
                .body("errorCodes", hasItems("E001", "E002"))
                .body("scannerType", equalTo("ADVANCED"));
    }

    // --------------------------------------------------------------------
    //   5. GET /api/maintenance-tasks (List+Filter)
    // --------------------------------------------------------------------

    @Test
    void listTasks_success() {
        MaintenanceTask t1 = MaintenanceTask.reconstitute(
                1L,
                "VIN11111111111111",
                TaskType.OIL_CHANGE,
                TaskStatus.CREATED,
                "note1",
                TirePosition.REAR_LEFT,
                new String[]{"X1"},
                ScannerType.BASIC
        );

        MaintenanceTask t2 = MaintenanceTask.reconstitute(
                2L,
                "VIN22222222222222",
                TaskType.BRAKE_INSPECTION,
                TaskStatus.IN_PROGRESS,
                "note2",
                TirePosition.FRONT_RIGHT,
                new String[]{"X2", "X3"},
                ScannerType.ADVANCED
        );

        when(service.listTasks(null)).thenReturn(List.of(t1, t2));

        given()
                .when()
                .get("/api/maintenance-tasks")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void listTasks_filteredByVin_success() {
        MaintenanceTask t1 = MaintenanceTask.reconstitute(
                3L,
                "VIN99999999999999",
                TaskType.DIAGNOSTIC_SCAN,
                TaskStatus.COMPLETED,
                "finished",
                TirePosition.ALL,
                new String[]{"A1", "B2"},
                ScannerType.ADVANCED
        );

        when(service.listTasks("VIN99999999999999")).thenReturn(List.of(t1));

        given()
                .when()
                .get("/api/maintenance-tasks?vin=VIN99999999999999")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].vin", equalTo("VIN99999999999999"))
                .body("[0].type", equalTo("DIAGNOSTIC_SCAN"))
                .body("[0].status", equalTo("COMPLETED"));
    }
}
