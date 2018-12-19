package com.nikolay.rest;

import com.nikolay.model.Department;
import com.nikolay.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Department rest controller.
 */
@RestController
@RequestMapping("/department")
public class DepartmentRestController {

    /**
     * The Department service.
     */
    private DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Gets all departments.
     *
     * @return the all departments
     */
    @GetMapping("/")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departmentList = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        Department department = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.FOUND);
    }

    /**
     * Add department response entity.
     *
     * @param department the department
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity<Long> addDepartment(@RequestBody Department department) {
        Long id = departmentService.saveDepartment(department);
        department.setId(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /**
     * Remove department response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity removeDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Update department response entity.
     *
     * @param newDepartment the new department
     * @param id            the id
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity updateDepartment(@PathVariable Long id, @RequestBody Department newDepartment) {
        Department department = departmentService.getDepartmentById(id);
        department.setDepartmentName(newDepartment.getDepartmentName());
        department.setAverageSalary(newDepartment.getAverageSalary());
        departmentService.updateDepartment(department);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
