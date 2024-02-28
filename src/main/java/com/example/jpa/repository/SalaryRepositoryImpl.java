package com.example.jpa.repository;

import com.example.jpa.entity.Salary;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SalaryRepositoryImpl implements SalaryRepository{
    EntityManager entityManager;

    public SalaryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Salary> save(Salary salary) {
        try {
            entityManager.getTransaction().begin();
            if (salary.getId() != null){
                entityManager.persist(salary);
            } else {
                entityManager.merge(salary);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(salary);
    }

    @Override
    public void delete(Salary salary) {
        entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(salary)) {
            entityManager.remove(salary);
        } else {
            entityManager.merge(salary);
        }

        entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }

    @Override
    public Optional<Salary> getSalaryById(Long id) {
        return Optional.ofNullable(entityManager.find(Salary.class, id));
    }
}
