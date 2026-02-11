package com.bmw.maintenance.api;


import com.bmw.maintenance.commons.GlobalExceptionMapper;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionMapperTest {

    private final GlobalExceptionMapper mapper = new GlobalExceptionMapper();

    @Test
    void mapsIllegalArgumentExceptionTo400() {
        Exception ex = new IllegalArgumentException("Invalid VIN");
        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof GlobalExceptionMapper.ErrorResponse);
        GlobalExceptionMapper.ErrorResponse body = (GlobalExceptionMapper.ErrorResponse) response.getEntity();
        assertEquals("BAD_REQUEST", body.code());
        assertEquals("Invalid VIN", body.message());
    }

    @Test
    void mapsIllegalStateExceptionTo409() {
        Exception ex = new IllegalStateException("Task already completed");
        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        GlobalExceptionMapper.ErrorResponse body = (GlobalExceptionMapper.ErrorResponse) response.getEntity();
        assertEquals("CONFLICT", body.code());
        assertEquals("Task already completed", body.message());
    }

    @Test
    void mapsNotFoundExceptionTo404() {
        Exception ex = new NotFoundException("Task not found");
        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        GlobalExceptionMapper.ErrorResponse body = (GlobalExceptionMapper.ErrorResponse) response.getEntity();
        assertEquals("NOT_FOUND", body.code());
        assertEquals("Task not found", body.message());
    }

    @Test
    void mapsOtherExceptionsTo500() {
        Exception ex = new RuntimeException("Boom");
        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        GlobalExceptionMapper.ErrorResponse body = (GlobalExceptionMapper.ErrorResponse) response.getEntity();
        assertEquals("INTERNAL_SERVER_ERROR", body.code());
        assertEquals("An unexpected error occurred", body.message());
    }
}

