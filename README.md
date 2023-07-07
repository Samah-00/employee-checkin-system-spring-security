## Authors
* Name: Email: Saja Abu Maizar | ID:208072371 | sajaabu@edu.hac.ac.il
* Name: Email: Samah Rajabi | ID: 211558556 | samahra@edu.hac.ac.il

## Description

## Employee Attendance System

This project is web application for attendance management. It includes several controllers that handle different functionalities related to employee login, clock-in/clock-out, and administrative tasks. Here's a breakdown of the different controllers and their functionalities:

* MainController:

Handles the root route ("/") and returns the "login-form" view.
Handles the "/login" route and returns the "validationEmployee" view.
Includes an exception handler to handle general exceptions and redirect to the "error" view.

* EmployeeController:

Handles routes under the "/employee" path.
Provides functionalities related to employee login, clock-in, clock-out, and attendance records.
Includes methods to show login forms, process login attempts, calculate hours passed since clock-in, and retrieve attendance records.
Implements logic for enabling/disabling clock-in and clock-out buttons based on the current state of attendance.
Utilizes the EmployeeRepository and AttendanceRepository for database interactions.

* AdminController:

Handles routes under the "/admin" path.
Implements administrative functionalities for managing employees.
Includes methods to list all employees, show employee details, edit employee information, and delete employees.
Utilizes the EmployeeRepository and AttendanceRepository for database interactions.
Includes transactional operations to ensure data consistency when deleting an employee and associated attendance records.

The project is built using the Spring Framework and follows the MVC (Model-View-Controller) architectural pattern. It utilizes Thymeleaf as the templating engine for rendering HTML views.

### General information

### Functionality

The website offers different functionalities and capabilities based on user roles. Here's an overview of what administrators and employees can do and see:

* Administrator Functionality:
- Employee Management: Administrators have the ability to manage employees. They can view a list of all employees, access individual employee details, and edit employee information such as email, and password. Administrators can also delete an existing employee if necessary.

- Attendance Records: Admins have access to attendance records, allowing them to view the clock-in and clock-out times of employees. They can retrieve attendance history.

* Employee Functionality:
- Clock-in/Clock-out: Employees can record their working hours by using the clock-in and clock-out functionality. The website provides a user-friendly interface where employees can easily click on the respective buttons to mark their entry and exit times.

- Attendance History: Employees can view their own attendance history, which includes previous clock-in and clock-out timestamps. This feature allows employees to keep track of their working hours and monitor their attendance patterns.

## Installation

(database creation, etc)

* Running the Program
To run the program, follow the steps below:

1. Download all the dapandencies.

2. Start the XAMPP server: Open XAMPP control panel and start the Apache and MySQL modules.

3. Access phpMyAdmin: Open your web browser and go to http://localhost/phpmyadmin.

4. Create a new database: In phpMyAdmin, click on the "Databases" tab and enter "ex5" as the database name. Click the "Create" button to create the database.

5. Run Ex5TemplateApplication.java

## Useful information

(user, password, etc)

* Accessing the Website
To access the website, follow the steps below:

1. Go to localhost:8080

2. Login as an admin: On the login page, enter the username **"admin"** and the password **"password"** to log in as an administrator. This will grant you access to the admin homepage, where you can perform various administrative tasks.

3. Login as an employee: If you're an employee, enter the username **"employee"** and the password **"password"** on the login page. After submitting the login form, a second login page will appear.

4. Employee authentication: On the second login page, enter your email and password. The system will validate the entered password and check if it matches the one stored in the database for your email. If the password is correct, you will be directed to the employee homepage (the clock-in/clock-out page), if you're a new employee without an existing account, enter your email and password, and the system will create a new employee account for you. After successful login, you will be redirected to the employee homepage.

Once logged in as an admin or employee, you can access the respective features and functionalities of the website. For admins, this includes managing employees, viewing attendance records, editing employee details, and more. Employees have access to the clock-in/clock-out functionality and can view their attendance history.
