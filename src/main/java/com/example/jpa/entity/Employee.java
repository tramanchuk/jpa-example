package com.example.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="employee_id")
    private Long id;

    @Column
    private String fName;

    @Column
    private String lName;

    @Column
    private Integer yearsExperience;

    @Transient
    private Double totalCompensation;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Salary salary;

    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;

    public Employee() {
    }

    public Employee(Long id, String fName, String lName, Integer yearsExperience, Company company, Salary salary) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.yearsExperience = yearsExperience;
        this.company = company;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public Double getTotalCompensation() {
        return totalCompensation;
    }

    public void setTotalCompensation(Double totalCompensation) {
        this.totalCompensation = totalCompensation;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
