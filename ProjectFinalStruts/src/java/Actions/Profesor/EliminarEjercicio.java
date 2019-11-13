package Actions.Profesor;

import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.XMLActions;
import static Complementos.Operaciones.*;
import xml.Ejercicio;

/**
 *
 * @author RodrigoSalazar
 */
public class EliminarEjercicio {
    private int numero;
    private int idProfesor;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlejercicios();
        
        XMLActions xml = new XMLActions();
        List listaEjercicios = xml.cargarXmlEjercicios(ruta);
        ArrayList<Ejercicio> ejercicios = xml.convierte2ArrayListEjercicios(listaEjercicios);
        xml.eliminaEjercicio(ejercicios, this.numero);
        if(xml.guardarXmlEjercicio(ejercicios, ruta) == true){
            return SUCCESS;
        }
        else{
            return ERROR;
        }        
    }
}
