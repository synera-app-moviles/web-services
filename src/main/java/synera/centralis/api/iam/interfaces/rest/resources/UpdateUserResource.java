package synera.centralis.api.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Update user resource
 */
@Schema(description = "Request to update user information")
public record UpdateUserResource(
    @Schema(description = "First name", example = "John", required = true)
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    String name,
    
    @Schema(description = "Last name", example = "Doe", required = true)
    @NotBlank(message = "Lastname cannot be blank")
    @Size(max = 100, message = "Lastname must not exceed 100 characters")
    String lastname,
    
    @Schema(description = "Email address", example = "john.doe@company.com", required = true)
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    String email
) {
}
