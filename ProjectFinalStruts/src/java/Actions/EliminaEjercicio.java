
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.Ejercicio;
import xml.XMLActions;


public class EliminaEjercicio extends ActionSupport {
    
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
    
    public EliminaEjercicio() {
    }
    
    @Override
    public String execute() {
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlejercicios();
        
        XMLActions xml=new XMLActions();
        List listaEjercicios=xml.cargarXml(ruta);
        ArrayList<Ejercicio> ejercicios=xml.convierte2ArrayList(listaEjercicios);
        xml.eliminaEjercicio(ejercicios, getNumero());
        if(xml.guardarXml(ejercicios, ruta)==true){
            return SUCCESS;
        }else{
            return ERROR;
        }
        
    }
    
}
