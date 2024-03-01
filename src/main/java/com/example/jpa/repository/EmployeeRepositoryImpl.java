package com.example.jpa.repository;

import com.example.jpa.entity.Employee;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

//isolate the persistence logic for each entity using the Repository pattern
public class EmployeeRepositoryImpl implements EmployeeRepository {
    EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Employee> save(Employee employee) {
        try {
            entityManager.getTransaction().begin(); //uncomment if not using @Transactional
            if (employee.getId() == null) {
                entityManager.persist(employee);
            } else {
                entityManager.merge(employee);
            }
            entityManager.getTransaction().commit(); //uncomment if not using @Transactional

            return Optional.of(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

    @Override
    public Optional<Employee> getEmployeeByFullname(String fName, String lName) {
        TypedQuery<Employee> jpslQuery = entityManager.createQuery("SELECT e FROM Employee as e WHERE e.lName = :lName and e.fName=:fName", Employee.class);
        jpslQuery.setParameter("lName", lName);
        jpslQuery.setParameter("fName", fName);
        return Optional.ofNullable(jpslQuery.getSingleResult());
    }

    @Override
    @Transactional(
            rollbackOn = IllegalArgumentException.class,
            dontRollbackOn = EntityExistsException.class)
    public void deleteEmployee(Employee employee) {
        //entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(employee)) {
            entityManager.remove(employee);
        } else {
            entityManager.merge(employee);
        }

        //entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }

    @Override
    public List<Employee> getEmployeesByExperience(Integer yearsOfExperience) {
        Query jpqlQuery = entityManager.createQuery("SELECT * FROM Employee " +
                "WHERE yearsExperience > :yearsExperience ORDER BY lName");
        jpqlQuery.setParameter("yearsExperience", yearsOfExperience);
        return jpqlQuery.getResultList();

    }

    @Override
    public List<Employee> getEmployeesByExperienceNativeQuery(Integer yearsOfExperience) {
        Query jpqlQuery = entityManager.createNativeQuery("SELECT * FROM employees " +
                "WHERE yearsExperience > :yearsExperience ORDER BY lName", Employee.class);
        jpqlQuery.setParameter("yearsExperience", yearsOfExperience);
        return jpqlQuery.getResultList();
    }

    @Override
    public List<Employee> getEmployeesByExperienceCriteriaQuery(Integer yearsOfExperience) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        List<Employee> employees = entityManager.createQuery(criteriaQuery.select(employeeRoot)
                .where(criteriaBuilder.greaterThan(employeeRoot.get("yearsExperience"), yearsOfExperience))).getResultList();
        return employees;
    }

}
