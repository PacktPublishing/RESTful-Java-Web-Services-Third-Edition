/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch4.ext;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * A JAX-RS meta-provider for dynamic registration of post-matching providers
 * during a JAX-RS application setup at deployment time. Dynamic feature is used
 * by JAX-RS runtime to register providers that shall be applied to a particular
 * resource class and method and overrides any annotation-based binding
 * definitions defined on any registered resource filter or interceptor
 * instance. Providers implementing this interface MAY be annotated with
 * @Provider annotation in order to be discovered by JAX-RS runtime when
 * scanning for resources and providers. This provider types is supported only
 * as part of the Server API.
 *
 * @author Jobinesh
 */
@Provider
public class DynamicFeatureRegister implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {

        if (resourceInfo.getResourceMethod().isAnnotationPresent(RequestLogger.class)) {
            context.register(RequestLoggerFilter.class);
        }

    }

}
