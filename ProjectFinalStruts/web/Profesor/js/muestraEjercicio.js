function muestraEjercicio(){
    cargarTabla()
}

function cargarTabla() {
      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function () {
        cargarXML(this);
      };
      xhr.open("GET", "../xml/ejercicios.xml", true);
      xhr.send();
    }

    function cargarXML(xml) {
      var docXML = xml.responseXML;
      console.log(docXML)
      var tabla = "<tr><th scope='col'>#</th><th scope='col'>Nombre del ejercicio</th><th scope='col'>Accionnes</th></tr>"
      var ejercicio = docXML.getElementsByTagName("ejercicio");
      console.log(ejercicio)
      for (var i = 0; i < ejercicio.length; i++) {
        tabla += "<tr><th socpe='row'>";
        tabla += (i + 1);
        tabla += "</th><td>";
        tabla += ejercicio[i].getAttribute("nombre");
        tabla += "</td>";
        tabla += "<td><a class='btn btn-primary' onclick='redireccionaVerEjercicio("+(i+1)+")'>Ver ejercicio</a>";
        tabla += "<a class='btn btn-warning' onclick='redireccionaModificaEjercicio("+(i+1)+")'>Modificar</a>";
        tabla += "<button type='button' class='btn btn-danger' onclick='eliminaClick("+(i+1)+")'>Eliminar</button>";
        tabla += "</td></tr>"
      }
      document.getElementById("demo").innerHTML = tabla;
    }
    
    function redireccionaVerEjercicio(numero){
          var id=getParameterByName("id");
        window.location.href='http://localhost:8080/ProjectFinalStruts/Profesor/infoEjercicio.html?id='+id+'&numero='+numero;
    }
      
    function redireccionaModificaEjercicio(numero){
          var id=getParameterByName("id");
        window.location.href='http://localhost:8080/ProjectFinalStruts/Profesor/modificaEjercicio.html?id='+id+'&numero='+numero;
      }  

    function eliminaClick(numero){
      var opcion = confirm("Desea eliminar el ejercicio: "+numero);
      console.log(opcion);
      if(opcion==true){
        let url = 'http://localhost:8080/ProjectFinalStruts/BorraEjercicio?numero='+numero;
        //alert('URL:' + url);
        fetch(url).then(response => response.text()).then(data => {
            alert(data);    
            cargarTabla()
        });
      }
        
    }