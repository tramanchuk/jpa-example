package com.example.jpa.repository;

import com.example.jpa.entity.Company;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class CompanyRepositoryImpl implements CompanyRepository{
    EntityManager entityManager;

    public CompanyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Company> save(Company company) {
        try{
            entityManager.getTransaction().begin();
            if (company.getId() == null){
                entityManager.persist(company);
            }else {
                entityManager.merge(company);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(company);
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {
        return Optional.ofNullable(entityManager.find(Company.class, id));
    }

    @Override
    public void delete(Company company) {
        entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(company)) {
            entityManager.remove(company);
        } else {
            entityManager.merge(company);
        }

        entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }
}
