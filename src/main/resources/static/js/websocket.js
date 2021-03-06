
// $(document).ready(function(){

//     //Establish the WebSocket connection and set up event handlers
//     console.log("ws:" + location.hostname + ":" + location.port + "/game/websocket")
//     var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/xx/websocket");
//     console.log(location.hostname);
//     webSocket.onmessage = function (msg) { updateChat(msg); };
//     webSocket.onclose = function () { alert("WebSocket connection closed") };
//     // Show a connected message when the WebSocket is opened.
// 	webSocket.onopen = function(event) {
// 	  $("#socketStatus").innerHTML = 'Connected to: ' + event.currentTarget.url;
// 	};
// 	// Handle any errors that occur.
// 	webSocket.onerror = function(error) {
// 	  console.log(error);
// 	  console.log('WebSocket Error: ' + error);
// 	};

//     //Send message if "Send" is clicked
//     $("#send").click(function() {
//         sendMessage($("#message").val());
//     });

//     //Send message if enter is pressed in the input field
//     $("#message").keypress(event => function (e) {
//         if (e.keyCode === 13) { sendMessage(e.target.value); }
//     });

//     //Send a message if it's not empty, then clear the input field
//     function sendMessage(message) {
//         if (message !== "") {
//             webSocket.send(message);
//             $("#message").val("");
//         }
//     }

//     //Update the chat-panel, and the list of connected users
//     function updateChat(msg) {
//         var data = JSON.parse(msg.data);
//         $("<li/>").html(data.message).appendTo($("#chat"));
//         $("userlist").innerHTML = "";
//         data.userlist.forEach(function (user) {
//             $("<li/>").html(user).appendTo($("#chat"));
//         });
//     }
// });