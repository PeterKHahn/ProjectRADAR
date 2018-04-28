
/*** Define global variables ***/

let c, ctx, offsetX, offsetY, mapHeight, staticEntities, data, player, name;
let gameStart = false;

$(document).ready(() => {

	/*** Establish the WebSocket connection and set up event handlers ***/
    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/xx/websocket");
   	
   	webSocket.onopen = function(event) {
	  $("#socketStatus").innerHTML = 'Connected to: ' + event.currentTarget.url;
	};

	$("#game").hide();
	$("#waitingRoom").hide();
	$("#getName").show();

	
    // Send message if enter is pressed in the input field
    $("#codename").keypress(event => {
    	console.log("KEY: " + event.keyCode);
        if (event.keyCode === 13) { 
        	websocketSend(webSocket, "name", event.target.value, false);
        	name = event.target.value;
        	$("#getName").fadeOut();
        	$("#waitingRoom").fadeIn();
        }
    });

    //starts game when start button clicked.
    $("#start").click(event => {
    	let status = {status: "start"};
    	websocketSend(webSocket, "game", "start", false);
    	$("#waitingRoom").fadeOut();
    	$("#game").fadeIn();
    	gameStart = true;
    	init();
    })


    webSocket.onmessage = function (msg) {
    	console.log(JSON.parse(msg.data));
    	data = JSON.parse(msg.data);
    	player = data.player;
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
	$(document).keydown(event => {
		if (gameStart) {
			switch(event.keyCode){
				case 97: // a for wasd
					websocketSend(webSocket, "key", "left", true); movePlayer("left"); break;
				case 100: // d in wasd
					websocketSend(webSocket, "key", "right", true); movePlayer("right"); break;
				case 119: // w in wasd
					websocketSend(webSocket, "key", "up", true); movePlayer("up"); break;
				case 115: // s in wasd
					websocketSend(webSocket, "key", "down", true); movePlayer("down"); break;
			}
		}
	})

	$(document).keyup(event => {
		if (gameStart) {
			switch(event.keyCode){
				case 97: // a for wasd
					websocketSend(webSocket, "key", "left", false); movePlayer("left"); break;
				case 100: // d in wasd
					websocketSend(webSocket, "key", "right", false); movePlayer("right"); break;
				case 119: // w in wasd
					websocketSend(webSocket, "key", "up", false); movePlayer("up"); break;
				case 115: // s in wasd
					websocketSend(webSocket, "key", "down", false); movePlayer("down"); break;
			}
		}
	})

	$(document).keypress(event => {
        console.log(event.keyCode)
		if (gameStart) {
			switch(event.keyCode){
				case 32: // space bar for attack
					websocketSend(webSocket, "key", "space", false); break;
				case 102: // f for items
					websocketSend(webSocket, "key", "f", false); break;
				case 114: // r for radar
					websocketSend(webSocket, "key", "r", false); break;
			}
		}
	});
});

 
function websocketSend(webSocket, type, status, held) {
	let x = {
		type: type, 
		status: status,
		held: held
	}
	webSocket.send(JSON.stringify(x));
}


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
		{x: 333, y:270, type:"deco"},
		{x: 700, y:270, type:"item"},
		{x: 1000, y:270, type:"weapon"}
	];
	//TESTING DUMMY PLAYER
	player = {
		x: 100,
		y: 200
	}
	console.log("player position is now (" + player.x + ", " + player.y + ")");
	determineOffset();
	drawPlayer();
	drawStatic();

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
			player.x=player.x-10;
			offsetX+=10; break;
		case "right":
			player.x=player.x+10;
			offsetX-=10; break;
		case "up":
			player.y=player.y-10;
			offsetY+=10; break;
		case "down":
			player.y=player.y+10;
			offsetY-=10; break;
	}
	console.log("player position is now (" + player.x + ", " + player.y + ")");
	clearCanvas();
	drawStatic();
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



/*** MISCELLANEOUS FUNCTIONS ***/

function determineOffset() {
	 offsetX = player.x - c.width/2;
	 offsetY = convertToCoord(player.y) - c.height/2;
	// figure out upper right of shrunk map
	// that is the offset, subtract from each number
}

function validMovement() {
	// is the player movement going to go out of bounds?
}

function drawStatic() {
	for (let i = 0; i < staticEntities.length; i++) {
		drawSquare(staticEntities[i].x+offsetX, staticEntities[i].y+offsetY, staticEntities[i].type);		
	}
}

function convertToCoord(y) {
	return -1*y;
}




