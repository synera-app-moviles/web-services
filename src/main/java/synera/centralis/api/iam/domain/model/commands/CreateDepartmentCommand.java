package synera.centralis.api.iam.domain.model.commands;

/**
 * Create department command
 */
public record CreateDepartmentCommand(String name, String description) {
    public CreateDepartmentCommand {
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
