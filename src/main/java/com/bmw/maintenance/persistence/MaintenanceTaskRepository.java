package com.bmw.maintenance.persistence;


import com.bmw.maintenance.domain.MaintenanceTask;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceTaskRepository {

    @Inject
    MaintenanceTaskPanacheRepository panacheRepository;

    @Transactional
    public void save(MaintenanceTask task) {
        MaintenanceTaskEntity2 entity = new MaintenanceTaskEntity2();
        entity.vin = task.getVin();
        entity.type = task.getType();
        entity.status = task.getStatus();
        entity.notes = task.getNotes();
        panacheRepository.persist(entity);
    }

    public List<MaintenanceTask> findAll() {
        return panacheRepository.listAll().stream()
                .map(e -> MaintenanceTask.reconstitute(e.id, e.vin, e.type, e.status, e.notes))
                .collect(Collectors.toList());
    }
}

