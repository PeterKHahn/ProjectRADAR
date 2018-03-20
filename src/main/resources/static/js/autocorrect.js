const numSuggestions = 5;

$(document).ready(() =>{


  suggestions("#box1", "#suggestions", "/suggestions", (text) => {
    text.endsWith(' ');
  })
  suggestions("#baconBox1", "#suggestions2", "/bacon_suggestions", (text) => {
    return text == ''
  })
  suggestions("#baconBox2", "#suggestions3", "/bacon_suggestions", (text) =>{
    return text == '';
  })


  function suggestions(boxID, dropdownID, postURL, hideFunction) {
    const textbox = $(boxID)
    const $suggestions = $(dropdownID)

    textbox.on('input', event =>{
      let text = textbox.val();

      if(hideFunction(text)){
        $suggestions.hide();

      }else{
        $suggestions.show();

      }


      const postParameters = {input: text}
      $.post(postURL, postParameters, responseJSON => {
        const responseObject = JSON.parse(responseJSON);
        const responseSuggestions = responseObject["suggestions"];
        let item = ''
        for(let i = 0; i < responseSuggestions.length && i < numSuggestions; i++){
          item += "<li>"+responseSuggestions[i]+"</li>";
        }
        $suggestions.html(item)
      });
    });

    $suggestions.on('click', event => {
      let result = event.target.innerText;
      textbox.val(result + ' ');
      $suggestions.hide();
      textbox.focus();

    });


  };



});
