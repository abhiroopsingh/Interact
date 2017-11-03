/*
 * Created by Abhiroop Singh on 2017.10.03  * 
 * Copyright © 2017 Abhiroop Singh. All rights reserved. * 
 */

import com.Interact.Entities.Questions;
import com.Interact.Entities.UserAnswers;
import com.Interact.FacadeBeans.SessionsFacade;
import com.Interact.FacadeBeans.UserAnswersFacade;
import com.Interact.controllers.QuestionsController;
import com.Interact.controllers.SessionsController;
import com.Interact.controllers.UserAnswersController;
import com.Interact.managers.AccountManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Abhi
 */
@Named("studentViewController")
@SessionScoped
public class StudentViewController implements Serializable {

    @Inject
    private QuestionsController questionsController;
    @Inject
    private SessionsController sessionsController;
    @Inject
    UserAnswersController userAnswersController;
    @Inject
    private AccountManager accountManager;

    @Inject
    private SessionsFacade sessionsFacade;
    @Inject
    private UserAnswersFacade userAnswersFacade;

    private String answer = "";
    private String questionId = "";
    private List<Questions> sessionQuestions = null;
    private int questionNum = 0;

    public StudentViewController() {
    }

    public void submit() {

        UserAnswers a = new UserAnswers();
        a.setUsername(accountManager.getSelected());
        a.setSessionId(sessionsFacade.findById(sessionsController.getJoinKey()));

        List<UserAnswers> b = userAnswersFacade.findByUsernameAndSession(a.getUsername().getUsername(), a.getSessionId().getId());
        if (b.size() == 0) {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put(this.questionId, this.answer);
            JSONObject json = new JSONObject(attributes);

            a.setAnswers(json.toString());

            userAnswersController.setSelected(a);
            userAnswersController.create();

        } else {

            UserAnswers c = b.get(0);
            JSONObject json = new JSONObject(c.getAnswers());

            json.put(this.questionId, this.answer);
            c.setAnswers(json.toString());

            userAnswersController.setSelected(c);
            userAnswersController.update();

        }

    }

    public void exec() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().get("questionID");

        questionId = session_id;

    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {

        System.out.println("Set Answer: " + answer);

        this.answer = answer;
    }

    public void makeA() {
        setAnswer("A");
    }

    public void makeB() {
        setAnswer("B");
    }

    public void makeC() {
        setAnswer("C");
    }

    public void makeD() {
        setAnswer("D");
    }

    public void makeE() {
        setAnswer("E");
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
