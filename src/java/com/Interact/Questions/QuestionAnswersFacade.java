/*
 * Created by Abhiroop Singh on 2017.10.06  * 
 * Copyright © 2017 Abhiroop Singh. All rights reserved. * 
 */
package com.Interact.Questions;

import com.interact.Session.QuestionAnswers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Abhi
 */
@Stateless
public class QuestionAnswersFacade extends AbstractFacade<QuestionAnswers> {

    @PersistenceContext(unitName = "InteractPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionAnswersFacade() {
        super(QuestionAnswers.class);
    }
    
}
