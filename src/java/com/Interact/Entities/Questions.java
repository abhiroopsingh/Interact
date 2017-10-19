/*
 * Created by Abhiroop Singh on 2017.10.06  * 
 * Copyright © 2017 Abhiroop Singh. All rights reserved. * 
 */
package com.Interact.Entities;

import java.io.Serializable;
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

/**
 *
 * @author Abhi
 */
@Entity
@Table(name = "Questions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q")
    , @NamedQuery(name = "Questions.findById", query = "SELECT q FROM Questions q WHERE q.id = :id")
    , @NamedQuery(name = "Questions.findByQuestion", query = "SELECT q FROM Questions q WHERE q.question = :question")
    , @NamedQuery(name = "Questions.findByQuestionType", query = "SELECT q FROM Questions q WHERE q.questionType = :questionType")
    , @NamedQuery(name = "Questions.findByAnswer", query = "SELECT q FROM Questions q WHERE q.answer = :answer")
    , @NamedQuery(name = "Questions.findByAnswerChoices", query = "SELECT q FROM Questions q WHERE q.answerChoices = :answerChoices")
    , @NamedQuery(name = "Questions.findBySessionId", query = "SELECT q FROM Questions q WHERE q.sessionId.id = :session_id")})
public class Questions implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "answerChoices")
    private String answerChoices;

    public Questions() {
    }

    public Questions(Integer id) {
        this.id = id;
    }

    public Questions(Integer id, String question, String questionType, String answer, String answerChoices) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.answer = answer;
        this.answerChoices = answerChoices;
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

    public String getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(String answerChoices) {
        this.answerChoices = answerChoices;
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Interact.Questions.Questions[ id=" + id + " ]";
    }

    public Sessions getSessionId() {
        return sessionId;
    }

    public void setSessionId(Sessions sessionId) {
        this.sessionId = sessionId;
    }
    
}
