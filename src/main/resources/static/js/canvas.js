
/*** Eefine global variables ***/

let c, ctx;

$(document).ready(() => {
	init();
	drawPlayer();


		
    /*** Establish the WebSocket connection and set up event handlers ***/

    console.log("ws:" + location.hostname + ":" + location.port + "/game/websocket")
    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/xx/websocket");
    webSocket.onmessage = function (msg) { updateChat(msg); };
    webSocket.onclose = function () { alert("WebSocket connection closed") };
    // Show a connected message when the WebSocket is opened.
	webSocket.onopen = function(event) {
	  $("#socketStatus").innerHTML = 'Connected to: ' + event.currentTarget.url;
	};
	// Handle any errors that occur.
	webSocket.onerror = function(error) {
	  console.log(error);
	  console.log('WebSocket Error: ' + error);
	};

    // Send message if "Send" is clicked
    $("#send").click(function() {
        sendMessage($("#message").val());
    });

    // Send message if enter is pressed in the input field
    $("#message").keypress(event => function (e) {
        if (e.keyCode === 13) { sendMessage(e.target.value); }
    });

    // Send a message if it's not empty, then clear the input field
    function sendMessage(message) {
        if (message !== "") {
            webSocket.send(message);
            $("#message").val("");
        }
    }

    // Update the chat-panel, and the list of connected users
    function updateChat(msg) {
        var data = JSON.parse(msg.data);
        $("<li/>").html(data.message).appendTo($("#chat"));
        $("userlist").innerHTML = "";
        data.userlist.forEach(function (user) {
            $("<li/>").html(user).appendTo($("#chat"));
        });
    }




	/*** Interpreting keypress events ***/

	$(document).keypress(event => {
		
		code = event.keyCode;
		console.log("called");
		console.log("called thing is " + code);
		switch (code) {
			case 37: // left arrow
			case 97: // a for wasd
				console.log("left!"); break;
			case 38: // up arrow
			case 119: // w in wasd
				console.log("up!"); break;
			case 39: // right in wasd
			case 100: // d in wasd
				console.log("right!"); break;
			case 40: // down arrow
			case 115: // s in wasd
				console.log("down!"); break;
			case 32: // space bar for attack
				console.log("space bar: attack!"); break;
			case 102: // f for items
				console.log("f: pick up items?"); break;
			case 114: // r for radar
				console.log("r: radar?"); break;
			}
	});
});

//initializes canvas with context
function init() {
	c = document.getElementById("gameCanvas");
	ctx = c.getContext("2d");
	c.width = 500;
	c.height = 500;
	//drawplayer
	//add eventlisteners for motion and for picking up stuff
};

function drawPlayer() {
	ctx.beginPath();
	ctx.strokeStyle = "black";
	ctx.lineWidth = 2;
	ctx.arc(c.width/2, c.height/2, 25, 0, 2*Math.PI);
	ctx.stroke();
}

//draw square
  // - weapon
  // - item
  // - decorations
function drawSquare(x, y) {
	ctx.beginPath();
	ctx.strokeStyle = "black";

}
//draw circle
 // - player

// upon keypress


// // move things with keyboard
// function check(e) {
// 	code = e.keyCode;
// 	console.log("called");
// 	switch (code) {
// 		case 37: // left arrow
// 		case 65: // a for wasd
// 			console.log("left!"); break;
// 		case 38: // up arrow
// 		case 87: // w in wasd
// 			console.log("up!"); break;
// 		case 39: // right in wasd
// 		case 68: // d in wasd
// 			console.log("right!"); break;
// 		case 40: // down arrow
// 		case 83: // s in wasd
// 			console.log("down!"); break;
// 		// 

// 	}
// }
