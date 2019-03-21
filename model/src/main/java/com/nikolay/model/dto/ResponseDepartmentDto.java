package com.nikolay.model.dto;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class ResponseDepartmentDto {

  private Long id;

  private String departmentName;

  private BigDecimal averageSalary;

  /**
   * Instantiates a new Update department dto.
   */
  public ResponseDepartmentDto() {
  }

  /**
   * Instantiates a new Response department dto.
   *
   * @param id the id
   * @param departmentName the department name
   * @param averageSalary the average salary
   */
  public ResponseDepartmentDto(Long id, String departmentName, BigDecimal averageSalary) {
    this.id = id;
    this.departmentName = departmentName;
    this.averageSalary = averageSalary;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public BigDecimal getAverageSalary() {
    return averageSalary;
  }

  public void setAverageSalary(BigDecimal averageSalary) {
    this.averageSalary = averageSalary;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ResponseDepartmentDto.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("departmentName='" + departmentName + "'")
        .add("averageSalary=" + averageSalary)
        .toString();
  }
}
