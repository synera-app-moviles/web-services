package synera.centralis.api.iam.domain.model.queries;

import java.util.UUID;

/**
 * Get all users by department query
 */
public record GetAllUsersByDepartmentQuery(UUID departmentId) {
    public GetAllUsersByDepartmentQuery {
        if (departmentId == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
    }
}
