package com.bmw.maintenance.persistence;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MaintenanceTaskJpaRepository implements PanacheRepository<MaintenanceTaskJpaEntity> {

    public List<MaintenanceTaskJpaEntity> findByVin(String vin) {
        return find("vin", vin).list();
    }
}
