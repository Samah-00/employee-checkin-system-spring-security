/**
 Represents an Employee entity.
 */
package hac.entities.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    // Getters and Setters

    /**
     Retrieves the ID of the employee.
     @return the ID of the employee
     */
    public Long getId() {
        return id;
    }

    /**
     Sets the ID of the employee.
     @param id the ID of the employee
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     Retrieves the email address of the employee.
     @return the email address of the employee
     */
    public String getEmail() {
        return email;
    }

    /**
     Sets the email address of the employee.
     @param email the email address of the employee
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     Retrieves the password of the employee.
     @return the password of the employee
     */
    public String getPassword() {
        return password;
    }

    /**
     Sets the password of the employee.
     @param password the password of the employee
     */
    public void setPassword(String password) {
        this.password = password;
    }
}


