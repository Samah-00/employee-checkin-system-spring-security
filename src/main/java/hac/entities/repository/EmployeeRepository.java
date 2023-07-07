/**
 Repository interface for managing Employee entities.
 */
package hac.entities.repository;

import hac.entities.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     Retrieves an Employee by their email address.
     @param email the email address of the Employee
     @return the Employee with the specified email address, or null if not found
     */
    Employee findByEmail(String email);
}
