package Actions;

import com.opensymphony.xwork2.ActionSupport;
import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.Ejercicio;
import xml.Examen;
import xml.XMLActions;

public class AgregaExamen extends ActionSupport {
    String nombre,numeros;
    private int idProfesor;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
    
    
    public AgregaExamen() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }
    
    @Override
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlejercicios();
        
        XMLActions xmlExamen=new XMLActions();
        XMLActions xmlEjercicio=new XMLActions();
        List listaExamen=xmlExamen.cargarXmlExamen();
        List listaEjercicios=xmlEjercicio.cargarXml(ruta);
        ArrayList<Ejercicio> ejercicios=xmlEjercicio.convierte2ArrayList(listaEjercicios);
        ArrayList<Examen> examenes=xmlExamen.convierteList2ArrayListExamen(listaExamen);
        
        String[] numEjercicio=numeros.split(",");
        Examen nuevo=new Examen();
        nuevo.setEjercicios(xmlEjercicio.regresaEjercicios(numEjercicio,ejercicios));
        nuevo.setNombre(nombre);
        Date fecha=new Date();
        System.out.println("La fecha es: "+fecha.toGMTString());
        int fechaDia=fecha.getDay();
        int fechaMes=fecha.getMonth();
        int fechaAnio=fecha.getYear()+1900;
        String fechaString=""+fechaDia+"-"+fechaMes+"-"+fechaAnio;
        System.out.println(fechaString);
        nuevo.setFecha(fechaString);
        examenes.add(nuevo);
        if(xmlExamen.guardarXMLExamen(examenes)){
            return SUCCESS;
        }else{
            return ERROR;
        }
        
    }
    
}
