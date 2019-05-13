/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch6.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * JAX-RS Binding for applying JWT Authentication<br>
 *
 * @author Balachandar
 */

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})

public @interface JWTTokenRequired {
    
}
