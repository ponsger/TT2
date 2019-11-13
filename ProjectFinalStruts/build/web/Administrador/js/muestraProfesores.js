function muestraProfesores(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
            if (this.readyState == 4 && this.status == 200){
                    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarTodosLosProfesores';
                    fetch(url).then(response => response.text()).then(data => {
                        alert(data);
                    var dataArray = JSON.parse(this.responseText);
                    console.log(this)
                    console.log(dataArray)
                    var idProfesores=dataArray.idProfesores;
                    console.log(idProfesores)
                    var i=0;
                        var tabla="<br><br><center class='contenido-mediano'>"
                        tabla+="<center><h3 class='text-white'>Tabla de profesores</h3></center>"
                        tabla+="<table class='table table-hover table-bordered table-dark'>"
                        tabla+="<thead>"
                        tabla+="  <tr>"
                        tabla+="<th scope='col' class='bg-success'>Id</th>"
                        tabla+="<th scope='col' class='bg-success'>Nombre</th>"
                        tabla+="<th scope='col' class='bg-success'>Apellido paterno</th>"
                        tabla+="<th scope='col' class='bg-success'>Apellido materno</th>"
                        tabla+="<th scope='col' class='bg-success'>Nombre de usuario</th>"
                        tabla+="</tr></thead><tbody>";
                        while(true){
                            console.log(idProfesores[i])
                            tabla+="<tr>"
                            tabla+="<td>"+idProfesores[i].id+"</td>"
                        tabla+="<td>"+idProfesores[i].nombre+"</td>"
                        tabla+="<td>"+idProfesores[i].apPat+"</td>"
                        tabla+="<td>"+idProfesores[i].apMat+"</td>"
                        tabla+="<td>"+idProfesores[i].username+"</td>"
                            tabla+="</tr>"
                            i++;
                            if(idProfesores[i]===undefined){
                                break;
                            }
                        }
                        tabla+="</tbody></table></center>"
                        document.getElementById("tabla3").innerHTML=tabla;
                    });
                    }
                    
            };
        xmlhttp.open("GET", "../json/resultadoConsultaProfesores.json", true);
                   

        xmlhttp.send();
        }