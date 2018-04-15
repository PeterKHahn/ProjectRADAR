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
     ${codename}
     
  	 <canvas id="gameCanvas" width="500" height="500"></canvas>
     <div id="chatControls">
        <input id="message" placeholder="Type your message">
        <button id="send">Send</button>
      </div>
      <ul id="userlist"> <!-- Built by JS --> </ul>
      <div id="chat">    <!-- Built by JS --> </div>
      <p id="socketStatus"></p>
      
  </body>
</html>