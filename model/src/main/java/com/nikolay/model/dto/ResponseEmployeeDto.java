package com.nikolay.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The type Response employee dto.
 */
public class ResponseEmployeeDto {

  private Long id;

  private Long departmentId;

  private String departmentName;

  private String fullName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  private BigDecimal salary;

  /**
   * Instantiates a new Response employee dto.
   */
  public ResponseEmployeeDto() {
  }

  /**
   * Instantiates a new Response employee dto.
   *
   * @param id the id
   * @param departmentId the department id
   * @param departmentName the department name
   * @param fullName the full name
   * @param birthday the birthday
   * @param salary the salary
   */
  public ResponseEmployeeDto(Long id, Long departmentId, String departmentName,
      String fullName, LocalDate birthday, BigDecimal salary) {
    this.id = id;
    this.departmentId = departmentId;
    this.departmentName = departmentName;
    this.fullName = fullName;
    this.birthday = birthday;
    this.salary = salary;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }
}
