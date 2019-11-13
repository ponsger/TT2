package Actions.Profesor;

import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import xml.Examen;
import xml.XMLActions;
import static Complementos.Operaciones.*;

/**
 *
 * @author RodrigoSalazar
 */
public class AgregarEjerciciosAExamen {
    private String numeroEjercicios;
    private String nombreExamen;
    private int idProfesor;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNumeroEjercicios() {
        return numeroEjercicios;
    }

    public void setNumeroEjercicios(String numeroEjercicios) {
        this.numeroEjercicios = numeroEjercicios;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        
        XMLActions xml = new XMLActions();
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        String rutaExamen = profesor.getRutaXmlexamen();
        
        List listaExamen = xml.cargarXmlExamenes(rutaExamen);
        ArrayList<Examen> examenes = xml.convierteList2ArrayListExamenAgregado(listaExamen);
        String[] numerosEjercicios = this.numeroEjercicios.split(",");
        
        int indicador = 0;
        
        for(int i = 0; i < examenes.size(); i++){
            if(this.nombreExamen.equals(examenes.get(i).getNombre())){
                indicador = i;
            }
        }    
        
        Examen nuevo = examenes.get(indicador);
        nuevo.setEjercicios(xml.regresaEjerciciosA(numerosEjercicios));
        examenes.remove(indicador);
        examenes.add(nuevo);
        
        if(xml.guardarXMLExamenAgregado(examenes)){
            return SUCCESS;
        }else{
            return ERROR;
        }

    }
}
