/*
 * Created by Andy Sin on 2017.10.25  * 
 * Copyright © 2017 Andy Sin. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.Questions;
import com.Interact.controllers.QuestionsController;
import com.Interact.controllers.SessionsController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andy
 */
@Named("masterViewController")
@SessionScoped
public class MasterViewController implements Serializable {

    @Inject
    private QuestionsController questionsController;
    @Inject
    private SessionsController sessionsController;

    private List<Questions> sessionQuestions = null;
    private int questionNum = 0;

    public MasterViewController() {
    }

    public String nextIsClicked() {
        questionNum++;

        if (questionNum >= sessionQuestions.size()) {
            sessionQuestions = null;
            sessionsController.setJoinKey(null);
            return "UserHomePage?faces-redirect=true";
        } else {
            return "JoinSession?faces-redirect=true";
        }

    }

    public String previousIsClicked() {
        if (questionNum != 0) {
            questionNum--;
        }

        return "JoinSession?faces-redirect=true";

    }

    public String getQuestion() {
        if (sessionQuestions == null) {
            sessionQuestions = questionsController.getSessionItems();
            questionNum = 0;
        }

        System.out.println("session questions:" + sessionQuestions);

        String question = sessionQuestions.get(questionNum).getQuestion();

        return question;

    }

}
