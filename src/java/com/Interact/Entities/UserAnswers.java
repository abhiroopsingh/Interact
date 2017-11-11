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
@Table(name = "UserAnswers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAnswers.findAll", query = "SELECT u FROM UserAnswers u")
    , @NamedQuery(name = "UserAnswers.findById", query = "SELECT u FROM UserAnswers u WHERE u.id = :id")
    , @NamedQuery(name = "UserAnswers.findByAnswers", query = "SELECT u FROM UserAnswers u WHERE u.answers = :answers")
    , @NamedQuery(name = "UserAnswers.findByUsernameAndSession", query = "SELECT u FROM UserAnswers u WHERE u.username.username = :username AND u.sessionId.id = :session_id" )
    , @NamedQuery(name = "UserAnswers.findBySession", query = "SELECT u FROM UserAnswers u WHERE u.sessionId.id = :session_id" )})
public class UserAnswers implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "grade")
    private String grade;

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
    @Column(name = "answers")
    private String answers;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users username;

    public UserAnswers() {
    }

    public UserAnswers(Integer id) {
        this.id = id;
    }

    public UserAnswers(Integer id, String answers) {
        this.id = id;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
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
        if (!(object instanceof UserAnswers)) {
            return false;
        }
        UserAnswers other = (UserAnswers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Interact.Questions.UserAnswers[ id=" + id + " ]";
    }

    public Sessions getSessionId() {
        return sessionId;
    }

    public void setSessionId(Sessions sessionId) {
        this.sessionId = sessionId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
