/*
 * Created by Andy Sin on 2017.12.02  * 
 * Copyright © 2017 Andy Sin. All rights reserved. * 
 */
package com.Interact.controllers;

import com.Interact.Entities.Questions;
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
    private com.Interact.FacadeBeans.UserAnswersFacade userAnswersFacade;

    @Inject
    private SessionsController sessionsController;
    
    @Inject
    private ChartView chartView;

    private int a;
    private int b;
    private int c;
    private int d;
    private int e;

    private String firstMostCommonFree;
    private String secondMostCommonFree;
    private String thirdMostCommonFree;

    private int freeRight;
    private int freeWrong;

    private int totalResponses;

    private Map<String, Integer> occurences;

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

    public void prepareCreate(Questions question) {
        a = 0;
        b = 0;
        c = 0;
        d = 0;
        e = 0;

        firstMostCommonFree = null;
        secondMostCommonFree = null;
        thirdMostCommonFree = null;

        freeRight = 0;
        freeWrong = 0;
        totalResponses = 0;

        occurences = new HashMap<>();

        String sessionId = sessionsController.getSelected().getId();

        List<UserAnswers> userAnswers = userAnswersFacade.findBySession(
                sessionId);

        String questionId = String.valueOf(question.getId());

        for (UserAnswers u : userAnswers) {
            JSONObject jsonObject = new JSONObject(u.getAnswers());
            if (jsonObject.has(questionId)) {
                totalResponses++;
                String answer = jsonObject.getString(questionId).toLowerCase();
                occurences.put(answer,
                        (occurences.containsKey(answer) ? occurences.get(answer) : 0) + 1);
            }
        }
        if (question.getQuestionType().equals("Text Entry")) {
            findMostCommon(occurences);
            String correctAnswer = question.getAnswer();
            if (occurences.containsKey(correctAnswer.toLowerCase())) {
                freeRight = occurences.get(correctAnswer.toLowerCase());
            } else {
                freeRight = 0;
            }
            freeWrong = totalResponses - freeRight;
        } else {
            if (occurences.containsKey("a")) {
                a = occurences.get("a");
            }
            if (occurences.containsKey("b")) {
                b = occurences.get("b");
            }
            if (occurences.containsKey("c")) {
                c = occurences.get("c");
            }
            if (occurences.containsKey("d")) {
                d = occurences.get("d");
            }
            if (occurences.containsKey("e")) {
                e = occurences.get("e");
            }
            chartView.init();
        }

        System.out.println(occurences.toString());

    }

    private String convertToPercent(int responses, int total) {
        return Math.round((responses * 1.0 / total) * 100) + "%";
    }

    private long convertToLong(int responses, int total) {
        return Math.round((responses * 1.0 / total) * 100);
    }

    private void findMostCommon(Map<String, Integer> map) {
        PriorityQueue<Answer> queue = new PriorityQueue<>();
        for (String s : map.keySet()) {
            queue.offer(new Answer(s, map.get(s)));
        }
        firstMostCommonFree = queue.size() > 0 ? queue.poll().answer : null;
        secondMostCommonFree = queue.size() > 0 ? queue.poll().answer : null;
        thirdMostCommonFree = queue.size() > 0 ? queue.poll().answer : null;
    }

    public String getTopAnswers() {
        String result = "";
        if (firstMostCommonFree != null) {
            result += firstMostCommonFree;
        }
        if (secondMostCommonFree != null) {
            result += ", " + secondMostCommonFree;
        }
        if (thirdMostCommonFree != null) {
            result += ", " + thirdMostCommonFree;
        }
        return result;
    }

    public String getA() {
        return convertToPercent(a, totalResponses);
    }

    public String getB() {
        return convertToPercent(b, totalResponses);
    }

    public String getC() {
        return convertToPercent(c, totalResponses);
    }

    public String getD() {
        return convertToPercent(d, totalResponses);
    }

    public String getE() {
        return convertToPercent(e, totalResponses);
    }

    public long getAInt() {
        return convertToLong(a, totalResponses);
    }

    public long getBInt() {
        return convertToLong(b, totalResponses);
    }

    public long getCInt() {
        return convertToLong(c, totalResponses);
    }

    public long getDInt() {
        return convertToLong(d, totalResponses);
    }

    public long getEInt() {
        return convertToLong(e, totalResponses);
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

    public String getFreeRight() {
        return convertToPercent(freeRight, totalResponses);
    }

    public void setFreeRight(int freeRight) {
        this.freeRight = freeRight;
    }

    public String getFreeWrong() {
        return convertToPercent(freeWrong, totalResponses);
    }

    public void setFreeWrong(int freeWrong) {
        this.freeWrong = freeWrong;
    }

    public int getTotalResponses() {
        return totalResponses;
    }
}
