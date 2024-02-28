package com.example.jpa.repository;

import com.example.jpa.entity.Employee;
import jakarta.persistence.EntityManager;

import javax.swing.text.html.Option;
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
    public void deleteEmployee(Employee employee) {
        entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(employee)) {
            entityManager.remove(employee);
        } else {
            entityManager.merge(employee);
        }

        entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }

}
