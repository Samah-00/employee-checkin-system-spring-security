/**
 This class represents the controller for handling admin-related HTTP requests.
 It provides endpoints for admin login, managing employees, and employee details.
 */
package hac.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hac.entities.model.Employee;
import hac.entities.repository.EmployeeRepository;
import hac.entities.repository.AttendanceRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * Displays the admin login page.
     *
     * @param model the model object for rendering the view
     * @return the name of the lognAdmin view
     */
    @GetMapping("/")
    public String loginAdmin(Model model) {
        return "lognAdmin";
    }

    /**
     * Retrieves all employees and displays them in the employee list page.
     *
     * @param model the model object for rendering the view
     * @return the name of the admin/employee-list view
     */
    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "admin/employee-list";
    }

    /**
     * Retrieves the details of a specific employee and displays them in the employee details page.
     *
     * @param id    the ID of the employee
     * @param model the model object for rendering the view
     * @return the name of the admin/employee-details view
     */
    @GetMapping("/employees/{id}")
    public String getEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            model.addAttribute("employee", employee);
            model.addAttribute("attendanceRecords", attendanceRepository.findByEmployee(employee));
        }
        return "admin/employee-details";
    }

    /**
     * Displays the edit employee form for a specific employee.
     *
     * @param id    the ID of the employee
     * @param model the model object for rendering the view
     * @return the name of the admin/edit-employee-form view
     */
    @GetMapping("/employees/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            model.addAttribute("employee", employee);
        }
        return "admin/edit-employee-form";
    }

    /**
     * Processes the edit employee form submission.
     * Updates the employee's details in the employee repository.
     *
     * @param id       the ID of the employee
     * @param employee the updated employee object
     * @return the URL for redirecting to the employee list page
     */
    @PostMapping("/employees/{id}/edit")
    public String editEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee) {
        employee.setId(id);
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }

    /**
     * Deletes an employee and their associated attendance records.
     *
     * @param id the ID of the employee
     * @return the URL for redirecting to the employee list page
     */
    @Transactional
    @GetMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable("id") Long id) {
        // Find the employee with the given ID
        Employee employee = employeeRepository.findById(id).orElse(null);

        // Check if the employee exists
        if (employee != null) {
            // Delete the attendance records associated with the employee
            attendanceRepository.deleteByEmployee(employee);

            // Delete the employee
            employeeRepository.deleteById(id);
        }

        // Redirect to the employee list page
        return "redirect:/admin/employees";
    }
}