package com.nikolay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * The type Department.
 *
 * @author Mikalai_Kazak @epam.com 10.12.2018
 */
public class Department {

    private Long id;

    private String departmentName;

    private Currency currency;

    private List<Employee> employees;

    /**
     * Instantiates a new Department.
     */
    public Department() {
    }

    public Department(Long id, String departmentName, Currency currency, List<Employee> employees) {
        this.id = id;
        this.departmentName = departmentName;
        this.currency = currency;
        this.employees = employees;
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
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets department name.
     *
     * @param departmentName the department name to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    /**
     * Gets average salary.
     *
     * @return the average salary
     */
    public BigDecimal getAverageSalary() {
        int size = employees.size();
        if(size == 0){
            return BigDecimal.ZERO;
        }
        return employees.stream().map(Employee::getSalary).reduce(BigDecimal::add).get().divide(BigDecimal.valueOf(size), RoundingMode.FLOOR);
    }

    /**
     * Gets currency.
     *
     * @return - the currency
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
        Department that = (Department) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentName, currency, employees);
    }
}
