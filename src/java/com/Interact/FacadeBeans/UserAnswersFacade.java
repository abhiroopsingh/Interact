/*
 * Created by Akhil Gangu on 2017.10.13  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.FacadeBeans;

import com.Interact.Entities.UserAnswers;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Akhil
 */
@Stateless
public class UserAnswersFacade extends AbstractFacade<UserAnswers> {

    @PersistenceContext(unitName = "InteractPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAnswersFacade() {
        super(UserAnswers.class);
    }
    
    
       public List<UserAnswers> findByUsernameAndSession(String username, String session_id) {

        return em.createNamedQuery("UserAnswers.findByUsernameAndSession")
                .setParameter("username", username)
                .setParameter("session_id", session_id).getResultList();
    }
}
