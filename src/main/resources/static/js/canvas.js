
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
    	websocketSend(webSocket, "game", "start", false);
    	$("#waitingRoom").fadeOut();
    	$("#game").fadeIn();
    	gameStart = true;
    	init();
    })


    webSocket.onmessage = function (msg) {
    	// console.log(JSON.parse(msg.data)); 
    	data = JSON.parse(msg.data);
    	console.log(data);
    	player = data.player;
    	staticEntities = data.statics;
    	if (gameStart) {
    		clearCanvas();
    		determineOffset();
    		drawStatic();  
			drawPlayer();  		
    	}
    };

    webSocket.onclose = function () { 
    	console.log("websocket connection closed.")
    };
	
	webSocket.onerror = function(error) {
	  console.log(error);
	  console.log('WebSocket Error: ' + error);
	};

	/*** Interpreting keypress events ***/
	$(document).keydown(event => {

		if (gameStart) {

			switch(event.key){
				case "a": // a for wasd
				case "ArrowLeft":
					console.log("hewwooo")
					websocketSend(webSocket, "key", "left", true); 
					//movePlayer("left"); 
					break;
				case "d": // d in wasd
				case "ArrowDown":
					websocketSend(webSocket, "key", "right", true); 
					//movePlayer("right"); 
					break;
				case "w": // w in wasd
				case "ArrowUp":
					websocketSend(webSocket, "key", "up", true); 
					//movePlayer("up"); 
					break;
				case "s": // s in wasd
				case "ArrowRight":
					websocketSend(webSocket, "key", "down", true); 
					//movePlayer("down"); 
					break;
			}
		}
	})

	$(document).keyup(event => {
		if (gameStart) {
			switch(event.key){
				case "a": // a for wasd
				case "ArrowLeft":
					websocketSend(webSocket, "key", "left", false);
					break;
				case "d": // d in wasd
				case "ArrowRight":
					websocketSend(webSocket, "key", "right", false);
					break;
				case "w": // w in wasd
				case "ArrowUp":
					websocketSend(webSocket, "key", "up", false);
					break;
				case "s": // s in wasd
				case "ArrowDown":
					websocketSend(webSocket, "key", "down", false);
					break;
			}
		}
	})

	$(document).keypress(event => {
		if (gameStart) {
			switch(event.keyCode){
				case "space": // space bar for attack
					websocketSend(webSocket, "key", "space", false); break;
				case "f": // f for items
					websocketSend(webSocket, "key", "f", false); break;
				case "r": // r for radar
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
	// staticEntities = [
	// 	{x:20, y:60, type:"weapon"},
	// 	{x:420, y:390, type: "item"},
	// 	{x: 333, y:270, type:"deco"},
	// 	{x: 700, y:270, type:"item"},
	// 	{x: 1000, y:270, type:"weapon"}
	// ];
	// //TESTING DUMMY PLAYER
	// player = {
	// 	x: 100,
	// 	y: 200
	// }
	drawPlayer();
	// drawStatic();
};

function drawPlayer() {
	ctx.beginPath();
	ctx.strokeStyle = "#b8dbd9";
	ctx.lineWidth = 2;
	ctx.arc(c.width/2, c.height/2, 5, 0, 2*Math.PI);
	ctx.stroke();
}

function movePlayer(direction) {
	switch(direction) {
		case "left":
			console.log("made it to move left"); break;
		case "right":
			console.log("made it to move left"); break;
		case "up":
			console.log("made it to move up"); break;
		case "down":
			console.log("made it to move down"); break;
	}
	console.log()
}

// clears canvas to redraw items.
function clearCanvas() {
	ctx.clearRect(0, 0, c.width, c.height);
}

function drawCircle(x, y, radius, type) {
	ctx.beginPath();
		switch(type) {
		case "weapon":
			ctx.strokeStyle = "red";
			ctx.fillStyle = "red";
			// maybe change color?? can pick up
			break;
		case "item":
			ctx.strokeStyle = "white";
			ctx.fillStyle = "white";
			// change color ??? can pick up
			break;
		case "deco":
			ctx.strokeStyle = "green";
			ctx.fillStyle = "green";
			// change color ??? can pick up
			break;
		case "none":
			ctx.strokeStyle = "orange";
			ctx.fillStyle = "orange";
			break;
	}
	ctx.lineWidth = 2;
	//TODO CHANGE OBSTACLE
	ctx.arc(x, y, radius, 0, 2*Math.PI);
	ctx.stroke();
	ctx.fill();
}



/*** MISCELLANEOUS FUNCTIONS ***/

function drawHP() {
	player.center.hp;
}

function determineOffset() {
	 offsetX = convertToCoord(player.center.x) + c.width/2;
	 offsetY = player.center.y + c.height/2;
	// how much we have to offset from (0,0) to keep player at center
	// that is the offset, ADD to each number so that we can keep it visible onscreen + properly displayed
}

// function validMovement() {
// 	// is the player movement going to go out of bounds?
// }

function drawStatic() {
	for (let i = 0; i < staticEntities.length; i++) {
		console.log(staticEntities[i].radius)
		drawCircle(staticEntities[i].center.x+offsetX, convertToCoord(staticEntities[i].center.y)+offsetY, staticEntities[i].radius, "none");		
	}
}

// TODO FIGURE THIS OUT
function convertToCoord(y) {
	return -1*y;
}




