package com.nikolay.model;

/**
 * The type Department.
 *
 * @author Mikalai_Kazak @epam.com 10.12.2018
 */
public class Department {

  private Long id;

  private String departmentName;

  /**
   * Instantiates a new Department.
   */
  public Department() {
  }

  /**
   * Instantiates a new Department.
   *
   * @param id the id
   * @param departmentName the department name
   */
  public Department(Long id, String departmentName) {
    this.id = id;
    this.departmentName = departmentName;
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
}
