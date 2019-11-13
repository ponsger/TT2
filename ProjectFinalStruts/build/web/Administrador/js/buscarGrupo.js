    var idGrupoid;
    function buscarGrupo(){
    var tabla="";
    tabla+="<center class='container bg-warning'>"
    tabla+="<br>"
                    tabla+="<div class='container'>"
                        tabla+="<div class='row'>"
                            tabla+="<div class='col-4'>"
                                tabla+="<label class='font-weight-bold text-white h5'>Ingresa el id del grupo a modificar: </label>"
                            tabla+="</div>"
                            tabla+="<div class='col-8'>"
                                tabla+="<input class='form-control' type='text' placeholder='Identificador del grupo' value='' required></input>"
                            tabla+="</div>"
                        tabla+="</div>"
                        tabla+="<br>"
                        tabla+="<div className='row'>"
                            tabla+="<div className='col'>"
                                tabla+="<butoon class='btn btn-danger btn-lg btn-block' onclick='obtieneInfo()'>Buscar</button>"
                            tabla+="</div></div><br></div></center>"
    document.getElementById("modificaUsuario").innerHTML=tabla;
}

function obtieneInfo(){
    var numero=document.getElementsByTagName("input");
    console.log(numero[0].value)
    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarUnGrupo?idGrupo='+numero[0].value;
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
            var idGrupo=dataArray.idGrupo;
            console.log(idGrupo)
            idGrupoid=idGrupo[0].id;
            var formulario="<br><br>";
            formulario+="<center><h3 class='text-white'>Datos del grupo con id "+idGrupo[0].id+"</h3></center>"
            formulario+="<center class='container bg-success'>"
            formulario+="<br>"
                    formulario+="<div class='container'>"
                        formulario+="<div class='row'>"
                            formulario+="<div class='col-4'>"
                                formulario+="<label class='font-weight-bold h5'>Ingresa el nombre del grupo: </label>"
                            formulario+="</div>"
                            formulario+="<div class='col-8'>"
                                formulario+="<input class='form-control' type='text' placeholder='Sera un identificador de grupo' name='nombre' value='"+idGrupo[0].nombre+"' required></input>"
                            formulario+="</div>"
                        formulario+="</div>"
                        formulario+="<br>"
                        formulario+="<div class='row'>"
                            formulario+="<div class='col-4'>"
                                formulario+="<label class='font-weight-bold h5'>Ingresa el año del grupo: </label>"
                            formulario+="</div>"
                            formulario+="<div class='col-8'>"
                                formulario+="<input class='form-control' type='text' placeholder='Ejemplo: 2018' name='anio' value='"+idGrupo[0].ano+"' required></input>"
                            formulario+="</div>"
                        formulario+="</div>"
                        formulario+="<br>"
                        formulario+="<div class='row'>"
                            formulario+="<div class='col-4'>"
                                formulario+="<label class='font-weight-bold h5'>Ingresa el turno del grupo: </label>"
                            formulario+="</div>"
                            formulario+="<div class='col-8'>"
                                formulario+="<input class='form-control' type='text' placeholder='Matutino/Vespertino' name='turno' value='"+idGrupo[0].turno+"' required></input>"
                            formulario+="</div>"
                        formulario+="</div>"
                        formulario+="<br>"
                        formulario+="<div class='row'>"
                            formulario+="<div class='col-12'>"
                                formulario+="<button class='btn btn-secondary btn-lg btn-outline-light btn-block' onclick='guardarInfo()'>Guardar grupo</button>"
                            formulario+="</div>    "
                        formulario+="</div>"
                        formulario+="<br>"
                    formulario+="</div>"
        formulario+="</center>"
                                    
                                    
               document.getElementById("profesor").innerHTML=formulario;
            }
        }
        xmlhttp.open("GET", "../json/respuestaConsultaUnGrupo.json", true);
        xmlhttp.send();
    }

function guardarInfo(){
    var nombre=document.getElementsByName("nombre");
    var anio=document.getElementsByName("anio");
    var turno=document.getElementsByName("turno");
    
    console.log(nombre[0].value)    
    console.log(idUsuarioid)
        let url = 'http://localhost:8080/ProjectFinalStruts/ModificarGrupo?idGrupo='+idGrupoid+"&nombre="+nombre[0].value+"&ano="+anio[0].value+"&turno="+turno[0].value;
    fetch(url).then(response => response.text()).then(data => {
        alert(data);
        
    });
    
    
    
    
}