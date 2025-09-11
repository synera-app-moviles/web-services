package synera.centralis.api.iam.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synera.centralis.api.iam.domain.model.aggregates.Department;
import synera.centralis.api.iam.domain.model.commands.CreateDepartmentCommand;
import synera.centralis.api.iam.domain.model.commands.DeleteDepartmentCommand;
import synera.centralis.api.iam.domain.model.commands.UpdateDepartmentCommand;
import synera.centralis.api.iam.domain.services.DepartmentCommandService;
import synera.centralis.api.iam.infrastructure.persistence.jpa.repositories.DepartmentRepository;

/**
 * Department command service implementation
 * <p>
 *     This service provides the command operations for the Department aggregate.
 * </p>
 */
@Service
public class DepartmentCommandServiceImpl implements DepartmentCommandService {

    private final DepartmentRepository departmentRepository;

    public DepartmentCommandServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Optional<Department> handle(CreateDepartmentCommand command) {
        // Check if department with the same name already exists
        if (departmentRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Department with name " + command.name() + " already exists");
        }

        var department = new Department(command.name(), command.description());
        var savedDepartment = departmentRepository.save(department);
        return Optional.of(savedDepartment);
    }

    @Override
    @Transactional
    public Optional<Department> handle(UpdateDepartmentCommand command) {
        var departmentOptional = departmentRepository.findById(command.departmentId());
        
        if (departmentOptional.isEmpty()) {
            throw new IllegalArgumentException("Department with id " + command.departmentId() + " not found");
        }

        // Check if another department with the same name exists
        if (departmentRepository.existsByNameAndIdNot(command.name(), command.departmentId())) {
            throw new IllegalArgumentException("Department with name " + command.name() + " already exists");
        }

        var department = departmentOptional.get();
        department.updateInformation(command.name(), command.description());
        var savedDepartment = departmentRepository.save(department);
        return Optional.of(savedDepartment);
    }

    @Override
    @Transactional
    public void handle(DeleteDepartmentCommand command) {
        var departmentOptional = departmentRepository.findById(command.departmentId());
        
        if (departmentOptional.isEmpty()) {
            throw new IllegalArgumentException("Department with id " + command.departmentId() + " not found");
        }

        departmentRepository.deleteById(command.departmentId());
    }
}
