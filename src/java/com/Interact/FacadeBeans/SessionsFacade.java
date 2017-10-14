/*
 * Created by Akhil Gangu on 2017.10.13  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.FacadeBeans;

import com.Interact.Entities.Sessions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Akhil
 */
@Stateless
public class SessionsFacade extends AbstractFacade<Sessions> {

    @PersistenceContext(unitName = "InteractPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SessionsFacade() {
        super(Sessions.class);
    }
    
}
