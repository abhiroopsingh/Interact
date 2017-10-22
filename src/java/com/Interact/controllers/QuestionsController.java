package com.Interact.controllers;

import com.Interact.Entities.Questions;
import com.Interact.controllers.util.JsfUtil;
import com.Interact.controllers.util.JsfUtil.PersistAction;
import com.Interact.FacadeBeans.QuestionsFacade;

import java.io.Serializable;
import java.util.List;
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

@Named("questionsController")
@SessionScoped
public class QuestionsController implements Serializable {

    @EJB
    private com.Interact.FacadeBeans.QuestionsFacade ejbFacade;
    private List<Questions> items = null;
    private List<Questions> sessionItems = null;
    private Questions selected;
    private String optionA = "", optionB = "", optionC = "", optionD = "";
    private final String OPTION_DELIM = "|$|";
    
    @Inject
    private SessionsController sessionsController;

    public QuestionsController() {
    }

    public Questions getSelected() {
        return selected;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void setSelected(Questions selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestionsFacade getFacade() {
        return ejbFacade;
    }

    public Questions prepareCreate() {
        selected = new Questions();
        selected.setSessionId(sessionsController.getSelected());
        initializeEmbeddableKey();
        return selected;
    }
    
    private String encodeOptions(){
        return optionA + OPTION_DELIM + optionB + OPTION_DELIM + optionC + OPTION_DELIM + optionD;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestionsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestionsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").
                getString("QuestionsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Questions> getItems() {
        items = getFacade().findBySessionId(sessionsController.getSelected().
                getId());
        return items;
    }

    public List<Questions> getSessionItems() {
        return getFacade().findBySessionId(sessionsController.getJoinKey());
    }

    public void setSessionItems(List<Questions> sessionItems) {
        this.sessionItems = sessionItems;
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

    public Questions getQuestions(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Questions> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Questions> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Questions.class)
    public static class QuestionsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext,
                UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionsController controller = (QuestionsController) facesContext.
                    getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null,
                            "questionsController");
            return controller.getQuestions(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
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
            if (object instanceof Questions) {
                Questions o = (Questions) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(),
                            Questions.class.getName()});
                return null;
            }
        }

    }

}