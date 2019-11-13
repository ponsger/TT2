var tipo="";
function creaEjercicio(){
    var muestra=document.getElementById("opcion").selectedOptions[0].attributes.value;
    console.log(muestra.value)
    var formulario="";
    if(muestra.value==="Linea"){
        formulario+="<div id='formulario' ></div>"
    }else if(muestra.value==="DAD"){
        formulario+="<div class='contenido-grande alert-secondary'>"
        formulario+="<div class='card-header'>"
        formulario+="<h3 class='text-center'>Ingresa los datos del nuevo ejercicio a agregar</h3>"
        formulario+="</div>"
        formulario+="<div class='card-body'>"
        formulario+="<form onsubmit='generaEjercicio()'>"
        formulario+="<label class='font-weight-bold'>Nombre del ejercicio:</label>"
        formulario+="<input class='form-control border-dark' type='text' name='nombre' value=''/ required>"
        formulario+="<label class='font-weight-bold'>Ingresa las instrucciones para el alumno:</label>"
        formulario+="<input class='form-control border-dark' type='nombre' name='instruccion' value='' required/>"
        formulario+="<label class='font-italic font-weight-bold text-uppercase'>Ingresa las opciones para que el alumno elija</label><br>"
        formulario+="<center>"
        formulario+="<label class='font-weight-bold'>"
        formulario+="Opcion 1:"
        formulario+="<input class='form-control border-dark' type='text' name='opcion1' value='' required/>"
        formulario+="</label>"
        formulario+="<label class='font-weight-bold'>"
        formulario+="Opcion 2:"
        formulario+="<input class='form-control border-dark' type='text' name='opcion2' value='' required/>"
        formulario+="</label>"
        formulario+="<label class='font-weight-bold'>"
        formulario+="Opcion 3:"
        formulario+="<input class='form-control border-dark' type='text' name='opcion3' value='' required/>"
        formulario+="</label>"
        formulario+="<label class='font-weight-bold'>"
        formulario+="Opcion 4:"
        formulario+="<input class='form-control border-dark' type='text' name='opcion4' value='' required/>"
        formulario+="</label><br>"
        formulario+="</center>"
        formulario+="<label class='font-weight-bold'>Ingresa el resultado correcto: </label>"
        formulario+="<input class='form-control border-dark' placeholder='1,2,3,4 -> el orden de la respuestapor opción' type='text' name='resultado' value='' required/>"
        formulario+="<br>"
        formulario+="<button class='btn btn-success form-control' onclick='generaEjercicio()'>Guardar</button>"
        formulario+="<br><br>"
        formulario+="</div>"
        formulario+="</form> </div>"
        tipo=muestra.value;
    }else if(muestra.value==="---"){
        formulario="";
    }
    document.getElementById("tipo").innerHTML=formulario;
}

function generaEjercicio(){
    var nombre=document.getElementsByName("nombre");
    var instruccion=document.getElementsByName("instruccion");
    var opcion1=document.getElementsByName("opcion1")
    var opcion2=document.getElementsByName("opcion2")
    var opcion3=document.getElementsByName("opcion3")
    var opcion4=document.getElementsByName("opcion4")
    var resultado=document.getElementsByName("resultado")
    console.log(nombre[0].value)
    var id=getParameterByName("id");
    if(nombre[0].value===""||instruccion[0].value===""||opcion1[0].value===""||opcion2[0].value===""||opcion3[0].value===""||opcion4[0].value===""||resultado[0].value===""){
        alert("Por favor llene el campo vacío");
    }else{
    let url = 'http://localhost:8080/ProjectFinalStruts/inEjercicio?idProfesor='+id+'&nombre='+nombre[0].value+'&inst='+instruccion[0].value+'&op1='+opcion1[0].value+'&op2='+opcion2[0].value+'&op3='+opcion3[0].value+'&op4='+opcion4[0].value+'&res='+resultado[0].value+'&tipo='+tipo;
        //alert('URL:' + url);
        fetch(url).then(response => response.text()).then(data => {
            alert(data);    
        });
    }
    
}

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }