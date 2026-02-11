package com.bmw.maintenance.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MaintenanceTaskRepositoryInH2Database implements PanacheRepository<MaintenanceTaskEntity> {

}
