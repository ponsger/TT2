function muestraUsuarios(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
            if (this.readyState == 4 && this.status == 200){
                    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarTodosLosUsuarios';
                    fetch(url).then(response => response.text()).then(data => {
                        alert(data);
                    var dataArray = JSON.parse(this.responseText);
                    var idUsuario=dataArray.idUsuario;
                    console.log(idUsuario)
                    var i=0;
                        var tabla="<br><br><center class='contenido-mediano'>"
                        tabla+="<center><h3 class='text-white'>Tabla de usuarios</h3></center>"
                        tabla+="<table class='table table-hover table-bordered table-dark'>"
                        tabla+="<thead>"
                        tabla+="  <tr>"
                        tabla+="<th scope='col' clas='bg-success'>Id</th>"
                        tabla+="<th scope='col' class='bg-success'>Nombre</th>"
                        tabla+="<th scope='col' class='bg-success'>Apellido Paterno</th>"
                        tabla+="<th scope=col' class='bg-success'>Apellido Materno</th>"
                        tabla+="<th scope='col' class='bg-success'>Nombre de usuario</th>"
                        tabla+="</tr></thead><tbody>";
                        while(true){
                            console.log(idUsuario[i])
                            tabla+="<tr>"
                            tabla+="<td>"+idUsuario[i].id+"</td>"
                            tabla+="<td>"+idUsuario[i].nombre+"</td>"
                            tabla+="<td>"+idUsuario[i].apPat+"</td>"
                            tabla+="<td>"+idUsuario[i].apMat+"</td>"
                            tabla+="<td>"+idUsuario[i].username+"</td>"
                            tabla+="</tr>"
                            i++;
                            if(idUsuario[i]===undefined){
                                break;
                            }
                        }
                        tabla+="</tbody></table></center>"
                        document.getElementById("tabla1").innerHTML=tabla;
                        
                    });
                    }
                    
            };
        
        xmlhttp.open("GET", "../json/resultadoConsultaUsuarios.json", true);
        xmlhttp.send();
        }