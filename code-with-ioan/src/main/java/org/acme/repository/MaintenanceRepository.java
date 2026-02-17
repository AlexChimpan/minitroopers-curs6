package org.acme.repository;

import java.util.List;

import org.acme.domain.MaintenanceTask;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MaintenanceRepository implements PanacheRepository<MaintenanceTask> {
    
    public MaintenanceTask findById(Long id) {
        return find("id", id).firstResult();
    }

    public List<MaintenanceTask> listAllTasks() {
        return listAll();
    }
}
