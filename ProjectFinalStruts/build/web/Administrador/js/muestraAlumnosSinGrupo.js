function muestraAlumnosSinGrupo(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
            if (this.readyState == 4 && this.status == 200){
                    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarAlumnosSinGrupo';
                    fetch(url).then(response => response.text()).then(data => {
                        alert(data);
                    var dataArray = JSON.parse(this.responseText);
                    console.log(this)
                    console.log(dataArray)
                    var idAlumno=dataArray.idAlumno;
                    console.log(idGrupo)
                    var i=0;
                        var tabla="<br><br><center class='contenido-mediano'>"
                        tabla+="<center><h3 class='text-white'>Tabla de Alumnos</h3></center>"
                        tabla+="<table class='table table-hover table-bordered table-dark'>"
                        tabla+="<thead>"
                        tabla+="  <tr>"
                        tabla+="<th scope='col' class='bg-danger'>Id</th>"
                        tabla+="<th scope='col' class='bg-danger'>Nombre</th>"
                        tabla+="<th scope='col' class='bg-danger'>Apellido Paterno</th>"
                        tabla+="<th scope='col' class='bg-danger'>Apellido Materno</th>"
                        tabla+="</tr></thead><tbody>";
                        while(true){
                            console.log(idAlumno[i])
                            tabla+="<tr>"
                            tabla+="<td>"+idAlumno[i].id+"</td>"
                        tabla+="<td>"+idAlumno[i].nombre+"</td>"
                        tabla+="<td>"+idAlumno[i].apPat+"</td>"
                        tabla+="<td>"+idAlumno[i].apMat+"</td>"
                            tabla+="</tr>"
                            i++;
                            if(idGrupo[i]===undefined){
                                break;
                            }
                        }
                        tabla+="</tbody></table></center>"
                        document.getElementById("tabla1").innerHTML=tabla;
                        
                    });
                    }
                    
            };
        xmlhttp.open("GET", "../json/resultadoConsultaAlumnoSinGrupo.json", true);
        xmlhttp.send();
        }