package synera.centralis.api.iam.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import synera.centralis.api.iam.domain.model.aggregates.Department;

/**
 * Department repository
 * <p>
 *     This repository provides the data access operations for the Department aggregate.
 * </p>
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    
    /**
     * Find department by name
     * @param name the department name
     * @return the department
     */
    Optional<Department> findByName(String name);
    
    /**
     * Check if department exists by name
     * @param name the department name
     * @return true if exists, false otherwise
     */
    boolean existsByName(String name);
    
    /**
     * Check if department exists by name and id not equal
     * @param name the department name
     * @param id the department id to exclude
     * @return true if exists, false otherwise
     */
    boolean existsByNameAndIdNot(String name, UUID id);
}
