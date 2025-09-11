package synera.centralis.api.iam.domain.model.commands;

import java.util.UUID;

/**
 * Update department command
 */
public record UpdateDepartmentCommand(UUID departmentId, String name, String description) {
    public UpdateDepartmentCommand {
        if (departmentId == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Department name must not exceed 100 characters");
        }
        if (description != null && description.length() > 255) {
            throw new IllegalArgumentException("Department description must not exceed 255 characters");
        }
    }
}
