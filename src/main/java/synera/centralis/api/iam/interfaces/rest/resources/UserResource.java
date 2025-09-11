package synera.centralis.api.iam.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User resource
 */
@Schema(description = "User information")
public record UserResource(
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id,
    
    @Schema(description = "Username", example = "john.doe")
    String username,
    
    @Schema(description = "First name", example = "John")
    String name,
    
    @Schema(description = "Last name", example = "Doe")
    String lastname,
    
    @Schema(description = "Email address", example = "john.doe@company.com")
    String email,
    
    @Schema(description = "Department ID", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID departmentId,
    
    @Schema(description = "User roles")
    List<String> roles,
    
    @Schema(description = "Creation date", example = "2023-09-11T10:00:00Z")
    String createdAt,
    
    @Schema(description = "Last update date", example = "2023-09-11T10:00:00Z")
    String updatedAt
) {
}
