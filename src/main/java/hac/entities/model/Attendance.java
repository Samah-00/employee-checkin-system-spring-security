/**
 Represents an attendance record for an employee.
 */
package hac.entities.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDateTime clockInTime;

    private LocalDateTime clockOutTime;

    private LocalDate date;

    /**
     Default constructor.
     */
    public Attendance() {
    }

    /**
     Constructs an Attendance object with the specified details.
     @param employee the Employee associated with the attendance
     @param clockInTime the date and time of clock-in
     @param clockOutTime the date and time of clock-out
     @param date the date of the attendance record
     */
    public Attendance(Employee employee, LocalDateTime clockInTime, LocalDateTime clockOutTime, LocalDate date) {
        this.employee = employee;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.date = date;
    }


    public Attendance(Optional<Employee> employee, LocalDateTime currentDateTime, Object clockOutTime, LocalDate currentDate) {
    }

    // Getters and setters for all fields
    /**
     Retrieves the ID of the attendance record.
     @return the ID of the attendance record
     */
    public Long getId() {
        return id;
    }

    /**
     Sets the ID of the attendance record.
     @param id the ID of the attendance record
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     Retrieves the Employee associated with the attendance record.
     @return the Employee associated with the attendance record
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     Sets the Employee associated with the attendance record.
     @param employee the Employee associated with the attendance record
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     Retrieves the date and time of clock-in.
     @return the date and time of clock-in
     */
    public LocalDateTime getClockInTime() {
        return clockInTime;
    }

    /**
     Sets the date and time of clock-in.
     @param clockInTime the date and time of clock-in
     */
    public void setClockInTime(LocalDateTime clockInTime) {
        this.clockInTime = clockInTime;
    }

    /**
     Retrieves the date and time of clock-out.
     @return the date and time of clock-out
     */
    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }

    /**
     Sets the date and time of clock-out.
     @param clockOutTime the date and time of clock-out
     */
    public void setClockOutTime(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    /**
     Retrieves the date of the attendance record.
     @return the date of the attendance record
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     Sets the date of the attendance record.
     @param date the date of the attendance record
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

}