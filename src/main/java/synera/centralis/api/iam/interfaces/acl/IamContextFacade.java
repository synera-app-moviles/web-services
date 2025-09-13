package synera.centralis.api.iam.interfaces.acl;

import synera.centralis.api.iam.domain.model.commands.SignUpCommand;
import synera.centralis.api.iam.domain.model.entities.Role;
import synera.centralis.api.iam.domain.model.queries.GetUserByIdQuery;
import synera.centralis.api.iam.domain.model.queries.GetUserByUsernameQuery;
import synera.centralis.api.iam.domain.services.UserCommandService;
import synera.centralis.api.iam.domain.services.UserQueryService;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */
public class IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacade(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Creates a user with the given username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The id of the created user.
     */
    public UUID createUser(String username, String password) {
        // Generate default values for new required fields
        var defaultName = username; // Use username as default name
        var defaultLastname = "User"; // Default lastname
        var defaultEmail = username + "@default.com"; // Generate default email
        
        var signUpCommand = new SignUpCommand(
            username, 
            password, 
            defaultName, 
            defaultLastname, 
            defaultEmail, 
            List.of(Role.getDefaultRole())
        );
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    /**
     * Creates a user with the given username, password and roles.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param roleNames The names of the roles of the user. When a role does not exist, it is ignored.
     * @return The id of the created user.
     */
    public UUID createUser(String username, String password, List<String> roleNames) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        
        // Generate default values for new required fields
        var defaultName = username; // Use username as default name
        var defaultLastname = "User"; // Default lastname
        var defaultEmail = username + "@default.com"; // Generate default email
        
        var signUpCommand = new SignUpCommand(
            username, 
            password, 
            defaultName, 
            defaultLastname, 
            defaultEmail, 
            roles
        );
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    /**
     * Fetches the id of the user with the given username.
     * @param username The username of the user.
     * @return The id of the user.
     */
    public UUID fetchUserIdByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var result = userQueryService.handle(getUserByUsernameQuery);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    /**
     * Fetches the username of the user with the given id.
     * @param userId The id of the user.
     * @return The username of the user.
     */
    public String fetchUsernameByUserId(UUID userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getUsername();
    }

}
