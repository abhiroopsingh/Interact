/*
 * Created by Andy Sin on 2017.12.02  * 
 * Copyright © 2017 Andy Sin. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.UserAnswers;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Andy
 */
@Named("statsController")
@SessionScoped
public class StatsController implements Serializable {

    @EJB
    private com.Interact.FacadeBeans.SessionsFacade sessionsFacade;

    @EJB
    private com.Interact.FacadeBeans.UserAnswersFacade userAnswersFacade;

    @Inject
    private SessionsController sessionsController;

    private String a = "0%";
    private String b = "0%";
    private String c = "0%";
    private String d = "0%";
    private String e = "0%";

    private String firstMostCommonFree;
    private String secondMostCommonFree;
    private String thirdMostCommonFree;

    public StatsController() {

    }
    
    private class Answer implements Comparable<Answer> {
        String answer;
        int occurences;
        private Answer(String s, int i) {
            answer = s;
            occurences = i;
        }

        @Override
        public int compareTo(Answer o) {
            return o.occurences - this.occurences;
        }
    }

    public void prepareCreate(String questionId) {
        String sessionId = sessionsController.getSelected().getId();

        List<UserAnswers> userAnswers = userAnswersFacade.findBySession(
                sessionId);

        Map<String, Integer> occurences = new HashMap<>();
        int totalResponses = 0;

        for (UserAnswers u : userAnswers) {
            JSONObject jsonObject = new JSONObject(u.getAnswers());
            if (jsonObject.has(questionId)) {
                totalResponses++;
                String answer = jsonObject.getString(questionId).toLowerCase();
                occurences.put(answer,
                        (occurences.containsKey(answer) ? occurences.get(answer) : 0) + 1);
            }
        }
        if (occurences.containsKey("A")) {
            setA(convertToPercent(occurences.get("A"), totalResponses));
        }
        if (occurences.containsKey("B")) {
            setB(convertToPercent(occurences.get("B"), totalResponses));
        }
        if (occurences.containsKey("C")) {
            setC(convertToPercent(occurences.get("C"), totalResponses));
        }
        if (occurences.containsKey("D")) {
            setD(convertToPercent(occurences.get("D"), totalResponses));
        }
        if (occurences.containsKey("E")) {
            setE(convertToPercent(occurences.get("E"), totalResponses));
        }
        findMostCommon(occurences);
    }

    private String convertToPercent(int responses, int total) {
        return Math.round((responses * 1.0 / total) * 100) + "%";
    }

    private void findMostCommon(Map<String, Integer> map) {
        PriorityQueue<Answer> queue = new PriorityQueue<>();
        for(String s : map.keySet()) {
            queue.offer(new Answer(s, map.get(s)));
        }
        firstMostCommonFree = queue.size() > 0 ? queue.poll().answer : "N/A";
        secondMostCommonFree = queue.size() > 0 ? queue.poll().answer : "N/A";
        thirdMostCommonFree = queue.size() > 0 ? queue.poll().answer : "N/A";
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getFirstMostCommonFree() {
        return firstMostCommonFree;
    }

    public void setFirstMostCommonFree(String firstMostCommonFree) {
        this.firstMostCommonFree = firstMostCommonFree;
    }

    public String getSecondMostCommonFree() {
        return secondMostCommonFree;
    }

    public void setSecondMostCommonFree(String secondMostCommonFree) {
        this.secondMostCommonFree = secondMostCommonFree;
    }

    public String getThirdMostCommonFree() {
        return thirdMostCommonFree;
    }

    public void setThirdMostCommonFree(String thirdMostCommonFree) {
        this.thirdMostCommonFree = thirdMostCommonFree;
    }

}
