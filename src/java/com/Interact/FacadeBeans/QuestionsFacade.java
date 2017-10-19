/*
 * Created by Akhil Gangu on 2017.10.13  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.FacadeBeans;

import com.Interact.Entities.Questions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Akhil
 */
@Stateless
public class QuestionsFacade extends AbstractFacade<Questions> {

    @PersistenceContext(unitName = "InteractPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionsFacade() {
        super(Questions.class);
    }
    
    public List<Questions> findBySessionId(String sessionId) {
        return em.createNamedQuery("Questions.findBySessionId").setParameter("session_id", sessionId)
                .getResultList();
    }
}
