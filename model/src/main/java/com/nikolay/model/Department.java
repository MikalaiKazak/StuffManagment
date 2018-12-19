package com.nikolay.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * The type Department.
 *
 * @author Mikalai_Kazak @epam.com 10.12.2018
 */
public class Department {

    private Long id;

    private String departmentName;

    private BigDecimal averageSalary;

    /**
     * Instantiates a new Department.
     */
    public Department() {
    }

    /**
     * Instantiates a new Department.
     *
     * @param id             the id
     * @param departmentName the department name
     * @param averageSalary  the average salary
     */
    public Department(Long id, String departmentName, BigDecimal averageSalary) {
        this.id = id;
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
    }

    /**
     * Instantiates a new Department.
     *
     * @param departmentName the department name
     * @param averageSalary  the average salary
     */
    public Department(String departmentName, BigDecimal averageSalary) {
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets department name.
     *
     * @return the department name
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets department name.
     *
     * @param departmentName the department name
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Gets average salary.
     *
     * @return the average salary
     */
    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    /**
     * Sets average salary.
     *
     * @param averageSalary the average salary
     */
    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }
}
