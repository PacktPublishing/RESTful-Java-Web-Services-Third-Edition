/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.validation;

import com.packtpub.rest.ch4.model.Department;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Defines the logic to validate a given constraint It just follows a dummy
 * implementation, you can override isDeptExistsForLoc() to provide real life
 * implementation.
 *
 * @author Jobinesh
 */
public class ValidDepartmentValidator implements ConstraintValidator<ValidDepartment, Department> {

    private static final Logger logger = Logger.getLogger(ValidDepartmentValidator.class.getName());

    @Override
    public void initialize(ValidDepartment constraintAnnotation) {
    }

    @Override
    public boolean isValid(Department department, ConstraintValidatorContext context) {
        
        if (isDeptExistsForLoc(department.getDepartmentId(),
                department.getDepartmentName(), department.getLocationId())) {
            
            logger.log(Level.INFO, "Valid: {0}", department.toString());
            return true;
        }

        logger.log(Level.INFO, "InValid: {0}", department.toString());
        return false;
    }

    private boolean isDeptExistsForLoc(Short deptId, String deptName, Short locationId) {
        if(deptId != 101)
            return true;
        return false;
    }

}
