/*
 * Copyright © 2017 Packt Publishing  - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package com.packtpub.rest.ch9.odata.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;

/**
 * For handling JTA data source for JPA model used with Olingo OData services
 * See https://issues.apache.org/jira/browse/OLINGO-580
 * @author Jobinesh
 */
public class ODataJPATransactionImpl implements ODataJPATransaction {

    private final EntityManager em;

    public ODataJPATransactionImpl() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory(ODataJPAServiceFactoryImpl.PUNIT_NAME);
        em = emf.createEntityManager();
    }

    @Override
    public void begin() {
        em.getTransaction().begin();
    }

    @Override
    public void commit() {
        em.getTransaction().commit();
    }

    @Override
    public void rollback() {
        em.getTransaction().rollback();
    }

    @Override
    public boolean isActive() {
        return em.getTransaction().isActive();
    }

}
