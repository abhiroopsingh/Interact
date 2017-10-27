/*
 * Created by Abhiroop Singh on 2017.10.06  * 
 * Copyright © 2017 Abhiroop Singh. All rights reserved. * 
 */
package com.Interact.Entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Abhi
 */
@Entity
@Table(name = "Questions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q")
    , @NamedQuery(name = "Questions.findById",
            query = "SELECT q FROM Questions q WHERE q.id = :id")
    , @NamedQuery(name = "Questions.findByQuestion",
            query = "SELECT q FROM Questions q WHERE q.question = :question")
    , @NamedQuery(name = "Questions.findByQuestionType",
            query = "SELECT q FROM Questions q WHERE q.questionType = :questionType")
    , @NamedQuery(name = "Questions.findByAnswer",
            query = "SELECT q FROM Questions q WHERE q.answer = :answer")
    , @NamedQuery(name = "Questions.findBySessionId",
            query = "SELECT q FROM Questions q WHERE q.sessionId.id = :session_id")})
public class Questions implements Serializable {

    @Size(max = 1000)
    @Column(name = "multipleChoiceA")
    private String multipleChoiceA;
    @Size(max = 1000)
    @Column(name = "multipleChoiceB")
    private String multipleChoiceB;
    @Size(max = 1000)
    @Column(name = "multipleChoiceC")
    private String multipleChoiceC;
    @Size(max = 1000)
    @Column(name = "multipleChoiceD")
    private String multipleChoiceD;
    @Size(max = 1000)
    @Column(name = "multipleChoiceE")
    private String multipleChoiceE;

    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @ManyToOne
    private Sessions sessionId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "questionType")
    private String questionType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "answer")
    private String answer;

    public Questions() {
    }

    public Questions(Integer id) {
        this.id = id;
    }

    public Questions(Integer id, String question, String questionType,
            String answer) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.
                equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", String.valueOf(id));
        attributes.put("question", question);
        attributes.put("answer", answer);
        attributes.put("questionType", questionType);
        attributes.put("A",
                multipleChoiceA == null ? JSONObject.NULL : multipleChoiceA);
        attributes.put("B",
                multipleChoiceB == null ? JSONObject.NULL : multipleChoiceB);
        attributes.put("C",
                multipleChoiceC == null ? JSONObject.NULL : multipleChoiceC);
        attributes.put("D",
                multipleChoiceD == null ? JSONObject.NULL : multipleChoiceD);
        attributes.put("E",
                multipleChoiceE == null ? JSONObject.NULL : multipleChoiceE);
        JSONObject json = new JSONObject(attributes);
        return json.toString();
    }

    public Sessions getSessionId() {
        return sessionId;
    }

    public void setSessionId(Sessions sessionId) {
        this.sessionId = sessionId;
    }

    public String getMultipleChoiceA() {
        return multipleChoiceA;
    }

    public void setMultipleChoiceA(String multipleChoiceA) {
        this.multipleChoiceA = multipleChoiceA;
    }

    public String getMultipleChoiceB() {
        return multipleChoiceB;
    }

    public void setMultipleChoiceB(String multipleChoiceB) {
        this.multipleChoiceB = multipleChoiceB;
    }

    public String getMultipleChoiceC() {
        return multipleChoiceC;
    }

    public void setMultipleChoiceC(String multipleChoiceC) {
        this.multipleChoiceC = multipleChoiceC;
    }

    public String getMultipleChoiceD() {
        return multipleChoiceD;
    }

    public void setMultipleChoiceD(String multipleChoiceD) {
        this.multipleChoiceD = multipleChoiceD;
    }

    public String getMultipleChoiceE() {
        return multipleChoiceE;
    }

    public void setMultipleChoiceE(String multipleChoiceE) {
        this.multipleChoiceE = multipleChoiceE;
    }

}
