/**
 This class represents the main controller for handling HTTP requests.
 It is responsible for routing and handling exceptions.
 */
package hac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Handles the HTTP GET request for the root ("/") URL.
     * It routes to the login-form view.
     *
     * @param model the model object for rendering the view
     * @return the name of the login-form view
     */
    @GetMapping("/")
    public String router(Model model) {
        return "login-form";
    }

    /**
     * Handles the HTTP GET request for the "/login" URL.
     * It routes to the validationEmployee view.
     *
     * @param model the model object for rendering the view
     * @return the name of the validationEmployee view
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "validationEmployee";
    }

    /**
     * Handles exceptions of type Exception.
     * It redirects to the error.html page.
     *
     * @param e the exception object
     * @return the name of the error view
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error"; // Redirect to the "error.html" page
    }
}
