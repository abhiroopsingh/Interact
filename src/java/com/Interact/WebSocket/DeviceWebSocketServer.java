package com.Interact.WebSocket;

import com.Interact.Entities.Questions;
import com.Interact.FacadeBeans.SessionsFacade;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.primefaces.json.JSONObject;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {

    @Inject
    private DeviceSessionHandler sessionHandler;

    @Inject
    private SessionsFacade facade;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);

    }

    @OnClose
    public void close(Session session) {

        sessionHandler.removeSession(session);

    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebSocketServer.class.getName()).
                log(Level.SEVERE, null, error);

    }

    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage1 = reader.readObject();
            JsonReader reader2 = Json.createReader(new StringReader(
                    jsonMessage1.getString("name")));
            JsonObject jsonMessage = reader2.readObject();
            Questions question = new Questions(Integer.valueOf(jsonMessage.
                    getString("id")),
                    jsonMessage.getString("question"), jsonMessage.
                    getString(
                            "questionType"), jsonMessage.getString("answer"));
            question.setMultipleChoiceA(
                    jsonMessage.isNull("A") ? null : jsonMessage.
                    getString("A"));
            question.setMultipleChoiceB(
                    jsonMessage.isNull("B") ? null : jsonMessage.
                    getString("B"));
            question.setMultipleChoiceC(
                    jsonMessage.isNull("C") ? null : jsonMessage.
                    getString("C"));
            question.setMultipleChoiceD(
                    jsonMessage.isNull("D") ? null : jsonMessage.
                    getString("D"));
            question.setMultipleChoiceE(
                    jsonMessage.isNull("E") ? null : jsonMessage.
                    getString("E"));
            question.setSessionId(facade.findById(jsonMessage.getString(
                    "session")));

            System.out.println("Socket Server Class: " + jsonMessage.
                    getString("session"));

            String checker = jsonMessage1.getString("action");
            if (checker.equals("disable")) {
                sessionHandler.disableSubmit(question);
            } else {

                sessionHandler.addQuestion(question);
            }
        }
    }
}
