
var fConnect = (function(){

    return {

        connection : function(lugar){
            var url = 'http://localhost:5000/consulta?ciudad=' + lugar + '/'


                        fetch(heroku, {
                            mode: 'no-cors',
                            method: 'GET',
                            headers: {
                                "Content-type": "application/json"
                            }
                        })
                            .then(response => response.json())
                            .then(json => $('#respuesta').html(json.respuesta))
        }
    }
})();