package com.nikolay;

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

    private Currency currency;

  /** Instantiates a new Department. */
  public Department() {}

  /**
   * Instantiates a new Department.
   *
   * @param departmentName the department name
   * @param averageSalary the average salary
   * @param currency the currency
   */
  public Department(String departmentName, BigDecimal averageSalary, Currency currency) {
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
        this.currency = currency;
    }

  /**
   * Instantiates a new Department.
   *
   * @param id the id
   * @param departmentName the department name
   * @param averageSalary the average salary
   * @param currency the currency
   */
  public Department(Long id, String departmentName, BigDecimal averageSalary, Currency currency) {
        this.id = id;
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
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
   * @param averageSalary the average salary to set
   */
  public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
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
}
