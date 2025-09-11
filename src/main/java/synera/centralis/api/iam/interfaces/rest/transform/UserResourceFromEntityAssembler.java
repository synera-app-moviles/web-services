package synera.centralis.api.iam.interfaces.rest.transform;

import synera.centralis.api.iam.domain.model.aggregates.User;
import synera.centralis.api.iam.domain.model.entities.Role;
import synera.centralis.api.iam.interfaces.rest.resources.UserResource;

/**
 * Assembler to convert User entity to UserResource
 */
public class UserResourceFromEntityAssembler {
    
    /**
     * Convert User entity to UserResource
     * @param user the user entity
     * @return the user resource
     */
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        
        return new UserResource(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getLastname(),
            user.getEmail(),
            user.getDepartmentId(),
            roles,
            user.getCreatedAt() != null ? user.getCreatedAt().toString() : null,
            user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null
        );
    }
}
