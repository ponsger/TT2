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
public class CrearCopiaPregunta {
    private int idProfesor;
    private int numeroPregunta;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getNumeroPregunta() {
        return numeroPregunta;
    }

    public void setNumeroPregunta(int numeroPregunta) {
        this.numeroPregunta = numeroPregunta;
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
        
        Pregunta pac = datos.get(this.numeroPregunta);
        
        pregunta.setNumero(Integer.toString(listas.size() + 1));
        pregunta.setIndicaciones(pac.getIndicaciones());
        pregunta.setNombre(pac.getNombre());
        pregunta.setOpcion1(pac.getOpcion1());
        pregunta.setOpcion2(pac.getOpcion2());
        pregunta.setRespuesta(pac.getRespuesta());
        pregunta.setTipo(pac.getTipo());
        pregunta.setCopiaPregunta(Integer.parseInt(pac.getNumero()));
        
        datos.add(pregunta);
        
        if(xml.guardarXmlPregunta(datos, ruta) == true){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
