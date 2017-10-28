window.onload = init;
var socket = new WebSocket("ws://localhost:8080/Interact/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);
    document.getElementById("demo").innerHTML = device.question;
}

function addDevice(name, type, description) {
    var DeviceAction = {
        action: "update",
        name: name,
        type: type,
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));
}

function formSubmit(question) {

    var name = question;
    var type = "Text Entry";
    var description = "Hello. It Works!";
    addDevice(name, type, description);
}

function init() {
}