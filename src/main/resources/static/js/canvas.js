
/*** Define global variables ***/

let c, ctx, offsetX, offsetY, mapHeight, staticEntities;
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
    	let status = {status: "start"};
    	webSocket.send("start");
    	$("#waitingRoom").fadeOut();
    	$("#game").fadeIn();
    	gameStart = true;
    	init();
    })


	// init();
	// drawPlayer();

    webSocket.onmessage = function (msg) {
    	// console.log(msg);
    	// console.log(msg.data) 
    	console.log(JSON.parse(msg.data));
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

	/*** Interpreting keypress events ***/

	$(document).keypress(event => {
		if (gameStart) {
			keySend(event.keyCode, webSocket);
		}
	});
});



/*** CANVAS SPECIFIC FUNCTIONS ***/

//initializes canvas with context
function init() {
	c = document.getElementById("gameCanvas");
	ctx = c.getContext("2d");
	c.width = 500;
	c.height = 500;
	offsetX = 0;
	offsetY = 0;
	staticEntities = [
		{x:20, y:60, type:"weapon"},
		{x:420, y:390, type: "item"},
		{x: 333, y:270, type:"deco"}
	];
	drawPlayer();
	drawDummies();

};

function drawPlayer() {
	ctx.beginPath();
	ctx.strokeStyle = "#b8dbd9";
	ctx.lineWidth = 2;
	ctx.arc(c.width/2, c.height/2, 25, 0, 2*Math.PI);
	ctx.stroke();
}

function movePlayer(direction) {
	switch(direction) {
		case "left":
			offsetX+=10; break;
		case "right":
			offsetX-=10; break;
		case "up":
			offsetY+=10; break;
		case "down":
			offsetY-=10; break;
	}
	clearCanvas();
	drawDummies();
	drawPlayer();
}

// clears canvas to redraw items.
function clearCanvas() {
	ctx.clearRect(0, 0, c.width, c.height);
}


// draws square. type = weapon, item, or decoration
function drawSquare(x, y, type) {
	ctx.beginPath();
	ctx.rect(x, y, 50, 50);
	ctx.fillStyle = "white";
	switch(type) {
		case "weapon":
			ctx.fillStyle = "red";
			// maybe change color?? can pick up
			break;
		case "item":
			ctx.fillStyle = "white";
			// change color ??? can pick up
			break;
		case "deco":
			ctx.fillStyle = "green";
			// change color ??? can pick up
			break;
	}
	ctx.fill();
}

function keySend(code, webSocket) {
	switch (code) {
		case 97: // a for wasd
			webSocket.send("left"); movePlayer("left"); break;
		case 100: // d in wasd
			webSocket.send("right"); movePlayer("right"); break;
		case 119: // w in wasd
			webSocket.send("up"); movePlayer("up"); break;
		case 115: // s in wasd
			webSocket.send("down"); movePlayer("down"); break;
		case 32: // space bar for attack
			webSocket.send("space"); break;
		case 102: // f for items
			webSocket.send("f"); break;
		case 114: // r for radar
			webSocket.send("r"); break;
	}
}

/*** MISCELLANEOUS FUNCTIONS ***/

function determineOffset() {
	// figure out upper right of shrunk map
	// that is the offset, subtract from each number
}

function validMovement() {
	// is the player movement going to go out of bounds?
}

function drawDummies() {
	drawSquare(staticEntities[0].x+offsetX, staticEntities[0].y+offsetY, staticEntities[0].type);
	drawSquare(staticEntities[1].x+offsetX, staticEntities[1].y+offsetY, staticEntities[1].type);
	drawSquare(staticEntities[2].x+offsetX, staticEntities[2].y+offsetY, staticEntities[2].type);
}

function convertToCoord(y) {
	y = mapHeight - y;
}




