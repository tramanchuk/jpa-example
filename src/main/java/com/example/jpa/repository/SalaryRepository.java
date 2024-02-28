package com.example.jpa.repository;

import com.example.jpa.entity.Salary;

import java.util.Optional;

public interface SalaryRepository {
    Optional<Salary> save(Salary salary);
    void delete(Salary salary);
    Optional<Salary> getSalaryById(Long id);
}
