package synera.centralis.api.iam.domain.model.queries;

import java.util.UUID;

/**
 * Get department by id query
 */
public record GetDepartmentByIdQuery(UUID departmentId) {
    public GetDepartmentByIdQuery {
        if (departmentId == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
    }
}
