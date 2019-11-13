package Actions.Alumno;

import static Complementos.Operaciones.ERROR;
import static Complementos.Operaciones.SUCCESS;
import entitys.Alumno;
import entitys.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.RespuestaEjercicio;
import xml.RespuestaPregunta;
import xml.XMLActions;

/**
 *
 * @author RodrigoSalazar
 */
public class RegistrarRespuestaEjercicios {
    private int idAlumno;
    private int numeroEjercicio;
    private String respuesta;

    public int getNumeroEjercicio() {
        return numeroEjercicio;
    }

    public void setNumeroEjercicio(int numeroEjercicio) {
        this.numeroEjercicio = numeroEjercicio;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        RespuestaEjercicio re = new RespuestaEjercicio();
        XMLActions xml = new XMLActions();
        
        re.setNumeroEjercicio(this.numeroEjercicio);
        re.setRespuestaEjercicio(this.respuesta);
        
        Alumno alumno = (Alumno)hibernateSession.load(Alumno.class, this.idAlumno);
        String ruta = alumno.getRutaXmlrespuestas();
        
        List preguntas = xml.cargarXmlRespuestasPreguntas(ruta);
        List ejercicios = xml.cargarXmlRespuestasEjercicios(ruta);
        
        ArrayList<RespuestaPregunta> datosPreguntas = xml.convierte2ArrayListRespuestasPreguntas(preguntas);
        ArrayList<RespuestaEjercicio> datosEjercicios = xml.convierte2ArrayListRespuestasEjercicios(ejercicios);
        datosEjercicios.add(re);
        
        if(xml.guardarXmlRespuestas(datosPreguntas, datosEjercicios, ruta)){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
