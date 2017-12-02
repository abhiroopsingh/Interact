/*
 * Created by Akhil Gangu on 2017.10.25  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.UserAnswers;
import com.Interact.FacadeBeans.QuestionsFacade;
import com.Interact.FacadeBeans.UserAnswersFacade;
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
    private List<Integer> dummyList = null; // for class stats

    private int totalPoints;
    private String low;
    private String high;
    private String avg;
    private String median;
    
    @Inject
    private QuestionsFacade questionsFacade;

    public ScoresController() {

    }

    public String prepareScores() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().
                get("sessId");
       
        this.setSessionAnswers(getFacade().findBySession(session_id));
        this.setTotalPoints(questionsFacade.findBySessionId(session_id).size());
        prepareSessionStats();
        // To display 1 row in class statistics table
        this.dummyList = new ArrayList<>();
        dummyList.add(1);
        
        return "StudentScores.xhtml?faces-redirect=true";
    }

    private void prepareSessionStats() {
        int[] scores = new int[this.getSessionAnswers().size()];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        double avg = 0.0;
        for (int k = 0; k < getSessionAnswers().size(); k++) {
            scores[k] = Integer.parseInt(sessionAnswers.get(k).getGrade());
            min = (scores[k] < min) ? scores[k] : min;
            max = (scores[k] > max) ? scores[k] : max;
            avg += scores[k] / (this.sessionAnswers.size() * 1.0);
        }
        this.setLow(convertToPercent(min * 1.0));
        this.setHigh(convertToPercent(max * 1.0));
        this.setAvg(convertToPercent(avg));

        double med = 0.0; // median
        if (scores.length > 1) {
            Arrays.sort(scores);
            int middle = scores.length / 2;
            if (scores.length % 2 == 1) {
                med = scores[middle] * 1.0;
            } else {
                med = (scores[middle - 1] + scores[middle]) / 2.0;
            }
        } else {
            med = scores[0];
        }

        this.setMedian(convertToPercent(med));
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
        return convertToPercent(Integer.parseInt(score) * 1.0);
    }

    private String convertToPercent(double score) {
        return Math.round((score / this.getTotalPoints()) * 100) + "%";
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
