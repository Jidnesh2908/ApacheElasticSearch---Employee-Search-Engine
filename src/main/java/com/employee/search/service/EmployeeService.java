
package com.employee.search.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.employee.search.model.Employee;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class EmployeeService {

    private final ElasticsearchClient client;

    public EmployeeService(ElasticsearchClient client) {
        this.client = client;
    }

    // 1. Add Employee
    public String addEmployee(Employee employee) throws Exception {

        client.index(i -> i
            .index("employees")
            .id(employee.getEmployeeId())
            .document(employee)
        );

        return "Employee added successfully!";
    }

    // 2. Get Employee by ID
    public Employee getEmployeeById(String employeeId)
            throws Exception {

        var response = client.get(g -> g
            .index("employees")
            .id(employeeId),
            Employee.class
        );

        if (!response.found()) {
            return null;
        }

        return response.source();
    }

    // 3. Search Employees by Name and Skills
    public List<Employee> searchEmployees(String searchText)
            throws Exception {

        SearchResponse<Employee> response = client.search(s -> s
            .index("employees")
            .query(q -> q
                .multiMatch(m -> m
                    .query(searchText)
                    .fields("name", "skills")
                )
            ),
            Employee.class
        );

        return response.hits().hits()
            .stream()
            .map(hit -> hit.source())
            .filter(Objects::nonNull)
            .toList();
    }

    // 4. Fuzzy Search Employees
    public List<Employee> fuzzySearchEmployees(String searchText)
            throws Exception {

        SearchResponse<Employee> response = client.search(s -> s
            .index("employees")
            .query(q -> q
                .multiMatch(m -> m
                    .query(searchText)
                    .fields("name", "skills", "designation")
                    .fuzziness("AUTO")
                )
            ),
            Employee.class
        );

        return response.hits().hits()
            .stream()
            .map(hit -> hit.source())
            .filter(Objects::nonNull)
            .toList();
    }

    // 5. Filter Employees by Department and Location
    public List<Employee> filterEmployees(
            String department, String location) throws Exception {

        SearchResponse<Employee> response = client.search(s -> s
            .index("employees")
            .query(q -> q
                .bool(b -> {

                    if (department != null && !department.isBlank()) {
                        b.filter(f -> f
                            .term(t -> t
                                .field("department")
                                .value(department)
                            )
                        );
                    }

                    if (location != null && !location.isBlank()) {
                        b.filter(f -> f
                            .term(t -> t
                                .field("location")
                                .value(location)
                            )
                        );
                    }

                    return b;
                })
            ),
            Employee.class
        );

        return response.hits().hits()
            .stream()
            .map(hit -> hit.source())
            .filter(Objects::nonNull)
            .toList();
    }

    // 6. Sort Employees by Salary
    public List<Employee> sortEmployeesBySalary(String order)
            throws Exception {

        SortOrder sortOrder = "desc".equalsIgnoreCase(order)
                ? SortOrder.Desc
                : SortOrder.Asc;

        SearchResponse<Employee> response = client.search(s -> s
            .index("employees")
            .sort(sort -> sort
                .field(f -> f
                    .field("salary")
                    .order(sortOrder)
                )
            ),
            Employee.class
        );

        return response.hits().hits()
            .stream()
            .map(hit -> hit.source())
            .filter(Objects::nonNull)
            .toList();
    }

    // 7. Pagination
    public List<Employee> getEmployeesWithPagination(
            int page, int size) throws Exception {

        int from = page * size;

        SearchResponse<Employee> response = client.search(s -> s
            .index("employees")
            .from(from)
            .size(size),
            Employee.class
        );

        return response.hits().hits()
            .stream()
            .map(hit -> hit.source())
            .filter(Objects::nonNull)
            .toList();
    }
}
