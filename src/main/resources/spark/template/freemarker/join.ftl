<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    
  </head>
  <body>	
     <script src="js/jquery-3.1.1.js"></script>
     <h1> WELCOME, ${codename}. PLEASE JOIN A GAME. </h1> 
 
     <#list roomIDs as ID>
  		 ${ID?counter}: <a href="/game/${ID}/${codename}"> ${ID} </a> <br>
	   </#list>

     <p id="result">  </p>
  </body>
</html>