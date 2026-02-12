package com.bmw.maintenance.persistence;

import com.bmw.maintenance.commons.serialization.VersionedSchemaSerDes;
import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import com.bmw.maintenance.persistence.mapper.MaintenanceTaskMapper;
import com.bmw.maintenance.persistence.mapper.MaintenanceTaskSchemaVLatest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MaintenanceTaskRepository implements MaintenanceTasks {


    private final MaintenanceTaskMapper mapper;
    private final VersionedSchemaSerDes<String > serializer;
    private final MaintenanceTaskPanacheRepository repository;

    @Inject
    public MaintenanceTaskRepository(MaintenanceTaskMapper mapper, VersionedSchemaSerDes<String> serializer,
                                     MaintenanceTaskPanacheRepository repository) {
        this.mapper = mapper;
        this.serializer = serializer;
        this.repository = repository;
    }

    @Override
    @Transactional
    public MaintenanceTask create(MaintenanceTask task) {
        // map domain to schema
        MaintenanceTaskSchemaVLatest.MaintenanceTask schema = mapper.toSchema(task);

        // serialize schema
        String serialized = serializer.serialize(schema);

        // create entity
        MaintenanceTaskEntity entity = new MaintenanceTaskEntity();
        entity.setAggregate(serialized);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        //presist
        repository.persist(entity);

        //retrun the domain model
        return mapper.toDomain(schema);
    }

    @Override
    @Transactional
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {

        MaintenanceTaskEntity entity = repository.findById(Long.parseLong(taskId));

        if(entity == null){
            throw new NotFoundException("Task " + taskId + "not found ");
        }

        // deserialize aggregate -> schema
        MaintenanceTaskSchemaVLatest.MaintenanceTask schema =
                (MaintenanceTaskSchemaVLatest.MaintenanceTask) serializer.deserialize(entity.getAggregate());

        // update schema
        schema.setStatus(newStatus);

        // serialize
        String serialized = serializer.serialize(schema);
        entity.setAggregate(serialized);
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(schema);
    }

    @Override
    @Transactional
    public MaintenanceTask upsertNotes(String taskId, String notes) {
       MaintenanceTaskEntity entity = repository.findById(Long.parseLong(taskId));

       if(entity == null){
           throw new NotFoundException("Task " + taskId + " not found!");
       }

       // deserialize aggregate -> schema
       MaintenanceTaskSchemaVLatest.MaintenanceTask schema =
               (MaintenanceTaskSchemaVLatest.MaintenanceTask) serializer.deserialize(entity.getAggregate());

       // update schema with new notes
        schema.setNotes(notes);

        // serialize
        String serialized = serializer.serialize(schema);
        entity.setAggregate(serialized);
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(schema);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        MaintenanceTaskEntity entity = repository.findById(Long.parseLong(taskId));

        if(entity == null){
            throw new NotFoundException("Task " + taskId + " not found!");
        }

        // deserialize
        MaintenanceTaskSchemaVLatest.MaintenanceTask schema =
                (MaintenanceTaskSchemaVLatest.MaintenanceTask) serializer.deserialize(entity.getAggregate());


        return mapper.toDomain(schema);
    }

    @Override
    public List<MaintenanceTask> getAllTasks() {
        return repository.streamAll()
                .map(entity -> {
                    MaintenanceTaskSchemaVLatest.MaintenanceTask schema = (MaintenanceTaskSchemaVLatest.MaintenanceTask) serializer.deserialize(entity.getAggregate());
                    return mapper.toDomain(schema);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return repository.streamAll()
                .map(entity -> {
                    MaintenanceTaskSchemaVLatest.MaintenanceTask schema = (MaintenanceTaskSchemaVLatest.MaintenanceTask) serializer.deserialize(entity.getAggregate());
                    return mapper.toDomain(schema);
                })
                .filter(task -> vin.equals(task.getVin()))
                .collect(Collectors.toList());
    }
}
