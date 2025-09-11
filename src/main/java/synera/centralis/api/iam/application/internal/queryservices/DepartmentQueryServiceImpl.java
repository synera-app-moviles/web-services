package synera.centralis.api.iam.application.internal.queryservices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synera.centralis.api.iam.domain.model.aggregates.Department;
import synera.centralis.api.iam.domain.model.aggregates.User;
import synera.centralis.api.iam.domain.model.queries.GetAllDepartmentsQuery;
import synera.centralis.api.iam.domain.model.queries.GetAllUsersByDepartmentQuery;
import synera.centralis.api.iam.domain.model.queries.GetDepartmentByIdQuery;
import synera.centralis.api.iam.domain.services.DepartmentQueryService;
import synera.centralis.api.iam.infrastructure.persistence.jpa.repositories.DepartmentRepository;
import synera.centralis.api.iam.infrastructure.persistence.jpa.repositories.UserRepository;

/**
 * Department query service implementation
 * <p>
 *     This service provides the query operations for the Department aggregate.
 * </p>
 */
@Service
public class DepartmentQueryServiceImpl implements DepartmentQueryService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public DepartmentQueryServiceImpl(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> handle(GetAllDepartmentsQuery query) {
        return departmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Department> handle(GetDepartmentByIdQuery query) {
        return departmentRepository.findById(query.departmentId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> handle(GetAllUsersByDepartmentQuery query) {
        return userRepository.findByDepartmentId(query.departmentId());
    }
}
