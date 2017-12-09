window.onload = init;
var socket = new WebSocket("ws://ec2-34-216-124-131.us-west-2.compute.amazonaws.com:8080/Interact/actions");
socket.onmessage = onMessage;

function onMessage(event) {

    var device = JSON.parse(event.data);


    if (device.end) {
        console.log("END");

        end([{name: 'endSession', value: true}]);

        return;
    }

    console.log(device);

    var form = document.getElementById("studentView");
    var join_id = form.elements["joinKeyId"].value;

    console.log("Join Key ID: " + join_id)

    if (device.session === join_id) {
        
        console.log("MATCHES ID");

        if (device.disable) {
            document.getElementById("studentView:submit").style.display = "none";
        } else {
            document.getElementById("studentView:waitField").style.display = "none";
            document.getElementById("studentView:submit").disabled = false;
            document.getElementById("studentViewQuestion").innerHTML = device.question;
            
            if (device.questionType === "Text Entry") {
                document.getElementById("studentView:studentResponse").style.display = "block";
                document.getElementById("studentView:studentViewOptionA").style.display = "none";
                document.getElementById("studentView:studentViewOptionB").style.display = "none";
                document.getElementById("studentView:studentViewOptionC").style.display = "none";
                document.getElementById("studentView:studentViewOptionD").style.display = "none";
                document.getElementById("studentView:studentViewOptionE").style.display = "none";
                document.getElementById("studentView:submit").style.display = "block";
                qid([{name: 'questionID', value: device.id}]);
                return;
            }
            document.getElementById("studentView:studentResponse").style.display = "none";
            if (device.A) {
                document.getElementById("studentView:studentViewOptionA").value = ("A. " + device.A);
                document.getElementById("studentView:studentViewOptionA").style.display = "block";
                document.getElementById("studentView:studentViewOptionB").style.display = "none";
                document.getElementById("studentView:studentViewOptionC").style.display = "none";
                document.getElementById("studentView:studentViewOptionD").style.display = "none";
                document.getElementById("studentView:studentViewOptionE").style.display = "none";
            }

            if (device.B) {
                document.getElementById("studentView:studentViewOptionB").value = ("B. " + device.B);
                document.getElementById("studentView:studentViewOptionB").style.display = "block";
                document.getElementById("studentView:studentViewOptionC").style.display = "none";
                document.getElementById("studentView:studentViewOptionD").style.display = "none";
                document.getElementById("studentView:studentViewOptionE").style.display = "none";
            }

            if (device.C) {
                document.getElementById("studentView:studentViewOptionC").value = ("C. " + device.C);
                document.getElementById("studentView:studentViewOptionC").style.display = "block";
                document.getElementById("studentView:studentViewOptionD").style.display = "none";
                document.getElementById("studentView:studentViewOptionE").style.display = "none";
            }

            if (device.D) {
                document.getElementById("studentView:studentViewOptionD").value = ("D. " + device.D);
                document.getElementById("studentView:studentViewOptionD").style.display = "block";
                document.getElementById("studentView:studentViewOptionE").style.display = "none";
            }

            if (device.E) {
                document.getElementById("studentView:studentViewOptionE").value = ("E. " + device.E);
                document.getElementById("studentView:studentViewOptionE").style.display = "block";
            }
            
            document.getElementById("studentView:submit").style.display = "block";

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
        type: "",
        decription: ""
    };
    socket.send(JSON.stringify(DeviceAction));
    document.getElementById("masterView:unbroadcast").style.display = 'none';
}

function endSession() {

    var DeviceAction = {
        action: "a",
        name: "b",
        type: "end",
        description: null
    };
    
    console.log("HERE");
    
    socket.send(JSON.stringify(DeviceAction));
}

function init() {
}
