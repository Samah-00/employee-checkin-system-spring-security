/**
 Repository interface for managing Attendance entities.
 */
package hac.entities.repository;

import hac.entities.model.Attendance;
import hac.entities.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     Retrieves a list of Attendance records for a specific Employee.
     @param employee the Employee for which to retrieve Attendance records
     @return a list of Attendance records associated with the Employee
     */
    List<Attendance> findByEmployee(Employee employee);

    /**
     Retrieves a list of the latest Attendance records for a specific Employee,
     sorted in descending order by ID.
     @param employee the Employee for which to retrieve the latest Attendance records
     @return a list of the latest Attendance records associated with the Employee
     */
    @Query("SELECT a FROM Attendance a WHERE a.employee = :employee ORDER BY a.id DESC")
    List<Attendance> findLatestByEmployee(Employee employee);

    /**
     Deletes all Attendance records associated with a specific Employee.
     @param employee the Employee for which to delete Attendance records
     */
    void deleteByEmployee(Employee employee);
}

