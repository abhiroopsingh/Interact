/*
 * Created by Abhiroop Singh on 2017.10.06  * 
 * Copyright © 2017 Abhiroop Singh. All rights reserved. * 
 */
package com.Interact.Questions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Abhi
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "InteractPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
}
