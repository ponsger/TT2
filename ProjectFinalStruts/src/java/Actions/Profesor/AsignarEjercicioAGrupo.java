package Actions.Profesor;

import entitys.Grupo;
import entitys.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import xml.EjerciciosAsignados;
import xml.ExamenesAsignados;
import xml.PreguntasAsignadas;
import xml.XMLActions;
import static Complementos.Operaciones.*;

/**
 *
 * @author RodrigoSalazar
 */
public class AsignarEjercicioAGrupo {
    private int numeroEjercicio;
    private int idGrupo;

    public int getNumeroEjercicio() {
        return numeroEjercicio;
    }

    public void setNumeroEjercicio(int numeroEjercicio) {
        this.numeroEjercicio = numeroEjercicio;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 

        EjerciciosAsignados ea = new EjerciciosAsignados();
        XMLActions xml = new XMLActions();
        
        ea.setGrupoCompleto("si");
        ea.setIdGrupo(this.idGrupo);
        ea.setNumeroAlumno(0);
        ea.setNumeroEjercicio(this.numeroEjercicio);
        
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.idGrupo);
        String ruta = grupo.getRutaXmlasignados();
        
        List examenes = xml.cargarXmlExamenesAsignados(ruta);
        List ejercicios = xml.cargarXmlEjerciciosAsignados(ruta);
        List preguntas = xml.cargarXmlPreguntasAsignadas(ruta);
        
        ArrayList<ExamenesAsignados> datosExamenes = xml.convierte2ArrayListExamenesAsignados(examenes);
        ArrayList<EjerciciosAsignados> datosEjercicios = xml.convierte2ArrayListEjerciciosAsignados(ejercicios);
        ArrayList<PreguntasAsignadas> datosPreguntas = xml.convierte2ArrayListPreguntasAsignadas(preguntas);
        
        datosEjercicios.add(ea);
        
        if(xml.guardarXmlAsignados(datosExamenes, datosEjercicios, datosPreguntas, ruta)){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
