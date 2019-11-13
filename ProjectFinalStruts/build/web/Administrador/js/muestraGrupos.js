function muestraGrupos(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function(){
            if (this.readyState == 4 && this.status == 200){
                    let url = 'http://localhost:8080/ProjectFinalStruts/BuscarTodosLosGrupos';
                    fetch(url).then(response => response.text()).then(data => {
                        alert(data);
                    var dataArray = JSON.parse(this.responseText);
                    console.log(this)
                    console.log(dataArray)
                    var idGrupo=dataArray.idGrupo;
                    console.log(idGrupo)
                    var i=0;
                        var tabla="<br><br><center class='contenido-mediano'>"
                        tabla+="<center><h3 class='text-white'>Tabla de grupos</h3></center>"
                        tabla+="<table class='table table-hover table-bordered table-dark'>"
                        tabla+="<thead>"
                        tabla+="  <tr>"
                        tabla+="<th scope='col' class='bg-warning'>Id</th>"
                        tabla+="<th scope='col' class='bg-warning'>Nombre</th>"
                        tabla+="<th scope='col' class='bg-warning'>Año</th>"
                        tabla+="<th scope='col' class='bg-warning'>Turno</th>"
                        tabla+="</tr></thead><tbody>";
                        while(true){
                            console.log(idGrupo[i])
                            tabla+="<tr>"
                            tabla+="<td>"+idGrupo[i].id+"</td>"
                        tabla+="<td>"+idGrupo[i].nombre+"</td>"
                        tabla+="<td>"+idGrupo[i].ano+"</td>"
                        tabla+="<td>"+idGrupo[i].turno+"</td>"
                            tabla+="</tr>"
                            i++;
                            if(idGrupo[i]===undefined){
                                break;
                            }
                        }
                        tabla+="</tbody></table></center>"
                        document.getElementById("tabla2").innerHTML=tabla;
                        
                    });
                    }
                    
            };
        xmlhttp.open("GET", "../json/resultadoConsultaGrupos.json", true);
        xmlhttp.send();
        }