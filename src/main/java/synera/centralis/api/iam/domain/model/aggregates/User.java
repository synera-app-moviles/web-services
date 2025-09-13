package synera.centralis.api.iam.domain.model.aggregates;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import synera.centralis.api.iam.domain.model.entities.Role;
import synera.centralis.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String lastname;

    @Email
    @NotBlank
    @Size(max = 150)
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, String name, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, String name, String lastname, String email, List<Role> roles) {
        this(username, password, name, lastname, email);
        this.roles = new HashSet<>();
        addRoles(roles);
    }

    /**
     * Add a role to the user
     * @param role the role to add
     * @return the user with the added role
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Add a list of roles to the user
     * @param roles the list of roles to add
     * @return the user with the added roles
     */
    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }

    /**
     * Update user information
     * @param name the new name
     * @param lastname the new lastname
     * @param email the new email
     * @return the user with updated information
     */
    public User updateInformation(String name, String lastname, String email) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        return this;
    }

}
