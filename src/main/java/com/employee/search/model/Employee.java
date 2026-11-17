
package com.employee.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    @JsonProperty("employee_id")
    private String employeeId;

    private String name;
    private String email;
    private String department;
    private String designation;
    private String skills;
    private int experience;
    private String location;
    private double salary;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(String employeeId, String name,
                    String email, String department,
                    String designation, String skills,
                    int experience, String location,
                    double salary) {

        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.skills = skills;
        this.experience = experience;
        this.location = location;
        this.salary = salary;
    }

    // Getters and Setters

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
