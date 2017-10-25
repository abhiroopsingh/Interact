/*
 * Created by Akhil Gangu on 2017.10.13  * 
 * Copyright © 2017 Akhil Gangu. All rights reserved. * 
 */
package com.Interact.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Akhil
 */
@Entity
@Table(name = "Sessions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sessions.findAll", query = "SELECT s FROM Sessions s")
    , @NamedQuery(name = "Sessions.findById", query = "SELECT s FROM Sessions s WHERE s.id = :id")
    , @NamedQuery(name = "Sessions.findByActive", query = "SELECT s FROM Sessions s WHERE s.active = :active")
    , @NamedQuery(name = "Sessions.findOwnedSessions", query = "SELECT s FROM Sessions s WHERE s.master.username = :master")})
public class Sessions implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "sessionId")
    private Collection<UserAnswers> userAnswersCollection;
    @JoinColumn(name = "master", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users master;
    @OneToMany(mappedBy = "sessionId")
    private Collection<Questions> questionsCollection;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "sessionName")
    private String sessionName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public Sessions() {
    }

    public Sessions(String id) {
        this.id = id;
    }

    public Sessions(String id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public Sessions(String id, boolean active, Users master) {
        this.id = id;
        this.active = active;
        this.master = master;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getActive() {

        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Collection<UserAnswers> getUserAnswersCollection() {
        return userAnswersCollection;
    }

    public void setUserAnswersCollection(
            Collection<UserAnswers> userAnswersCollection) {
        this.userAnswersCollection = userAnswersCollection;
    }

    public Users getMaster() {
        return master;
    }

    public void setMaster(Users master) {
        this.master = master;
    }

    @XmlTransient
    public Collection<Questions> getQuestionsCollection() {
        return questionsCollection;
    }

    public void setQuestionsCollection(Collection<Questions> questionsCollection) {
        this.questionsCollection = questionsCollection;
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
        if (!(object instanceof Sessions)) {
            return false;
        }
        Sessions other = (Sessions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.
                equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Interact.Questions.Sessions[ id=" + id + " ]";
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
