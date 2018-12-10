package com.nikolay;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Mikalai_Kazak@epam.com 10.12.2018
 */
public class Department {


    private Long id;

    private String departmentName;

    private BigDecimal averageSalary;

    private Currency currency;

    public Department() {
    }

    public Department(String departmentName, BigDecimal averageSalary, Currency currency) {
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
        this.currency = currency;
    }

    public Department(Long id, String departmentName, BigDecimal averageSalary, Currency currency) {
        this.id = id;
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
        this.currency = currency;
    }

    /**
     * @return the identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the department name
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the department name to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the average salary
     */
    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    /**
     * @param averageSalary the average salary to set
     */
    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    /**
     * @return - the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
