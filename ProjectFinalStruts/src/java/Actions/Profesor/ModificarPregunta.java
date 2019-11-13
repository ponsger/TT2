package Actions.Profesor;

import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.Pregunta;
import xml.XMLActions;
import static Complementos.Operaciones.*;

/**
 *
 * @author RodrigoSalazar
 */
public class ModificarPregunta {
    private int idProfesor;
    private String numero;
    private String indicaciones;
    private String respuesta;
    private String nombre;
    private String opcion1;
    private String opcion2;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlpreguntas();
        
        XMLActions xml = new XMLActions();        
        List lista = xml.cargarXmlPreguntas(ruta);
        ArrayList<Pregunta> preguntas = xml.convierte2ArrayListPreguntas(lista);
        Pregunta original = preguntas.get(Integer.parseInt(this.numero) - 1);
        Pregunta preguntaModificada = new Pregunta();
        
        preguntaModificada.setNombre(nombre);
        preguntaModificada.setIndicaciones(indicaciones);
        preguntaModificada.setNumero(numero);
        preguntaModificada.setOpcion1(opcion1);
        preguntaModificada.setOpcion2(opcion2);
        preguntaModificada.setRespuesta(respuesta);
        preguntaModificada.setTipo(original.getTipo());
        
        preguntas = xml.modificaPregunta(preguntas, numero, preguntaModificada);
        
        if(xml.guardarXmlPregunta(preguntas, ruta)==true){
            return SUCCESS;
        }else{
            return ERROR;
        }
    }
}
