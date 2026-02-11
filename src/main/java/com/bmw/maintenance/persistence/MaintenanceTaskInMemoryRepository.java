package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

/**
 * In-memory implementation of {@link MaintenanceTasks} for managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskInMemoryRepository implements MaintenanceTasks {

    private final MaintenanceTaskPanacheRepository repo;
    private final MaintenanceTaskMapper mapper;


    @Inject
    public MaintenanceTaskInMemoryRepository(MaintenanceTaskPanacheRepository repo,MaintenanceTaskMapper mapper) {
        this.repo=repo;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);


        entity.setId(null);

        if (entity.getCreatedAt() == null) entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        repo.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = parseId(taskId);
        MaintenanceTaskEntity entity = repo.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        return mapper.toDomain(entity);
    }

    @Transactional
    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = parseId(taskId);
        MaintenanceTaskEntity entity = repo.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Task not found: "  +  taskId));


        entity.setStatus(newStatus);
        entity.setUpdatedAt(LocalDateTime.now());


        return mapper.toDomain(entity);
    }
    @Transactional
    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = parseId(taskId);
        MaintenanceTaskEntity entity = repo.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));


        entity.setNotes(notes);
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAll() {
        return repo.listAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return repo.find("vin" , vin).list().stream()
                .map(mapper::toDomain)
                .toList();
    }

    private static Long parseId(String taskId){
        try{
            return Long.parseLong(taskId);
        }catch(NumberFormatException e){
            throw new NotFoundException("Task not found: " + taskId);
        }
    }

}
