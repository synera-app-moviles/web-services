package synera.centralis.api.iam.domain.services;

import java.util.Optional;

import synera.centralis.api.iam.domain.model.aggregates.Department;
import synera.centralis.api.iam.domain.model.commands.CreateDepartmentCommand;
import synera.centralis.api.iam.domain.model.commands.DeleteDepartmentCommand;
import synera.centralis.api.iam.domain.model.commands.UpdateDepartmentCommand;

/**
 * Department command service
 * <p>
 *     This service provides the command operations for the Department aggregate.
 * </p>
 */
public interface DepartmentCommandService {
    
    /**
     * Handle create department command
     * @param command the create department command
     * @return the created department
     */
    Optional<Department> handle(CreateDepartmentCommand command);
    
    /**
     * Handle update department command
     * @param command the update department command
     * @return the updated department
     */
    Optional<Department> handle(UpdateDepartmentCommand command);
    
    /**
     * Handle delete department command
     * @param command the delete department command
     */
    void handle(DeleteDepartmentCommand command);
}
