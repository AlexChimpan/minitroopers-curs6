package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.persistence.inMemory.MaintenanceTaskEntity;

/**
 * Maps between {@link MaintenanceTask} domain objects and {@link MaintenanceTaskEntity} persistence entities\.
 */
public interface MaintenanceTaskMapper<T> {
    /**
     * Converts a persistence entity to a domain object\.
     *
     * @param entity the persistence entity
     * @return the domain object
     */
    MaintenanceTask toDomain(T entity);

    /**
     * Converts a domain object to a persistence entity\.
     *
     * @param task the domain object
     * @return the persistence entity
     */
    T toEntity(MaintenanceTask task);
}
