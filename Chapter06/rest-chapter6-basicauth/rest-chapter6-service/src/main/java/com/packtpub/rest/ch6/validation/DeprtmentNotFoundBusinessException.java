/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.validation;

/**
 * Custom business exception. DepartmentNotFoundExceptionMapper maps this to 
 * HTTP response code
 *
 * @author Jobinesh
 */
public class DeprtmentNotFoundBusinessException extends Exception {

    public DeprtmentNotFoundBusinessException(String message) {
        super(message);
    }

    public DeprtmentNotFoundBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeprtmentNotFoundBusinessException(Throwable cause) {
        super(cause);
    }

}
