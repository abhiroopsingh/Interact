package com.Interact.WebSocket;

import com.Interact.Entities.Questions;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import com.Interact.WebSocket.Device;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.spi.JsonProvider;
import org.primefaces.json.JSONObject;

@ApplicationScoped
public class DeviceSessionHandler {

    private int deviceId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Device> devices = new HashSet<>();
    private final Set<Questions> questions = new HashSet<>();

    public List<Device> getDevices() {
        return new ArrayList<>(devices);
    }

    public List<Questions> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void addQuestion(Questions question) {
        questions.add(question);        
        JSONObject addMessage = createAddMessage(question);
        
        sendToAllConnectedSessions(addMessage);
    }

    private JsonObject createAddMessage(Device device) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "update")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getStatus())
                .add("description", device.getDescription())
                .build();
        return addMessage;
    }

    private JSONObject createAddMessage(Questions question) {
        JSONObject addMessage = new JSONObject(question.toString());
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToAllConnectedSessions(JSONObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(DeviceSessionHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    private void sendToSession(Session session, JSONObject message) {
        System.out.println(message.toString());
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(DeviceSessionHandler.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    public void addSession(Session session) {
        sessions.add(session);
        for (Device device : devices) {
            JsonObject addMessage = createAddMessage(device);
            sendToSession(session, addMessage);
        }

    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

}
