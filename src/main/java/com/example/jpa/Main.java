package com.example.jpa;

import com.example.jpa.entity.Company;
import com.example.jpa.entity.Employee;
import com.example.jpa.entity.Salary;
import com.example.jpa.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class Main {

    @PersistenceContext
    EntityManager entityManager;

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(entityManager);
        CompanyRepository companyRepository = new CompanyRepositoryImpl(entityManager);
        SalaryRepository salaryRepository = new SalaryRepositoryImpl(entityManager);

        Company company1 = new Company("MyCompany");
        companyRepository.save(company1);
        Salary salary1 = new Salary(54000.00, true);
        salaryRepository.save(salary1);
        Salary salary2 = new Salary(54000.00, true);
        salaryRepository.save(salary1);
        salary1.setCompany(company1);
        salaryRepository.save(salary1);
        salary2.setCompany(company1);
        salaryRepository.save(salary1);

        //create a new Employee
        Employee employee = new Employee();
        employee.setfName("Mary");
        employee.setlName("Doe");
        employee.setYearsExperience(20);

        //set salary
        employee.setSalary(salary1);

        //set company
        employee.setCompany(company1);

        Employee employee2 = new Employee();
        employee2.setfName("James");
        employee2.setlName("Doe");
        employee2.setYearsExperience(5);
        employee2.setCompany(company1);
        employee2.setSalary(salary2);
        //save Employees
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        //update Employee
        Optional<Employee> retrievedEmployee = employeeRepository.
                getEmployeeById(6L);
        if (retrievedEmployee.isPresent()) {
            retrievedEmployee.get().setlName("Johnson");
            employeeRepository.save(retrievedEmployee.get());
            System.out.println("name is updated");
        }

        //delete Employee
        employeeRepository.deleteEmployee(employee2);

        System.out.println("Don't forget to launch Postgres before running this code!");
        entityManager.close();
        entityManagerFactory.close();
    }
}