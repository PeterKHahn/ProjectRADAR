$(document).ready(() =>{


  const button = $("#connectButton")
  const $results = $("#resultList")
  const $message = $("#message")



  button.on('click', event =>{
    connect()
  })

  function connect(){
    let actor1Name = $("#baconBox1").val()
    let actor2Name = $("#baconBox2").val()
    const postParameters = {actor1: actor1Name, actor2: actor2Name}
    $message.html("Loading results...")

    $results.html('')


    $.post('/bacon_results', postParameters, responseJSON =>{


      const responseObject = JSON.parse(responseJSON);


      const connectionList = responseObject['connectionList'];
      let firstItem = ''

      for(let i = 0; i < connectionList.length; i++) {
        let connection = connectionList[i]

        let actor1Id = connection["actor1Id"];
        let actor1Name = connection["actor1Name"];
        let actor2Id = connection["actor2Id"];
        let actor2Name = connection["actor2Name"];
        let filmId = connection["filmId"];
        let filmName = connection["filmName"];

        console.log(filmId)

        firstItem += "<li>"+"<a href=\"/bacon/actor/"+actor1Id+"\">"+
          actor1Name+"</a> -> "+
          "<a href=\"/bacon/actor/"+actor2Id+"\">"+
            actor2Name+"</a> : "+
          "<a href=\"/bacon/film/"+filmId+"\">"+
            filmName+"</a>"
          "</li>";


      }

      console.log(firstItem)


      $results.html(firstItem)
      $message.html(responseObject["message"]);
    });
  }

});
