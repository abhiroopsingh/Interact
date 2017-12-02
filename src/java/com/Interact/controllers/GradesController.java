/*
 * Created by Andy Sin on 2017.12.01  * 
 * Copyright © 2017 Andy Sin. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.UserAnswers;
import com.Interact.FacadeBeans.QuestionsFacade;
import com.Interact.FacadeBeans.UserAnswersFacade;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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

    @Inject
    private QuestionsFacade questionsFacade;

    public GradesController() {

    }

    public String getClassAvg(String sessionId) {
        List<UserAnswers> sessionAnswers = userAnswersFacade.findBySession(
                sessionId);
        double avg = 0.0;
        for (int k = 0; k < sessionAnswers.size(); k++) {
            int grade = Integer.parseInt(sessionAnswers.get(k).getGrade());
            avg += grade / (sessionAnswers.size() * 1.0);
        }
        return convertToPercent(avg, getTotalPoints(sessionId));
    }

    public String getClassMedian(String sessionId) {
        List<UserAnswers> sessionAnswers = userAnswersFacade.findBySession(
                sessionId);
        int[] scores = new int[sessionAnswers.size()];
        double med = 0.0;
        for (int k = 0; k < sessionAnswers.size(); k++) {
            scores[k] = Integer.parseInt(sessionAnswers.get(k).getGrade());
        }
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
        return convertToPercent(med, getTotalPoints(sessionId));
    }

    public int getTotalPoints(String sessionId) {
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
}