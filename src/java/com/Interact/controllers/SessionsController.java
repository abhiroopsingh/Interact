package com.Interact.controllers;

import com.Interact.Entities.Questions;
import com.Interact.Entities.Sessions;
import com.Interact.Entities.UserAnswers;
import com.Interact.controllers.util.JsfUtil;
import com.Interact.controllers.util.JsfUtil.PersistAction;
import com.Interact.FacadeBeans.SessionsFacade;
import com.Interact.FacadeBeans.UserAnswersFacade;
import com.Interact.managers.AccountManager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named("sessionsController")
@SessionScoped
public class SessionsController implements Serializable {

    @EJB
    private com.Interact.FacadeBeans.SessionsFacade ejbFacade;
    @EJB
    private com.Interact.FacadeBeans.QuestionsFacade questionsFacade;
    private List<Sessions> items = null;
    private Sessions selected;

    @Inject
    private AccountManager accountManager;

    @Inject
    private QuestionsController questionsController;
    
    @Inject
    private UserAnswersFacade userAnswersFacade;
    
    @Inject
    private MasterViewController masterViewController;

    @Inject
    private UserAnswersController userAnswersController;
    private List<Sessions> ownedSessions = null;

    final static char[] candidates = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".
            toCharArray();
    final static int ID_SIZE = 16;

    private String joinKey;

    public SessionsController() {
    }

    public Sessions getSelected() {
        return selected;
    }

    public void setSelected(Sessions selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public String getJoinKey() {
        return joinKey;
    }

    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey;
    }

    private SessionsFacade getFacade() {
        return ejbFacade;
    }

    public String initSession() {
        prepareCreate();
        return "CreateSession?faces-redirect=true";
    }

    public String join() {
        return "JoinSession?faces-redirect=true";
    }

    public String backIsClicked() {
        joinKey = null;
        return "UserHomePage?faces-redirect=true";
    }

    public String startSession() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().
                get("sessId");
        Sessions sess = getSessions(session_id);
        setSelected(sess);
        getSelected().setStatus(1);
        update();
        joinKey = getSelected().getId();
        masterViewController.prepare();
        return "MasterJoinSession?faces-redirect=true";
    }
    
    public void endSession() {
        setJoinKey(null);
        getSelected().setStatus(2);
        update();
    }
    
    public boolean sessionOver(String sessionId){
        Sessions session = ejbFacade.findById(sessionId);
        return session.getStatus() == 2;
    }
    
    public int numQuestions(String sessionId){
        return questionsFacade.findBySessionId(sessionId).size();
    }

    public Sessions prepareCreate() {
        selected = new Sessions(generateId(), 0, accountManager.
                getSelected());
        selected.setDateModified(new Date());
        initializeEmbeddableKey();
        return selected;
    }
    
    public void prepareJoin() {
        
    }

    public String create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").
                getString("SessionsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }

        return "CreateSession?faces-redirect=true";

    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").
                getString("SessionsUpdated"));
    }

    public void destroy() {

        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().
                get("deleteId");

        Sessions sess = getSessions(session_id);
        setSelected(sess);

        List<Questions> deleteQuestions = questionsController.getItems();
        List<UserAnswers> userAnswer = userAnswersFacade.findBySession(sess.getId());
        
        for (UserAnswers u : userAnswer) {
            
            userAnswersController.setSelected(u);
            userAnswersController.destroy();
        }
       
        for (Questions q : deleteQuestions) {

            questionsController.setSelected(q);
            questionsController.destroy();
        }

        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").
                getString("SessionsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Sessions> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle(
                            "/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").
                        getString("PersistenceErrorOccured"));
            }
        }
    }

    public Sessions getSessions(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Sessions> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Sessions> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Sessions> getOwnedSessions() {
        String username = accountManager.getSelected().getUsername();

        return getFacade().findOwnedSessions(username);
    }

    public void setOwnedSessions(List<Sessions> ownedSessions) {
        this.ownedSessions = ownedSessions;
    }

    public String routeSessions() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String session_id = fc.getExternalContext().getRequestParameterMap().
                get("sessId");

        Sessions sess = getSessions(session_id);
        setSelected(sess);

        return "CreateSession.xhtml?faces-redirect=true";
    }

    @FacesConverter(forClass = Sessions.class)
    public static class SessionsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext,
                UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SessionsController controller = (SessionsController) facesContext.
                    getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null,
                            "sessionsController");
            return controller.getSessions(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Sessions) {
                Sessions o = (Sessions) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(),
                            Sessions.class.getName()});
                return null;
            }
        }

    }

    private String generateId() {
        Random random = new Random();
        char[] id = new char[ID_SIZE];
        for (int x = 0; x < ID_SIZE; x++) {
            id[x] = candidates[random.nextInt(ID_SIZE)];
            id[x] = candidates[random.nextInt(candidates.length)];
        }
        return new String(id);
    }

}
