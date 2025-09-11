package synera.centralis.api.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Create department resource
 */
@Schema(description = "Request to create a new department")
public record CreateDepartmentResource(
    @Schema(description = "Department name", example = "Human Resources", required = true)
    @NotBlank(message = "Department name cannot be blank")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    String name,
    
    @Schema(description = "Department description", example = "Manages employee relations and policies")
    @Size(max = 255, message = "Department description must not exceed 255 characters")
    String description
) {
}
