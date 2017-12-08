package com.Interact.controllers;

import com.Interact.Entities.Questions;
import com.Interact.Entities.UserAnswers;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.json.JSONObject;
import org.primefaces.model.chart.PieChartModel;
 
@Named("liveChart")
@SessionScoped
public class LiveChart implements Serializable {
    
    @EJB
    private com.Interact.FacadeBeans.UserAnswersFacade userAnswersFacade;
    
    @EJB
    private com.Interact.FacadeBeans.QuestionsFacade questionsFacade;

    @Inject
    private SessionsController sessionsController;
    
    @Inject
    private MasterViewController masterViewController;
    
    private PieChartModel livePieModel;
 
    public PieChartModel getLivePieModel() {
        
        List<UserAnswers> userAnswers = userAnswersFacade.findBySession(
                sessionsController.getSelected().getId());
        Questions question = masterViewController.getQuestion();
        String questionId = String.valueOf(question.getId());
        Map<String, Integer> occurences = new HashMap<String, Integer>();
        for (UserAnswers u : userAnswers) {
            JSONObject jsonObject = new JSONObject(u.getAnswers());
            if (jsonObject.has(questionId)) {
                String answer = jsonObject.getString(questionId).toLowerCase();
                occurences.put(answer,
                        (occurences.containsKey(answer) ? occurences.get(answer) : 0) + 1);
            }
        }
        livePieModel = new PieChartModel();
        livePieModel.setTitle("Live Response Stats");
        if(occurences.isEmpty()){
            // To show the graph initially
            livePieModel.set("A", 0);
            
        }
        else{
            for(String option : occurences.keySet()){
                livePieModel.set(option, occurences.get(option));
            }
        }
        livePieModel.setLegendPosition("ne");
         
        return livePieModel;
    }
}