/*
 * Created by Akhil Gangu on 2017.10.13  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.FacadeBeans;

import com.Interact.Entities.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Akhil
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
    
    /**
     * @param username is the username attribute (column) value of the user
     * @return object reference of the User entity whose username is username
     */
    public Users findByUsername(String username) {
        if (em.createQuery("SELECT c FROM Users c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (Users) (em.createQuery("SELECT c FROM Users c WHERE c.username = :username")
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }
    
}
