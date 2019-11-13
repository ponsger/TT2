var nombre,ins,opcion1,opcion2,opcion3,opcion4,resultado;
      var numero;
      function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
      }
      
      function modificaEjercicio(){
          cargaDatos();
      }

      function cargaDatos() {
        numero = getParameterByName('numero');
        cargarTodo(numero);
      }

      function cargarTodo(numero) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
          cargarXML(this, numero);
        };
        xhr.open("GET", "../xml/ejercicios.xml", true);
        xhr.send();
      }

      function cargarXML(xml, num) {
        var docXML = xml.responseXML;
        var infoEjercicio = '';
        var ejercicio = docXML.getElementsByTagName("ejercicio");
        nombre=ejercicio[num-1].getAttribute("nombre");
        ins=ejercicio[num-1].getElementsByTagName("indicaciones")[0].textContent;
        opcion1=ejercicio[num-1].getElementsByTagName("opcion1")[0].textContent;
        opcion2=ejercicio[num-1].getElementsByTagName("opcion2")[0].textContent;
        opcion3=ejercicio[num-1].getElementsByTagName("opcion3")[0].textContent;
        opcion4=ejercicio[num-1].getElementsByTagName("opcion4")[0].textContent;
        resultado=ejercicio[num-1].getElementsByTagName("resultado")[0].textContent;
            infoEjercicio +="<div class='card-columns'>";
            infoEjercicio+="<label class='lead'>Nombre del ejercicio: </label><input type='text' class='form-control' id='nombreInput' value='"+nombre+"' oninput='cambioNombre()' /><br></div>";
            infoEjercicio+="<div class='card-columns'><label class='lead'> Instrucciones: </label><input type='text' class='form-control' id='instInput' value='"+ins+"' oninput='cambioIns()' /><br></div>";
            infoEjercicio+="<hr><div class='card-columns'><label class='lead'>Opcion 1: </label><input type='text' class='form-control' id='op1Input' value='"+opcion1+"' oninput='cambioOp1()'/><br></div>";
            infoEjercicio+="<div class='card-columns'><label class='lead'>Opcion 2: </label><input type='text' class='form-control' id='op2Input' value='"+opcion2+"' oninput='cambioOp2()'/><br></div>";
            infoEjercicio+="<div class='card-columns'><label class='lead'>Opcion 3: </label><input type='text' class='form-control' id='op3Input' value='"+opcion3+"' oninput='cambioOp3()'/><br></div>";
            infoEjercicio+="<div class='card-columns'><label class='lead'>Opcion 4: </label><input type='text' class='form-control' id='op4Input' value='"+opcion4+"' oninput='cambioOp4()'/><br></div>";
            infoEjercicio+="<hr><div class='card-columns'><label class='lead'>Resultado: </label><input type='text' class='form-control' id='resInput' value='"+resultado+"' oninput='cambioRes()'/><br></div>";
            infoEjercicio+="<center>"
            infoEjercicio+="<hr><input type='submit' onclick='guardarCambios()' class='btn btn-primary btn-lg' value='Guardar cambios'/>"
            infoEjercicio+="</center>"
        document.getElementById("cuerpo").innerHTML=infoEjercicio;
      }

      function cambioNombre(e){
        //nombre=e.target.value;
        nombre=document.getElementById("nombreInput").value;
        console.log("Nombre: "+nombre)
      }

      function cambioIns(e){
        ins=document.getElementById("instInput").value;
        console.log("Instrucciones "+ins)
      }

      function cambioOp1(e){
        opcion1=document.getElementById("op1Input").value;
        console.log("Op1 "+opcion1)
      }
      function cambioOp2(e){
        opcion2=document.getElementById("op2Input").value;
        console.log("Op2 "+opcion2)
      }
      function cambioOp3(e){
        opcion3=document.getElementById("op3Input").value;
        console.log("Op3 "+opcion3)
      }
      function cambioOp4(e){
        opcion4=document.getElementById("op4Input").value;
        console.log("Op4 "+opcion4)
      }
      function cambioRes(e){
        resultado=document.getElementById("resInput").value;
        console.log("Resultado: "+resultado)
      }

      function guardarCambios(event){
        console.log("Instrucciones "+ins)
        console.log("Op1 "+opcion1)
        console.log("Op2 "+opcion2)
        console.log("Op3 "+opcion3)
        console.log("Op4 "+opcion4)
        console.log("Resultado: "+resultado)
        //http://localhost/struts2react/input1.jsp
        let url = 'http://localhost:8080/ProjectFinalStruts/ModificaEjercicio?numero='+numero+'&nombre='+nombre+'&instrucciones='+ins+'&opcion1='+opcion1+'&opcion2='+opcion2+'&opcion3='+opcion3+'&opcion4='+opcion4+'&resultado='+resultado;
        //alert('URL:' + url);
        fetch(url).then(response => response.text()).then(data => {
            alert(data);
            
        });
      }