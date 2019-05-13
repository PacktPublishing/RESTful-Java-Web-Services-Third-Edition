/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch9.odata.service;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.ref.factory.JPAEntityManagerFactory;

/**
 * Factory class  that transforms Java Persistence Models into an OData Service. 
 * @author Jobinesh
 */
public class ODataJPAServiceFactoryImpl extends ODataJPAServiceFactory {

    static final String PUNIT_NAME = "HR-PU";
    //   ODataJPATransactionImpl jtaTxn = new ODataJPATransactionImpl();

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext oDataJPAContext = getODataJPAContext();
        oDataJPAContext.setEntityManagerFactory(JPAEntityManagerFactory.getEntityManagerFactory(PUNIT_NAME));
        oDataJPAContext.setPersistenceUnitName(PUNIT_NAME);
        return oDataJPAContext;
    }

    @Override
    protected void setODataJPATransaction(ODataJPATransaction oDataJPATransaction) {

        //  super.setODataJPATransaction(jtaTxn);
    }

    public <T extends ODataCallback> T getCallback(final Class<? extends ODataCallback> callbackInterface) {
        T callback;

        if (callbackInterface.isAssignableFrom(ODataDebugCallbackImpl.class)) {
            callback = (T) new ODataDebugCallbackImpl();
        } else {
            callback = (T) super.getCallback(callbackInterface);
        }

        return callback;
    }

}
