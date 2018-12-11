package com.nikolay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

/** The type Employee. */
public class Employee {

    private Long id;

    private long departmentId;

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
     * @param departmentId the department identifier
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
     * @return the identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets department name.
     *
     * @return the department name
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets department id.
     *
     * @param departmentId the department identifier to set
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets full name.
     *
     * @return the employee full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the employee full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets birthday.
     *
     * @return the employee birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the employee birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets salary.
     *
     * @return the employee salary
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * Sets salary.
     *
     * @param salary the employee salary to set
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
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return departmentId == employee.departmentId
                && Objects.equals(id, employee.id)
                && Objects.equals(fullName, employee.fullName)
                && Objects.equals(birthday, employee.birthday)
                && Objects.equals(salary, employee.salary)
                && Objects.equals(currency, employee.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentId, fullName, birthday, salary, currency);
    }
}
