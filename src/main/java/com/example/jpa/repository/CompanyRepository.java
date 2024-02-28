package com.example.jpa.repository;

import com.example.jpa.entity.Company;

import java.util.Optional;

public interface CompanyRepository {
    Optional<Company> save(Company company);
    Optional<Company> getCompanyById(Long id);
    void delete(Company company);
}
