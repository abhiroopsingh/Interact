window.onload = init;
var socket = new WebSocket("ws://localhost:8080/Interact/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);
    
    document.getElementById("studentViewQuestion").innerHTML = device.question;
    
    if(device.A) {
        document.getElementById("studentViewOptionA").innerHTML = device.A;
    }
    if(device.B) {
        document.getElementById("studentViewOptionB").innerHTML = device.B;
    }
    if(device.C) {
        document.getElementById("studentViewOptionC").innerHTML = device.C;
    }
    if(device.D) {
        document.getElementById("studentViewOptionD").innerHTML = device.D;
    }
    if(device.E) {
        document.getElementById("studentViewOptionE").innerHTML = device.E;
    }
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