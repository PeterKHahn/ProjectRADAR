<html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <script src="/js/jquery-3.1.1.js"></script>
    <script src="/js/canvas.js"></script>    
  </head>
  <body>
  
  	 <div id="getName" class="page" > 
	  		<p> enter your codename.</p>
	  		<input id="codename" name="codename" type="text"/>	 
		 </form>
	 </div> 
	 
	 <div id="waitingRoom" class="page"> 
	 
	 	<h1> waiting room </h1>
	 	
	 	<!-- <a href="/game/${roomID}"> link to join </a>  -->
	 	<input type="textarea" id="url"> 
	    <input type="button" value="Copy Url To ClipBoard" id="copy" />
	    <p id="copied"> </p>

	 	<ul id="users">
	 	</ul> 
	 	
	 	<p id="socketStatus"> </p>
	 	
	 	<button type="button" id="start" name="start"> Start Game </a> 
	 </div>
	   
	 <div id="game" class="page">   
	  	 <canvas id="gameCanvas" width="500" height="500"></canvas>
	      <ul id="userlist"> <!-- Built by JS --> </ul>
	      <p id="socketStatus"></p>
	 </div>

	 <div id="gameOver" class="page">   
	  	 <h1> GAME OVER </h1>
	    <h3 id="howitgo"> WINNER: <span id="winner"> </span> </h3>
	 	<a href="/"> start over? </a> 
	 </div>

      
  </body>
</html>