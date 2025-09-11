package synera.centralis.api.iam.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import synera.centralis.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Department aggregate root
 * This class represents the aggregate root for the Department entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class Department extends AuditableAbstractAggregateRoot<Department> {

    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @Size(max = 255)
    private String description;

    public Department() {
    }

    public Department(String name) {
        this();
        this.name = name;
    }

    public Department(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    /**
     * Update department information
     * @param name the new name
     * @param description the new description
     * @return the updated department
     */
    public Department updateInformation(String name, String description) {
        this.name = name;
        this.description = description;
        return this;
    }

    /**
     * Update department name
     * @param name the new name
     * @return the updated department
     */
    public Department updateName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Update department description
     * @param description the new description
     * @return the updated department
     */
    public Department updateDescription(String description) {
        this.description = description;
        return this;
    }
}
