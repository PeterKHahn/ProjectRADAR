
/*** Define global variables ***/

let c, ctx, offsetX, offsetY, mapHeight, staticEntities;

$(document).ready(() => {
	init();

	/*** Interpreting keypress events ***/

	$(document).keypress(event => {
		code = event.keyCode;
		console.log(code);
		switch (code) {
			case 97: // a for wasd
				console.log("left!"); movePlayer("left"); break;
			case 100: // d in wasd
				console.log("right!"); movePlayer("right"); break;
			case 119: // w in wasd
				console.log("up!"); movePlayer("up"); break;
			case 115: // s in wasd
				console.log("down!"); movePlayer("down"); break;
			case 32: // space bar for attack
				console.log("space bar: attack!"); break;
			case 102: // f for items
				console.log("f: pick up items?"); break;
			case 114: // r for radar
				console.log("r: radar?"); break;
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