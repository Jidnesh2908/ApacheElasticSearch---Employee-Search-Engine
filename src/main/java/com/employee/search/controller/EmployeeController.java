
package com.employee.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.search.model.Employee;
import com.employee.search.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Add Employee
    @PostMapping
    public String addEmployee(@RequestBody Employee employee)
            throws Exception {

        return employeeService.addEmployee(employee);
    }

    // 2. Get Employee by ID
    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable String employeeId)
            throws Exception {

        return employeeService.getEmployeeById(employeeId);
    }

    // 3. Search Employees
    @GetMapping("/search")
    public List<Employee> searchEmployees(
            @RequestParam("q") String query) throws Exception {

        return employeeService.searchEmployees(query);
    }

    // 4. Fuzzy Search
    @GetMapping("/search/fuzzy")
    public List<Employee> fuzzySearchEmployees(
            @RequestParam("q") String query) throws Exception {

        return employeeService.fuzzySearchEmployees(query);
    }

    // 5. Filter Employees
    @GetMapping("/filter")
    public List<Employee> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String location)
            throws Exception {

        return employeeService.filterEmployees(department, location);
    }

    // 6. Sort Employees by Salary
    @GetMapping("/sort")
    public List<Employee> sortEmployees(
            @RequestParam(defaultValue = "asc") String order)
            throws Exception {

        return employeeService.sortEmployeesBySalary(order);
    }

    // 7. Pagination
    @GetMapping("/page")
    public List<Employee> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size)
            throws Exception {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                "Page must be >= 0 and size must be between 1 and 100"
            );
        }

        return employeeService.getEmployeesWithPagination(page, size);
    }
}
