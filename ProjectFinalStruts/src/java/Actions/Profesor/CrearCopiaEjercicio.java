package Actions.Profesor;

import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.XMLActions;
import static Complementos.Operaciones.SUCCESS;
import static Complementos.Operaciones.ERROR;
import xml.Ejercicio;

/**
 *
 * @author RodrigoSalazar
 */
public class CrearCopiaEjercicio{
    private int idProfesor;
    private int numeroEjercicio;

    public int getNumeroEjercicio() {
        return numeroEjercicio;
    }

    public void setNumeroEjercicio(int numeroEjercicio) {
        this.numeroEjercicio = numeroEjercicio;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Ejercicio ejercicio = new Ejercicio();
        XMLActions xml = new XMLActions();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String ruta = profesor.getRutaXmlejercicios();        
        List listas = xml.cargarXml(ruta);
        ArrayList<Ejercicio> datos = xml.convierte2ArrayList(listas);
        
        Ejercicio eac = datos.get(this.numeroEjercicio);
        
        ejercicio.setNumero(Integer.toString(listas.size() + 1));
        ejercicio.setPregunta(eac.getPregunta());
        ejercicio.setNombre(eac.getNombre());
        ejercicio.setOpcion1(eac.getOpcion1());
        ejercicio.setOpcion2(eac.getOpcion2());
        ejercicio.setOpcion3(eac.getOpcion3());
        ejercicio.setOpcion4(eac.getOpcion4());
        ejercicio.setResultado(eac.getResultado());
        ejercicio.setTipo(eac.getTipo());
        ejercicio.setCopiaEjercicio(Integer.parseInt(eac.getNumero()));
        
        datos.add(ejercicio);
        
        if(xml.guardarXml(datos, ruta) == true){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
