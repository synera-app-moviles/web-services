package synera.centralis.api.iam.interfaces.rest.resources;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Department resource
 */
@Schema(description = "Department information")
public record DepartmentResource(
    @Schema(description = "Department ID", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id,
    
    @Schema(description = "Department name", example = "Human Resources")
    String name,
    
    @Schema(description = "Department description", example = "Manages employee relations and policies")
    String description,
    
    @Schema(description = "Creation date", example = "2023-09-11T10:00:00Z")
    String createdAt,
    
    @Schema(description = "Last update date", example = "2023-09-11T10:00:00Z")
    String updatedAt
) {
}
