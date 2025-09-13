package synera.centralis.api.iam.domain.model.commands;

import java.util.List;

import synera.centralis.api.iam.domain.model.entities.Role;

/**
 * Sign up command
 * <p>
 *     This class represents the command to sign up a user.
 * </p>
 * @param username the username of the user
 * @param password the password of the user
 * @param name the first name of the user
 * @param lastname the last name of the user
 * @param email the email of the user
 * @param roles the roles of the user
 */
public record SignUpCommand(
    String username, 
    String password, 
    String name, 
    String lastname, 
    String email, 
    List<Role> roles
) {
    public SignUpCommand {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
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
    }
}
