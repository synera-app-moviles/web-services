package synera.centralis.api.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synera.centralis.api.iam.domain.model.commands.CreateDepartmentCommand;
import synera.centralis.api.iam.domain.model.commands.DeleteDepartmentCommand;
import synera.centralis.api.iam.domain.model.commands.UpdateDepartmentCommand;
import synera.centralis.api.iam.domain.model.queries.GetAllDepartmentsQuery;
import synera.centralis.api.iam.domain.model.queries.GetAllUsersByDepartmentQuery;
import synera.centralis.api.iam.domain.model.queries.GetDepartmentByIdQuery;
import synera.centralis.api.iam.domain.services.DepartmentCommandService;
import synera.centralis.api.iam.domain.services.DepartmentQueryService;
import synera.centralis.api.iam.interfaces.rest.resources.CreateDepartmentResource;
import synera.centralis.api.iam.interfaces.rest.resources.DepartmentResource;
import synera.centralis.api.iam.interfaces.rest.resources.UpdateDepartmentResource;
import synera.centralis.api.iam.interfaces.rest.resources.UserResource;

import java.util.List;
import java.util.UUID;

/**
 * Department controller
 * <p>
 *     This controller provides the REST API operations for the Department aggregate.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/departments")
@Tag(name = "Departments", description = "Department management operations")
public class DepartmentController {

    private final DepartmentCommandService departmentCommandService;
    private final DepartmentQueryService departmentQueryService;

    public DepartmentController(DepartmentCommandService departmentCommandService, 
                               DepartmentQueryService departmentQueryService) {
        this.departmentCommandService = departmentCommandService;
        this.departmentQueryService = departmentQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new department", description = "Creates a new department in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Department created successfully",
                content = @Content(schema = @Schema(implementation = DepartmentResource.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Department with the same name already exists")
    })
    public ResponseEntity<DepartmentResource> createDepartment(@Valid @RequestBody CreateDepartmentResource resource) {
        var command = new CreateDepartmentCommand(resource.name(), resource.description());
        var department = departmentCommandService.handle(command);
        
        if (department.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        var departmentResource = new DepartmentResource(
            department.get().getId(),
            department.get().getName(),
            department.get().getDescription(),
            department.get().getCreatedAt().toString(),
            department.get().getUpdatedAt().toString()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentResource);
    }

    @GetMapping
    @Operation(summary = "Get all departments", description = "Retrieves a list of all departments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Departments retrieved successfully",
                content = @Content(schema = @Schema(implementation = DepartmentResource.class)))
    })
    public ResponseEntity<List<DepartmentResource>> getAllDepartments() {
        var query = new GetAllDepartmentsQuery();
        var departments = departmentQueryService.handle(query);
        
        var departmentResources = departments.stream()
            .map(department -> new DepartmentResource(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.getCreatedAt().toString(),
                department.getUpdatedAt().toString()
            ))
            .toList();
        
        return ResponseEntity.ok(departmentResources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Retrieves a specific department by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Department found",
                content = @Content(schema = @Schema(implementation = DepartmentResource.class))),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<DepartmentResource> getDepartmentById(
            @Parameter(description = "Department ID", required = true)
            @PathVariable UUID id) {
        var query = new GetDepartmentByIdQuery(id);
        var department = departmentQueryService.handle(query);
        
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var departmentResource = new DepartmentResource(
            department.get().getId(),
            department.get().getName(),
            department.get().getDescription(),
            department.get().getCreatedAt().toString(),
            department.get().getUpdatedAt().toString()
        );
        
        return ResponseEntity.ok(departmentResource);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update department", description = "Updates an existing department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Department updated successfully",
                content = @Content(schema = @Schema(implementation = DepartmentResource.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "409", description = "Department with the same name already exists")
    })
    public ResponseEntity<DepartmentResource> updateDepartment(
            @Parameter(description = "Department ID", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDepartmentResource resource) {
        var command = new UpdateDepartmentCommand(id, resource.name(), resource.description());
        var department = departmentCommandService.handle(command);
        
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var departmentResource = new DepartmentResource(
            department.get().getId(),
            department.get().getName(),
            department.get().getDescription(),
            department.get().getCreatedAt().toString(),
            department.get().getUpdatedAt().toString()
        );
        
        return ResponseEntity.ok(departmentResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete department", description = "Deletes an existing department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<Void> deleteDepartment(
            @Parameter(description = "Department ID", required = true)
            @PathVariable UUID id) {
        var command = new DeleteDepartmentCommand(id);
        departmentCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/users")
    @Operation(summary = "Get users by department", description = "Retrieves all users belonging to a specific department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                content = @Content(schema = @Schema(implementation = UserResource.class))),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<List<UserResource>> getUsersByDepartment(
            @Parameter(description = "Department ID", required = true)
            @PathVariable UUID id) {
        var query = new GetAllUsersByDepartmentQuery(id);
        var users = departmentQueryService.handle(query);
        
        var userResources = users.stream()
            .map(user -> new UserResource(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getDepartmentId(),
                user.getRoles().stream().map(role -> role.getStringName()).toList(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
            ))
            .toList();
        
        return ResponseEntity.ok(userResources);
    }
}
