var stompClient = null;

/*
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
*/

function connectDirect(queue) {
    var socket = new SockJS('/my-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe(queue, function (greeting) {
            console.log("Received message in queue.");
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function sendName() {
    fetch('http://localhost:8080/hello', {
        method: 'POST',
        body: JSON.stringify({name:$('#name').val()}),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(function(response){
        response.text().then(function(queue){
            connectDirect(queue);
        });
      });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

/*
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
*/

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    /*$( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });*/
    $( "#send" ).click(function() { sendName(); });
});