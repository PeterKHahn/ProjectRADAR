<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
  </head>
  <body>	
     <script src="js/jquery-3.1.1.js"></script>
     <script src="js/name.js"></script>
     
     
     <h1> &lt;PROJECT R.A.D.A.R&gt; </h1>
     
     <form id="nameForm" method="GET">
  		<label for="codename"> enter your name.</label>
  		<input id="codename" name="codename" type="text"/>
		 <br>
		 <button type="submit" formaction="/create" id="create" name="create" value="create">create</button>
		 <button type="submit" formaction="/join" id="join" name="join" value="join">join</button>		 
	 </form>

     <p id="result">  </p>
  </body>
</html>