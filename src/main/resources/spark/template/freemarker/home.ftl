<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
  </head>
  <body>	
     <script src="js/jquery-3.1.1.js"></script>
     <script src="js/name.js"></script>
     
     <form id="nameForm" action="#">
  		<label for="codename"> enter your name.</label>
  		<input id="codename" name="codename" type="text"/>
		 <br>
		 <button type="button" onclick="localhost:4567/create" id="create" name="create" value="create">create</button>
		 <button type="button" onclick="localhost:4567/join" id="join" name="join" value="join">join</button>		 
	 </form>

     <p id="result"> 😉 </p>
  </body>
</html>