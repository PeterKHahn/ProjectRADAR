
/*** Define global variables ***/

let c, ctx, xOffset, yOffset, mapHeight, entities, markers, items, data, player, name; 
let scalar = 1.5;
let gameStart = false;

$(document).ready(() => {



	/*** Establish the WebSocket connection and set up event handlers ***/
	pathname = location.pathname.substring(6, location.pathname.length);
    var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/websocket?roomID=" + pathname);
    
    $("#url").val(location.href);

   	webSocket.onopen = function(event) {
	  $("#socketStatus").innerHTML = 'Connected to: ' + event.currentTarget.url;
	  $("#gameOver").hide();
		$("#game").hide();
		$("#waitingRoom").hide();
		$("#getName").show();

	};


	$("#copy").click(event => {
		$("#url").select();
        document.execCommand("copy");
        $("#copied").innerHTML = "copied link to clipboard!";

	})

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
    	if (data.type === "gameMessage") {
    		$("#users").empty();
    		for (let i = 0; i < data.userlist.length; i++) {
    			$("<li>").html(data.userlist[i]).appendTo($("#users"));
    		}
    		if (data.message === "start") {
    			$("#waitingRoom").fadeOut();
		    	$("#game").fadeIn();
		    	gameStart = true;
		    	init();

    		}
    		if (data.message.substring(0, 9) === "GAME OVER") {
    			gameStart = false;
    			$("#waitingRoom").hide();
				$("#getName").hide();
    			$("#game").fadeOut();
		    	$("#winner").text(data.message.substring(11, data.message.length));
		    	$("#gameOver").fadeIn();
    		}
    	} else {
    		if (gameStart) {
    			player = data.player;
    			interactables = data.interactables;
					console.log(interactables.length)
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
			switch(event.key) {
		        case " ": // space for fighting
		          	websocketSend(webSocket, "key", "space", true);	break;
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
	xOffset = 0;
	yOffset = 0;
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


    drawCircle(x,y, boxes[i].radius, "hitbox");
  }
}

function drawHitbox(interactable) {
	let boxes = interactable.hitBox.boxes
	for(let i = 0; i < boxes.length; i++) {

		let xOff = boxes[i].offset.x;
		let yOff = boxes[i].offset.y;
		let x = xOff + interactable.x;
    let y = yOff + interactable.y;

    drawCircle(x,y, boxes[i].radius, "hitbox");
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
	ctx.arc(x + xOffset, convertToCoord(y) + yOffset, radius, 0, 2*Math.PI);
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
	 xOffset = convertToCoord(player.x) + c.width/2;
	 yOffset = player.y + c.height/2;
	// how much we have to offset from (0,0) to keep player at center
	// that is the offset, ADD to each number so that we can keep it visible onscreen + properly displayed
}

function drawInteractables() {
	for (let i = 0; i < interactables.length; i++) {
		for(let j = 0; j < interactables[i].collisionBox.boxes.length; j++) {
			let radius = interactables[i].collisionBox.boxes[j].radius;


			drawCircle(interactables[i].x, interactables[i].y, radius, interactables[i].drawType);


		}
		drawHitbox(interactables[i])
	}
}

function drawItems() {
	for(let i = 0 ; i < items.length; i++) {
		drawCircle(items[i].x, items[i].y, 3, items[i].type);
	}
}

function drawMarkers() {
	let bigX = 0;
	let bigY = 0;

	for(let i = 0; i < markers.length; i++) {

	    drawCircle(markers[i].x, markers[i].y, 3, "markers");

	    if (checkMarkers(markers[i].x, bigX)) {
	    	bigX = markers[i].x;
	    }

	    if (checkMarkers(markers[i].y, bigY)) {
	    	bigY = markers[i].y;
	    }
	}


	// make a transparent square

	ctx.strokeStyle = "#b8dbd9";
	ctx.beginPath();
	ctx.globalAlpha = "0.05";
	ctx.rect(0 + xOffset, 0 + yOffset, bigX, convertToCoord(bigY));
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
