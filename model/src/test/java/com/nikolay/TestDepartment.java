package com.nikolay;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;

public class TestDepartment {

    private Department department;

    @Before
    public void setUp(){
        department = new Department(1L, "java", Arrays.asList(
            new Employee(1L, 1L, "Nikolay Kozak", LocalDate.of(1999, 2, 28), BigDecimal.valueOf(200), Currency.getInstance("USD")),
            new Employee(2L, 1L, "Nikolay Kozak", LocalDate.of(1999, 2, 28), BigDecimal.valueOf(200), Currency.getInstance("USD"))
        ));
    }

    @Test
    public void testAverageSalary(){
        BigDecimal averageSalary = department.getAverageSalary();
        Assert.assertEquals(BigDecimal.valueOf(200), averageSalary);
    }
}
