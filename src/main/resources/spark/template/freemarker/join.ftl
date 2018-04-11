<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
  </head>
  <body>	
     <script src="js/jquery-3.1.1.js"></script>
     <h1> JOIN ME AND TOGETHER WE CAN RULE THE GALAXY</h1> 
     
     ${codename}

     <#list roomIDs as ID>
  		 ${ID?counter}: <a href="/game/${ID}"> ${ID} </a>
	 </#list>

     <p id="result"> 😉 </p>
  </body>
</html>