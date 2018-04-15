
/*** Eefine global variables ***/

let c, ctx;
// CHANGE THIS TO FALSE LATER.
let gameStart = false;

$(document).ready(() => {

	/*** Establish the WebSocket connection and set up event handlers ***/
    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/xx/websocket");
   	
   	webSocket.onopen = function(event) {
	  $("#socketStatus").innerHTML = 'Connected to: ' + event.currentTarget.url;
	};

	$("#getName").show();
	$("#game").hide();
	$("#waitingRoom").hide();
	
    // Send message if enter is pressed in the input field
    $("#codename").keypress(event => {
    	console.log("KEY: " + event.keyCode);
        if (event.keyCode === 13) { 
        	let name = { name: event.target.value };
        	webSocket.send(JSON.stringify(name)); 
        	$("#getName").fadeOut();
        	$("#waitingRoom").fadeIn();
        }
    });

    $("#start").click(event => {
    	// let status = {status: "start"};
    	// webSocket.send("hewwo");
    	// webSocket.send(JSON.stringify(status));
    	$("#waitingRoom").fadeOut();
    	$("#game").fadeIn();
    	gameStart = true;
    })


	// init();
	// drawPlayer();

    webSocket.onmessage = function (msg) { 
    	// console.log(JSON.parse(msg));
    	// let res = JSON.parse(msg);
    	// if (res.kind === "gamePost") {
    	// 	console.log("whomst")
    	// }
    	// if (res.kind === "gamePlayerList") {
    	// 	for (let i = 0; i < res.players.length; i++) {
    	// 		$("#users").append($("<li/>").html(res.players[i]));
    	// 	}
    	// }
    	// updateChat(msg); 
    };

    webSocket.onclose = function () { 
    	console.log("websocket connection closed.")
    	// alert("WebSocket connection closed") 
    };


    // Show a connected message when the WebSocket is opened.
	
	// Handle any errors that occur.
	webSocket.onerror = function(error) {
	  console.log(error);
	  console.log('WebSocket Error: ' + error);
	};

    

    // // Send a message if it's not empty, then clear the input field
    // function sendMessage(message) {
    //     if (message !== "") {
    //         webSocket.send(message);
    //         $("#message").val("");
    //     }
    // }

    // // Update the chat-panel, and the list of connected users
    // function updateChat(msg) {
    //     var data = JSON.parse(msg.data);
    //     $("<li/>").html(data.message).appendTo($("#chat"));
    //     $("userlist").innerHTML = "";
    //     data.userlist.forEach(function (user) {
    //         $("<li/>").html(user).appendTo($("#chat"));
    //     });
    // }




	/*** Interpreting keypress events ***/

	// $(document).keypress(event => {
	// 	console.log(event.keyCode);
	// 	if (gameStart) {
	// 		keySend(event.keyCode, webSocket);
	// 	}
	// });
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

function keySend(code, webSocket) {
	switch (code) {
		case 37: // left arrow
		case 97: // a for wasd
			webSocket.send("left");
			console.log("left!"); break;
		case 38: // up arrow
		case 119: // w in wasd
			webSocket.send("up");
			console.log("up!"); break;
		case 39: // right in wasd
		case 100: // d in wasd
			webSocket.send("right");
			console.log("right!"); break;
		case 40: // down arrow
		case 115: // s in wasd
			webSocket.send("down");
			console.log("down!"); break;
		case 32: // space bar for attack
			webSocket.send("space");
			console.log("space bar: attack!"); break;
		case 102: // f for items
			webSocket.send("f");
			console.log("f: pick up items?"); break;
		case 114: // r for radar
			webSocket.send("r");
			console.log("r: radar?"); break;
	}
}




