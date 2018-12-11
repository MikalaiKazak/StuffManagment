package com.nikolay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * The type Employee.
 */
public class Employee {

    private Long id;

    private Long departmentId;

    private String fullName;

    private LocalDate birthday;

    private BigDecimal salary;

    private Currency currency;

    /**
     * Instantiates a new Employee.
     */
    public Employee() {
    }

    /**
     * Instantiates a new Employee.
     *
     * @param id           the id
     * @param departmentId the department id
     * @param fullName     the full name
     * @param birthday     the birthday
     * @param salary       the salary
     * @param currency     the currency
     */
    public Employee(
            Long id,
            Long departmentId,
            String fullName,
            LocalDate birthday,
            BigDecimal salary,
            Currency currency) {
        this.id = id;
        this.departmentId = departmentId;
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
        this.currency = currency;
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
     * Gets department id.
     *
     * @return the department id
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets department id.
     *
     * @param departmentId the department id
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * Sets salary.
     *
     * @param salary the salary
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}


