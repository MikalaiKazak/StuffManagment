package com.nikolay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/** The type Employee. */
public class Employee {

    private Long id;

    private String departmentName;

    private String fullName;

    private LocalDate birthday;

    private BigDecimal salary;

    private Currency currency;

  /** Instantiates a new Employee. */
  public Employee() {}

  /**
   * Instantiates a new Employee.
   *
   * @param departmentName the department name
   * @param fullName the full name
   * @param birthday the birthday
   * @param salary the salary
   * @param currency the currency
   */
  public Employee(
      String departmentName,
      String fullName,
      LocalDate birthday,
      BigDecimal salary,
      Currency currency) {
        this.departmentName = departmentName;
        this.fullName = fullName;
        this.birthday = birthday;
        this.salary = salary;
        this.currency = currency;
    }

  /**
   * Instantiates a new Employee.
   *
   * @param id the id
   * @param departmentName the department name
   * @param fullName the full name
   * @param birthday the birthday
   * @param salary the salary
   * @param currency the currency
   */
  public Employee(
      Long id,
      String departmentName,
      String fullName,
      LocalDate birthday,
      BigDecimal salary,
      Currency currency) {
        this.id = id;
        this.departmentName = departmentName;
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
}
