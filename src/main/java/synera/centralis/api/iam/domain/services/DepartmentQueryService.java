package synera.centralis.api.iam.domain.services;

import java.util.List;
import java.util.Optional;

import synera.centralis.api.iam.domain.model.aggregates.Department;
import synera.centralis.api.iam.domain.model.aggregates.User;
import synera.centralis.api.iam.domain.model.queries.GetAllDepartmentsQuery;
import synera.centralis.api.iam.domain.model.queries.GetAllUsersByDepartmentQuery;
import synera.centralis.api.iam.domain.model.queries.GetDepartmentByIdQuery;

/**
 * Department query service
 * <p>
 *     This service provides the query operations for the Department aggregate.
 * </p>
 */
public interface DepartmentQueryService {
    
    /**
     * Handle get all departments query
     * @param query the get all departments query
     * @return the list of departments
     */
    List<Department> handle(GetAllDepartmentsQuery query);
    
    /**
     * Handle get department by id query
     * @param query the get department by id query
     * @return the department
     */
    Optional<Department> handle(GetDepartmentByIdQuery query);
    
    /**
     * Handle get all users by department query
     * @param query the get all users by department query
     * @return the list of users
     */
    List<User> handle(GetAllUsersByDepartmentQuery query);
}
