package com.example.jpa.repository;

import com.example.jpa.entity.Employee;

import java.util.Optional;
import java.util.List;

//isolate the persistence logic for each entity using the Repository pattern
public interface EmployeeRepository {
    Optional<Employee> save(Employee employee);
    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> getEmployeeByFullname(String fName, String lName);
    void deleteEmployee(Employee employee);
    List<Employee> getEmployeesByExperience(Integer yearsOfExperience);
    List<Employee> getEmployeesByExperienceNativeQuery(Integer yearsOfExperience);
    List<Employee> getEmployeesByExperienceCriteriaQuery(Integer yearsOfExperience);
}
