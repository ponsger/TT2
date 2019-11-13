package Actions.Alumno;

import entitys.Alumno;
import entitys.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.RespuestaEjercicio;
import xml.RespuestaPregunta;
import xml.XMLActions;
import static Complementos.Operaciones.*;

/**
 *
 * @author RodrigoSalazar
 */
public class RegistrarRespuestaPregunta {
    private int idAlumno;
    private int numeroPregunta;
    private String respuesta;

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getNumeroPregunta() {
        return numeroPregunta;
    }

    public void setNumeroPregunta(int numeroPregunta) {
        this.numeroPregunta = numeroPregunta;
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
        
        RespuestaPregunta rp = new RespuestaPregunta();
        XMLActions xml = new XMLActions();
        
        rp.setNumeroPregunta(this.numeroPregunta);
        rp.setRespuestaPregunta(this.respuesta);
        
        Alumno alumno = (Alumno)hibernateSession.load(Alumno.class, this.idAlumno);
        String ruta = alumno.getRutaXmlrespuestas();
        
        List preguntas = xml.cargarXmlRespuestasPreguntas(ruta);
        List ejercicios = xml.cargarXmlRespuestasEjercicios(ruta);
        
        ArrayList<RespuestaPregunta> datosPreguntas = xml.convierte2ArrayListRespuestasPreguntas(preguntas);
        ArrayList<RespuestaEjercicio> datosEjercicios = xml.convierte2ArrayListRespuestasEjercicios(ejercicios);
        datosPreguntas.add(rp);
        
        if(xml.guardarXmlRespuestas(datosPreguntas, datosEjercicios, ruta)){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
