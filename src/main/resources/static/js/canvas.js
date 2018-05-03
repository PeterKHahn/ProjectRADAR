
/*** Define global variables ***/

let c, ctx, offsetX, offsetY, mapHeight, entities, markers, items, data, player, name;
let scalar = 1.5;
let gameStart = false;

$(document).ready(() => {

	/*** Establish the WebSocket connection and set up event handlers ***/
	pathname = location.pathname.substring(6, location.pathname.length);
    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/websocket?roomID=" + pathname);

   	webSocket.onopen = function(event) {
	  $("#socketStatus").innerHTML = 'Connected to: ' + event.currentTarget.url;
	};

	$("#gameOver").hide();
	$("#game").hide();
	$("#waitingRoom").hide();
	$("#getName").show();


    // Send message if enter is pressed in the input field
    $("#codename").keypress(event => {
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
    })


    webSocket.onmessage = function (msg) {
    	data = JSON.parse(msg.data);
    	console.log(data);
    	if (data.type === "gameMessage") {
    		if (data.message === "start") {
    			$("#waitingRoom").fadeOut();
		    	$("#game").fadeIn();
		    	gameStart = true;
		    	init();

    		} 
    		if (data.message === "Someone has left!" && data.userlist.length === 1) {
    			console.log(data.userlist);
    			gameStart = false;
    			$("#game").fadeOut();
		    	$("#winner").text(data.userlist[0]);
		    	$("#gameOver").fadeIn();
    		}
    	} else {
    		if (gameStart) {
    			player = data.player;
    			interactables = data.interactables;
    			items = data.items;
    			markers = data.markers;
    			weapon = data.weapon;

	    		clearCanvas();
	            determineOffset();
	            drawInteractables();
	            drawItems();
	            drawMarkers();
	            drawPlayer();
	            ctx.globalAlpha = "1.0";	
	    	}
    	}
    	
    };

    webSocket.onclose = function () {
    	console.log("websocket connection closed.")
    	gameStart = false;
    	$("#game").fadeOut();
    	$("#winner").text("not you!");
    	$("#gameOver").fadeIn();

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
					websocketSend(webSocket, "key", "left", true);
					break;
				case "d": // d in wasd
				case "ArrowDown":
					websocketSend(webSocket, "key", "right", true);
					break;
				case "w": // w in wasd
				case "ArrowUp":
					websocketSend(webSocket, "key", "up", true);
					break;
				case "s": // s in wasd
				case "ArrowRight":
					websocketSend(webSocket, "key", "down", true);
					break;
        case " ":
          websocketSend(webSocket, "key", "space", true);
          break;
        case "f":
          websocketSend(webSocket, "key", "f", true);
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
				// case " ": // space bar for attack
				// 	websocketSend(webSocket, "key", "space", false); break;
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
	ctx = c.getContext("2d", {alpha: false});
	c.width = 500;
	c.height = 500;
	offsetX = 0;
	offsetY = 0;
};

function drawPlayer() {
	ctx.beginPath();
	ctx.strokeStyle = "#DDE392";
	ctx.lineWidth = 2;
	ctx.arc(c.width/2, c.height/2, 5, 0, 2*Math.PI);
	ctx.stroke();

	drawHP();
    drawName();
    if (weapon.attack.attacking) {
    	drawPlayerHitbox();    	
    }
}

function drawPlayerHitbox() {

  let boxes = weapon.attack.currentAttackFrame.hitbox.boxes;
  for(let i = 0; i < boxes.length; i++) {
    let xOff = boxes[i].offset.x;
    let yOff = boxes[i].offset.y;
    let x = xOff + player.x;
    let y = yOff + player.y;

    drawCircle(offsetX + x, offsetY + convertToCoord(y), boxes[i].radius, "hitbox");
  }
}

function drawCircle(x, y, radius, type) {
	ctx.beginPath();
	switch(type) {
		case "WEAPON":
			ctx.strokeStyle = "#CF1259";
			ctx.fillStyle = "#CF1259";
			break;
	    case "hitbox":
	      ctx.strokeStyle = "red";
	      ctx.globalAlpha = "0.5";
	      ctx.fillStyle = "red";
      	  break;
		case "item":
			ctx.strokeStyle = "white";
			ctx.fillStyle = "white";
			break;
		case "obstacle":
			ctx.strokeStyle = "#657C73";
			ctx.fillStyle = "#657C73";
			break;
		case "markers":
			ctx.strokeStyle = "#b8dbd9";
			ctx.fillStyle = "#b8dbd9";
			break;
		case "ai":
			// ctx.strokeStyle = "#B2E6E8";
			// ctx.fillStyle = "#B2E6E8";
			// break;
		case "human":
			ctx.strokeStyle = "#DDE392";
			ctx.fillStyle = "#DDE392";
			break;
	}
	ctx.lineWidth = 2;
	//TODO CHANGE OBSTACLE
	ctx.arc(x, y, radius, 0, 2*Math.PI);
	ctx.stroke();
	ctx.fill();
}

function clearCanvas() {
	ctx.clearRect(0, 0, c.width, c.height);
}


/*** MISCELLANEOUS FUNCTIONS ***/


function drawHP() {
	achepee = player.health;
	ctx.font = "25px Arial";
	ctx.strokeText(achepee,30,30);
}

function drawName() {
	ctx.strokeStyle = "#b8dbd9";
	ctx.font = "13px Arial";
	ctx.textAlign = "center"
	ctx.fillText(name, c.width/2, c.height/2 - 15);
}

function determineOffset() {
	 offsetX = convertToCoord(player.x) + c.width/2;
	 offsetY = player.y + c.height/2;
	// how much we have to offset from (0,0) to keep player at center
	// that is the offset, ADD to each number so that we can keep it visible onscreen + properly displayed
}

function drawInteractables() {
	for (let i = 0; i < interactables.length; i++) {
		drawCircle(interactables[i].x+offsetX, convertToCoord(interactables[i].y)+offsetY, interactables[i].collisionBox.reach, interactables[i].drawType);
	}
}

function drawItems() {
	for(let i = 0 ; i < items.length; i++) {
		drawCircle(items[i].x + offsetX, convertToCoord(items[i].y) + offsetY, 3, items[i].type);
	}
}

function drawMarkers() {
	let bigX = 0;
	let bigY = 0;

	for(let i = 0; i < markers.length; i++) {

	    drawCircle(markers[i].x + offsetX, convertToCoord(markers[i].y) + offsetY, 3, "markers");

	    if (checkMarkers(markers[i].x, bigX)) {
	    	bigX = markers[i].x;
	    }

	    if (checkMarkers(markers[i].y, bigY)) {
	    	bigY = markers[i].y;
	    }
	}


	console.log("drawing borders until "+ bigX + " " + bigY);
	// make a transparent square

	ctx.strokeStyle = "#b8dbd9";
	ctx.beginPath();
	ctx.globalAlpha = "0.05";
	ctx.rect(0 + offsetX, 0 + offsetY, bigX, convertToCoord(bigY));
	ctx.fill();
	ctx.stroke();

}

function checkMarkers(z, bigZ) {
	if (z > bigZ) {
		return true;
	} else {
		return false;
	}

}

function convertToCoord(y) {
	return -1*y;
}

function scale(x) {
	return scalar*x;
}