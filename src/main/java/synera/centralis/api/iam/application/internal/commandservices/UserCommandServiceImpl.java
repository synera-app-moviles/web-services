package synera.centralis.api.iam.application.internal.commandservices;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import synera.centralis.api.iam.application.internal.outboundservices.hashing.HashingService;
import synera.centralis.api.iam.application.internal.outboundservices.tokens.TokenService;
import synera.centralis.api.iam.domain.model.aggregates.User;
import synera.centralis.api.iam.domain.model.commands.SignInCommand;
import synera.centralis.api.iam.domain.model.commands.SignUpCommand;
import synera.centralis.api.iam.domain.model.commands.UpdateUserCommand;
import synera.centralis.api.iam.domain.model.queries.GetDepartmentByIdQuery;
import synera.centralis.api.iam.domain.services.DepartmentQueryService;
import synera.centralis.api.iam.domain.services.UserCommandService;
import synera.centralis.api.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import synera.centralis.api.iam.infrastructure.persistence.jpa.repositories.UserRepository;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final DepartmentQueryService departmentQueryService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository, DepartmentQueryService departmentQueryService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.departmentQueryService = departmentQueryService;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Email already exists");
        
        // Validate department exists if departmentId is provided
        if (command.departmentId() != null) {
            var getDepartmentQuery = new GetDepartmentByIdQuery(command.departmentId());
            var department = departmentQueryService.handle(getDepartmentQuery);
            if (department.isEmpty()) {
                throw new RuntimeException("Department with id " + command.departmentId() + " not found");
            }
        }
        
        var roles = command.roles().stream()
            .map(role -> roleRepository.findByName(role.getName())
                .orElseThrow(() -> new RuntimeException("Role name not found")))
            .toList();
            
        var user = new User(
            command.username(), 
            hashingService.encode(command.password()),
            command.name(),
            command.lastname(),
            command.email(),
            command.departmentId(),
            roles
        );
        
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

    /**
     * Handle update user command
     * @param command the update user command containing the user data to update
     * @return the updated user
     */
    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        var userOptional = userRepository.findById(command.userId());
        
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with id " + command.userId() + " not found");
        }

        // Check if email is already taken by another user
        var existingUserWithEmail = userRepository.findByEmail(command.email());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(command.userId())) {
            throw new IllegalArgumentException("Email " + command.email() + " is already taken by another user");
        }

        // Validate department exists if departmentId is provided
        if (command.departmentId() != null) {
            var getDepartmentQuery = new GetDepartmentByIdQuery(command.departmentId());
            var department = departmentQueryService.handle(getDepartmentQuery);
            if (department.isEmpty()) {
                throw new IllegalArgumentException("Department with id " + command.departmentId() + " not found");
            }
        }

        var user = userOptional.get();
        user.updateInformation(command.name(), command.lastname(), command.email());
        user.updateDepartment(command.departmentId());
        
        var savedUser = userRepository.save(user);
        return Optional.of(savedUser);
    }
}
