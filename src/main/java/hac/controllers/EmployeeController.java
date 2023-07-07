/**
 This class represents the controller for handling employee-related HTTP requests.
 It provides endpoints for employee login, clock in/out, and attendance retrieval.
 */

package hac.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hac.entities.model.Employee;
import hac.entities.model.Attendance;
import hac.entities.repository.EmployeeRepository;
import hac.entities.repository.AttendanceRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    public EmployeeController(EmployeeRepository employeeRepository, AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
    }

    /**
     * Displays the first login form.
     *
     * @param model the model object for rendering the view
     * @return the name of the validationEmployee view
     */
    @GetMapping("/")
    public String showFirstLoginForm(Model model) {
        return "validationEmployee";
    }

    /**
     * Displays the second login form.
     *
     * @param model the model object for rendering the view
     * @return the name of the employee/login view
     */
    @GetMapping("/secondLogin")
    public String showLoginForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/login";
    }

    /**
     * Processes the second login form submission.
     * Validates the employee's credentials and redirects to the clock-in/out page.
     *
     * @param employee the employee object submitted from the form
     * @param model    the model object for rendering the view
     * @return the URL for redirecting to the clock-in/out page
     */
    @PostMapping("/secondLogin")
    public String login(@ModelAttribute("employee") Employee employee, Model model) {
        Employee existingEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee == null) {
            //encrypt the password before saving it to the db
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(encryptedPassword);
            existingEmployee = employeeRepository.save(employee);
        } else if (!existingEmployee.getPassword().equals(employee.getPassword())) {
            model.addAttribute("error", "Incorrect password");
            return "employee/login";
        }

        Long employeeId = existingEmployee.getId();
        return "redirect:/employee/clock-in-out?employeeId=" + employeeId;
    }

    /**
     * Displays the clock-in/out page for the specified employee.
     * Determines the clock-in/out status and calculates the hours passed.
     *
     * @param employeeId the ID of the employee
     * @param model      the model object for rendering the view
     * @return the name of the employee/clock-in-out view
     */
    @GetMapping("/clock-in-out")
    public String clockInOut(@RequestParam("employeeId") Long employeeId, Model model) {

        Employee existingEmployee = employeeRepository.findById(employeeId).orElse(null);

        if (existingEmployee == null) {
            return "error";
        }

        List<Attendance> attendances = attendanceRepository.findLatestByEmployee(existingEmployee);
        Attendance latestAttendance = attendances.isEmpty() ? null : attendances.get(0);

        if(latestAttendance == null){ //the employee is new and wants to clock in
            model.addAttribute("clockOutDisabled", true);
            model.addAttribute("clockInDisabled", false);
        }
        else if(latestAttendance.getClockOutTime() == null){ //the employee has clocked in and not clocked out
            model.addAttribute("clockOutDisabled", false);
            model.addAttribute("clockInDisabled", true);
            LocalDateTime clockInTime = latestAttendance.getClockInTime();
            LocalDateTime currentTime = LocalDateTime.now();
            long hoursPassed = calculateHoursPassed(clockInTime, currentTime);
            model.addAttribute("hoursPassed", hoursPassed);
        }
        else if(latestAttendance.getClockOutTime() != null){ //the employee isn't new but wants to clock in again
            model.addAttribute("clockOutDisabled", true);
            model.addAttribute("clockInDisabled", false);
        }

        model.addAttribute("employee", existingEmployee);
        model.addAttribute("attendance", latestAttendance); // Add attendance object to the model

        return "employee/clock-in-out";
    }

    /**
     * Calculates the number of hours passed between two date-time values.
     *
     * @param startDateTime the starting date-time
     * @param endDateTime   the ending date-time
     * @return the number of hours passed
     */
    private long calculateHoursPassed(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long hours = 0;

        LocalDateTime tempDateTime = LocalDateTime.from(startDateTime);

        while (tempDateTime.isBefore(endDateTime)) {
            tempDateTime = tempDateTime.plusHours(1);
            hours++;
        }

        return hours;
    }

    /**
     * Processes the clock-in request for the specified employee.
     * Records the clock-in time in the attendance repository.
     *
     * @param employeeId the ID of the employee
     * @param model      the model object for rendering the view
     * @return the URL for redirecting to the attendance page
     */
    @GetMapping("/clock-in")
    public String clockIn(@RequestParam("employeeId") Long employeeId, Model model) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDate currentDate = LocalDate.now();
            Attendance attendance = new Attendance(employee, currentDateTime, null, currentDate);
            attendanceRepository.save(attendance);
        }
            assert employee != null;
            return "redirect:/employee/attendance?employeeId=" + employee.getId();
    }

    /**
     * Processes the clock-out request for the specified employee.
     * Records the clock-out time in the attendance repository.
     *
     * @param employeeId the ID of the employee
     * @param model      the model object for rendering the view
     * @return the URL for redirecting to the attendance page
     */
    @GetMapping("/clock-out")
    public String clockOut(@RequestParam("employeeId") Long employeeId, Model model) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            List<Attendance> attendances = attendanceRepository.findLatestByEmployee(employee);
            if (!attendances.isEmpty()) {
                Attendance attendance = attendances.get(0);
                if (attendance.getClockOutTime() == null) {
                    attendance.setClockOutTime(LocalDateTime.now());
                    attendanceRepository.save(attendance);
                }
            }
        }
        return "redirect:/employee/attendance?employeeId=" + employee.getId();
    }

    /**
     * Retrieves the attendance records for the specified employee.
     *
     * @param employeeId the ID of the employee
     * @param model      the model object for rendering the view
     * @return the name of the employee/attendance view
     */
    @GetMapping("/attendance")
    public String getAttendance(@RequestParam("employeeId") Long employeeId, Model model) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            model.addAttribute("employee", employee);
            model.addAttribute("attendanceRecords", attendanceRepository.findByEmployee(employee));
        }
        return "employee/attendance";
    }
}
