package com.packtpub.rest.ch4.model;

import com.packtpub.rest.ch4.model.Employee;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150916-rNA", date="2017-10-29T12:38:55")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> lastName;
    public static volatile SingularAttribute<Employee, String> firstName;
    public static volatile SingularAttribute<Employee, String> jobId;
    public static volatile SingularAttribute<Employee, Date> hireDate;
    public static volatile SingularAttribute<Employee, String> phoneNumber;
    public static volatile SingularAttribute<Employee, Short> departmentId;
    public static volatile CollectionAttribute<Employee, Employee> employeeCollection;
    public static volatile SingularAttribute<Employee, Integer> employeeId;
    public static volatile SingularAttribute<Employee, Employee> managerId;
    public static volatile SingularAttribute<Employee, BigDecimal> salary;
    public static volatile SingularAttribute<Employee, String> email;
    public static volatile SingularAttribute<Employee, BigDecimal> commissionPct;

}