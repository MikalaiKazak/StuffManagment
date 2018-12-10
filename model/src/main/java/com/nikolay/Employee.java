package com.nikolay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Employee {

    private Long id;

    private String departmentName;

    private String fullName;

    private LocalDate birthday;

    private BigDecimal salary;

    private Currency currency;

    public Employee() {
    }

    public Employee(String departmentName, String fullName, LocalDate birthday, BigDecimal salary, Currency currency) {
        this.departmentName = departmentName;
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
        this.currency = currency;
    }

    public Employee(Long id, String departmentName, String fullName, LocalDate birthday, BigDecimal salary, Currency currency) {
        this.id = id;
        this.departmentName = departmentName;
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
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
     * @return the employee full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the employee full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the employee birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the employee birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the employee salary
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * @param salary the employee salary to set
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /**
     * @return the currency
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
