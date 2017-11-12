/*
 * Created by Akhil Gangu on 2017.10.25  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.UserAnswers;
import com.Interact.Entities.Users;
import com.Interact.FacadeBeans.UserAnswersFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Akhil
 */
@Named("scoresController")
@SessionScoped
public class ScoresController implements Serializable {

    @EJB
    private com.Interact.FacadeBeans.UserAnswersFacade ejbFacade;

    @EJB
    private com.Interact.FacadeBeans.SessionsFacade sessionsFacade;

    private List<UserAnswers> sessionAnswers = null;

    private int totalPoints;

    public ScoresController() {

    }

    public String prepareScores() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().
                get("sessId");
        this.setSessionAnswers(getFacade().findBySession(session_id));
        this.setTotalPoints(sessionsFacade.findById(session_id).getQuestionsCollection().size());
        return "StudentScores.xhtml?faces-redirect=true";
    }

    public List<UserAnswers> getSessionAnswers() {
        return sessionAnswers;
    }

    public void setSessionAnswers(List<UserAnswers> sessionAnswers) {
        this.sessionAnswers = sessionAnswers;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int points) {
        totalPoints = points;
    }

    private UserAnswersFacade getFacade() {
        return ejbFacade;
    }

    public String calculatePercent(String score) {
        return Math.round((Double.parseDouble(score) / this.getTotalPoints()) * 100) + "%";
    }
}
