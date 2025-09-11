package synera.centralis.api.iam.domain.model.commands;

import java.util.UUID;

/**
 * Delete department command
 */
public record DeleteDepartmentCommand(UUID departmentId) {
    public DeleteDepartmentCommand {
        if (departmentId == null) {
            throw new IllegalArgumentException("Department ID cannot be null");
        }
    }
}
