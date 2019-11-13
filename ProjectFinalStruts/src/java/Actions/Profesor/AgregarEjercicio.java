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
import xml.Ejercicio;

/**
 *
 * @author RodrigoSalazar
 */
public class AgregarEjercicio {
    private int idProfesor;
    private String tipo;
    private String indicaciones;
    private String resultado;
    private String nombre;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

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

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        
        Ejercicio ejercicio = new Ejercicio();
        XMLActions xml = new XMLActions();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlejercicios();
        List listas = xml.cargarXmlEjercicios(ruta);
        ArrayList<Ejercicio> datos = xml.convierte2ArrayListEjercicios(listas);
        
        ejercicio.setNumero(Integer.toString(listas.size() + 1));
        ejercicio.setPregunta(indicaciones);
        ejercicio.setNombre(nombre);
        ejercicio.setOpcion1(opcion1);
        ejercicio.setOpcion2(opcion2);
        ejercicio.setOpcion2(opcion3);
        ejercicio.setOpcion2(opcion4);
        ejercicio.setResultado(resultado);
        ejercicio.setTipo(tipo);
        
        datos.add(ejercicio);
        
        if(xml.guardarXmlEjercicio(datos, ruta) == true){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
