/*
 * Created by Andy Sin on 2017.12.01  * 
 * Copyright © 2017 Andy Sin. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.UserAnswers;
import com.Interact.FacadeBeans.QuestionsFacade;
import com.Interact.FacadeBeans.UserAnswersFacade;
import com.Interact.managers.AccountManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Andy
 */
@Named("gradesController")
@SessionScoped
public class GradesController implements Serializable {

    @EJB
    private com.Interact.FacadeBeans.UserAnswersFacade ejbFacade;

    @EJB
    private com.Interact.FacadeBeans.UserAnswersFacade userAnswersFacade;

    private List<UserAnswers> sessionAnswers = null;
    private List<Integer> dummyList = null; // for class stats

    private String low;
    private String high;
    private String avg;
    private String median;

    @Inject
    private QuestionsFacade questionsFacade;

    @Inject
    private AccountManager accountManager;

    public GradesController() {

    }

    public void prepareScores() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().
                get("sessionId");

        System.out.println("SESSION ID: " + session_id);
        String username = accountManager.getUsername();

        this.setSessionAnswers(userAnswersFacade.findByUsernameAndSession(
                username, session_id));
        // To display 1 row in class statistics table
        this.dummyList = new ArrayList<>();
        dummyList.add(1);
        
    }

    public List<UserAnswers> getSessionAnswers() {
        return sessionAnswers;
    }

    public void setSessionAnswers(List<UserAnswers> sessionAnswers) {
        this.sessionAnswers = sessionAnswers;
    }

    public int getTotalPoints(String sessionId) {
        System.out.println("Session: " + sessionId);
        System.out.println("Total: " + questionsFacade.findBySessionId(sessionId).size());
        return questionsFacade.findBySessionId(sessionId).size();
    }

    private UserAnswersFacade getFacade() {
        return ejbFacade;
    }

    public String calculatePercent(String score, String sessionId) {
        int total = getTotalPoints(sessionId);
        return convertToPercent(Double.parseDouble(score), total);
    }

    private String convertToPercent(double score, int total) {
        return Math.round((score / total) * 100) + "%";
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
        this.median = median;
    }

    public List<Integer> getDummyList() {
        return dummyList;
    }

    public void setDummyList(List<Integer> dummyList) {
        this.dummyList = dummyList;
    }
}
