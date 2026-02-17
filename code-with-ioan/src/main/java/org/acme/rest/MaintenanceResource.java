package org.acme.rest;

import java.util.List;

import org.acme.domain.MaintenanceTask;
import org.acme.domain.TaskStatus;
import org.acme.service.MaintenanceService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Maintenance Tasks", description = "Operations for managing vehicle maintenance tasks")
public class MaintenanceResource {

    @Inject
    MaintenanceService maintenanceService;

    @POST
    @Transactional
    @Operation(summary = "Create a new maintenance task", description = "Creates a new maintenance task for a vehicle")
    @APIResponse(responseCode = "201", description = "Task created successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createTask(MaintenanceTask task) {
        if (task == null || task.getVin() == null || task.getType() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("VIN and type are required")
                    .build();
        }
        
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        
        MaintenanceTask created = maintenanceService.create(task);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    @Operation(summary = "Update task status", description = "Updates the status of an existing maintenance task")
    @APIResponse(responseCode = "200", description = "Status updated successfully")
    @APIResponse(responseCode = "404", description = "Task not found")
    public Response updateStatus(
            @Parameter(description = "Task ID") @PathParam("id") Long id,
            @Parameter(description = "New status") TaskStatus newStatus) {
        MaintenanceTask updated = maintenanceService.updateStatus(id, newStatus);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @PUT
    @Path("/{id}/notes")
    @Transactional
    @Operation(summary = "Add or update notes", description = "Adds or updates notes for a maintenance task")
    @APIResponse(responseCode = "200", description = "Notes updated successfully")
    @APIResponse(responseCode = "404", description = "Task not found")
    public Response upsertNotes(
            @Parameter(description = "Task ID") @PathParam("id") Long id,
            @Parameter(description = "Notes content") String notes) {
        MaintenanceTask updated = maintenanceService.upsertNotes(id, notes);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get task by ID", description = "Returns the details of a specific maintenance task")
    @APIResponse(responseCode = "200", description = "Task found")
    @APIResponse(responseCode = "404", description = "Task not found")
    public Response getTaskById(@Parameter(description = "Task ID") @PathParam("id") Long id) {
        MaintenanceTask task = MaintenanceTask.findById(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(task).build();
    }

    @GET
    @Operation(summary = "List all tasks", description = "Returns all maintenance tasks")
    @APIResponse(responseCode = "200", description = "Tasks retrieved successfully")
    public Response getAllTasks() {
        List<MaintenanceTask> tasks = maintenanceService.findAll();
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/vin/{vin}")
    @Operation(summary = "List tasks by VIN", description = "Returns all maintenance tasks for a specific vehicle")
    @APIResponse(responseCode = "200", description = "Tasks retrieved successfully")
    public Response getTasksByVin(@Parameter(description = "Vehicle Identification Number") @PathParam("vin") String vin) {
        List<MaintenanceTask> tasks = maintenanceService.findByVin(vin);
        return Response.ok(tasks).build();
    }
}
