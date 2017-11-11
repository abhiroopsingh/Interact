window.onload = init;
var socket = new WebSocket("ws://localhost:8080/Interact/actions");
socket.onmessage = onMessage;

function onMessage(event) {
    var device = JSON.parse(event.data);

    console.log(device);

    var form = document.getElementById("studentView");
    var join_id = form.elements["joinKeyId"].value;

    console.log(join_id);

    if (device.session === join_id) {

        if (device.disable) {
            document.getElementById("studentView:submit").disabled = true;
        } else {
            document.getElementById("studentViewQuestion").innerHTML = device.question;

            if (device.A) {
                document.getElementById("studentView:studentViewOptionA").value = device.A;
            }
            if (device.B) {
                document.getElementById("studentView:studentViewOptionB").value = device.B;
            }
            if (device.C) {
                document.getElementById("studentView:studentViewOptionC").value = device.C;
            }
            if (device.D) {
                document.getElementById("studentView:studentViewOptionD").value = device.D;
            }
            if (device.E) {
                document.getElementById("studentView:studentViewOptionE").value = device.E;
            }
            qid([{name: 'questionID', value: device.id}]);
        }

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
    document.getElementById("masterView:broadcast").style.display = 'none';
    document.getElementById("masterView:unbroadcast").style.display = '';
}

function formSubmit(question) {
    var name = question;
    var type = "No errors";
    var description = "Hello. It Works!";
    addDevice(name, type, description);
}

function formDisable(question) {
    var DeviceAction = {
        action: "disable",
        name: question,
        type: null,
        decription: null
    };
    socket.send(JSON.stringify(DeviceAction));
    document.getElementById("masterView:unbroadcast").disabled = true;
}

function init() {
}