package synera.centralis.api.iam.domain.model.commands;

import java.util.UUID;

/**
 * Update user command
 */
public record UpdateUserCommand(
    UUID userId,
    String name,
    String lastname,
    String email
) {
    public UpdateUserCommand {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name must not exceed 100 characters");
        }
        if (lastname.length() > 100) {
            throw new IllegalArgumentException("Lastname must not exceed 100 characters");
        }
        if (email.length() > 150) {
            throw new IllegalArgumentException("Email must not exceed 150 characters");
        }
    }
}
