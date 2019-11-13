package Actions.Profesor;

import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.Pregunta;
import xml.XMLActions;
import static Complementos.Operaciones.SUCCESS;
import static Complementos.Operaciones.ERROR;

/**
 *
 * @author RodrigoSalazar
 */
public class AgregarPregunta {
    private int idProfesor;
    private String tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        
        Pregunta pregunta = new Pregunta();
        XMLActions xml = new XMLActions();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlpreguntas();
        List listas = xml.cargarXmlPreguntas(ruta);
        ArrayList<Pregunta> datos = xml.convierte2ArrayListPreguntas(listas);
        
        pregunta.setNumero(Integer.toString(listas.size() + 1));
        pregunta.setIndicaciones(indicaciones);
        pregunta.setNombre(nombre);
        pregunta.setOpcion1(opcion1);
        pregunta.setOpcion2(opcion2);
        pregunta.setRespuesta(respuesta);
        pregunta.setTipo(tipo);
        pregunta.setCopiaPregunta(0);
        
        datos.add(pregunta);
        
        if(xml.guardarXmlPregunta(datos, ruta) == true){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
