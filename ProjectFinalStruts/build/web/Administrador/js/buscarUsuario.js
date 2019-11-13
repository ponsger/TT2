    var idUsuarioid;
    
    function buscarUsuario(){
    var tabla="";
    tabla+="<center class='container bg-success'>"
    tabla+="<br>"
                    tabla+="<div class='container'>"
                        tabla+="<div class='row'>"
                            tabla+="<div class='col-4'>"
                                tabla+="<label class='font-weight-bold text-white h5'>Ingresa el id del usuario a modificar: </label>"
                            tabla+="</div>"
                            tabla+="<div class='col-8'>"
                                tabla+="<input class='form-control' type='text' placeholder='Identificador del usuario' value='' required></input>"
                            tabla+="</div>"
                        tabla+="</div>"
                        tabla+="<br>"
                        tabla+="<div className='row'>"
                            tabla+="<div className='col'>"
                                tabla+="<butoon class='btn btn-warning btn-lg btn-block' onclick='obtieneInfo()'>Buscar</button>"
                            tabla+="</div></div><br></div></center>"
    document.getElementById("modificaUsuario").innerHTML=tabla;
}


function obtieneInfo(){
    var numero=document.getElementsByTagName("input");
    console.log(numero[0].value);
    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarUnUsuario?idUsuario='+numero[0].value;
    fetch(url).then(response => response.text()).then(data => {
        alert(data);
        
    muestraDatos();
    
    });
}

function muestraDatos(){
    var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
            if (this.readyState == 4 && this.status == 200){
            var dataArray = JSON.parse(this.responseText);
            console.log(this)
            console.log(dataArray)
            var idUsuario=dataArray.idUsuario;
            console.log(idUsuario)
            idUsuarioid=idUsuario[0].id;
    
            var formulario="<br><br>";
            formulario+="<center class='container bg-danger'>"
                        formulario+="<br>"
                            formulario+="<div class='container'>"
                                formulario+="<div class='row'>"
                                    formulario+="<div class='col-4'>"
                                        formulario+="<label class='font-weight-bold text-white h5'>Nombre o nombres de la persona: </label>"
                                    formulario+="</div>"
                                    formulario+="<div class='col-8'>"
                                        formulario+="<input class='form-control' type='text' placeholder='Nombre o nombres' name='nombre' value='"+idUsuario[0].nombre+"' required></input>"
                                    formulario+="</div>"
                                formulario+="</div>"
                                formulario+="<br>"
                                formulario+="<div class='row'>"
                                    formulario+="<div class='col-4'>"
                                        formulario+="<label class='font-weight-bold text-white h5'>Nombre de usuario (Forma de inicio de sesión): </label>"
                                    formulario+="</div>"
                                    formulario+="<div class='col-8'>"
                                        formulario+="<input class='form-control' type='text' placeholder='Apodo del usuario para iniciar sesión' name='username' value='"+idUsuario[0].nombreUsuario+"' required></input>"
                                    formulario+="</div>"
                                formulario+="</div>"
                                formulario+="<br>"
                                formulario+="<div class='row'> "
                                    formulario+="<div class='col-4'>"
                                        formulario+="<label class='font-weight-bold text-white h5'>Apellido paterno: </label>"
                                    formulario+="</div>"
                                    formulario+="<div class='col-8'>"
                                        formulario+="<input class='form-control' type='text' placeholder='Apellido paterno' name='apaterno' value='"+idUsuario[0].apellidoPat+"' required></input>"
                                    formulario+="</div>"
                                formulario+="</div>"
                                formulario+="<br>"
                                formulario+="<div class='row'> "
                                    formulario+="<div class='col-4'>"
                                        formulario+="<label class='font-weight-bold text-white h5'>Apellido materno: </label>"
                                    formulario+="</div>"
                                    formulario+="<div class='col-8'>"
                                        formulario+="<input class='form-control' type='text' placeholder='Apellido materno' name='amaterno' value='"+idUsuario[0].apellidoMat+"' required></input>"
                                    formulario+="</div>"
                                formulario+="</div>"
                                formulario+="<br>"
                                formulario+="<div class='row'> "
                                    formulario+="<div class='col-4'>"
                                        formulario+="<label class='font-weight-bold text-white h5'>Contraseña: </label>"
                                    formulario+="</div>"
                                    formulario+="<div class='col-8'>"
                                        formulario+="<input class='form-control' type='text' placeholder='Contraseña para el usuario' name='contrasena' value='"+idUsuario[0].contrasena+"' required></input>"
                                    formulario+="</div>"
                                formulario+="</div>"
                                formulario+="<br>"
                                formulario+="<div class='row'>"
                                    formulario+="<div class='col'>"
                                        formulario+="<button class='btn btn-info btn-outline-light btn-lg btn-block' onclick='guardarInfo()'>Guardar información</button>"
                                    formulario+="</div></div><br></div></center>"
                                    
                                    
               document.getElementById("profesor").innerHTML=formulario;
            }
        }
        xmlhttp.open("GET", "../json/resultadoConsultaUnUsuario.json", true);
        xmlhttp.send();
    }

function guardarInfo(){
    var nombre=document.getElementsByName("nombre");
    var username=document.getElementsByName("username");
    var apaterno=document.getElementsByName("apaterno");
    var amaterno=document.getElementsByName("amaterno");
    var contrasena=document.getElementsByName("contrasena");
    console.log(nombre[0].value)    
    console.log(idUsuarioid)
        let url = 'http://localhost:8080/ProjectFinalStruts/ModificarUsuario?id='+idUsuarioid+"&nombres="+nombre[0].value+"&nombreUsuario="+username[0].value+"&apellidoPat="+apaterno[0].value+"&apellidoMat="+amaterno[0].value+"&contrasena="+contrasena[0].value;
    fetch(url).then(response => response.text()).then(data => {
        alert(data);
        
    });
    
    
    
    
}