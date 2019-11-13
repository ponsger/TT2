function muestraGrupoSinProfe(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
            if (this.readyState == 4 && this.status == 200){
                    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarGruposSinProfesor';
                    fetch(url).then(response => response.text()).then(data => {
                        alert(data);
                    var dataArray = JSON.parse(this.responseText);
                    console.log(this)
                    console.log(dataArray)
                    var idGrupoSinProfesor=dataArray.idGrupoSinProfesor;
                    console.log(idGrupoSinProfesor)
                    var i=0;
                        var tabla="<br><br><center class='contenido-mediano'>"
                        tabla+="<center><h3 class='text-white'>Tabla de grupos sin profesor</h3></center>"
                        tabla+="<table class='table table-hover table-bordered table-dark'>"
                        tabla+="<thead>"
                        tabla+="  <tr>"
                        tabla+="<th scope='col' class='bg-warning'>Id</th>"
                        tabla+="<th scope='col' class='bg-warning'>Nombre</th>"
                        tabla+="</tr></thead><tbody>";
                        while(true){
                            console.log(idGrupoSinProfesor[i])
                            tabla+="<tr>"
                            tabla+="<td>"+idGrupoSinProfesor[i].id+"</td>"
                        tabla+="<td>"+idGrupoSinProfesor[i].nombre+"</td>"
                            tabla+="</tr>"
                            i++;
                            if(idGrupoSinProfesor[i]===undefined){
                                break;
                            }
                        }
                        tabla+="</tbody></table></center>"
                        document.getElementById("tabla4").innerHTML=tabla;
                        
                    });
                    }
                    
            };
        xmlhttp.open("GET", "../json/resultadoConsultaGrupoSinProfesor.json", true);
        xmlhttp.send();
        }