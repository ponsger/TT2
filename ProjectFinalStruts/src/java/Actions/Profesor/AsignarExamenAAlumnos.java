package Actions.Profesor;

import static Complementos.Operaciones.ERROR;
import static Complementos.Operaciones.SUCCESS;
import entitys.Grupo;
import entitys.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import xml.EjerciciosAsignados;
import xml.ExamenesAsignados;
import xml.PreguntasAsignadas;
import xml.XMLActions;

/**
 *
 * @author RodrigoSalazar
 */
public class AsignarExamenAAlumnos {
    private String numerosAlumnos;
    private int idGrupo;
    private int numeroExamen;

    public String getNumerosAlumnos() {
        return numerosAlumnos;
    }

    public void setNumerosAlumnos(String numerosAlumnos) {
        this.numerosAlumnos = numerosAlumnos;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getNumeroExamen() {
        return numeroExamen;
    }

    public void setNumeroExamen(int numeroExamen) {
        this.numeroExamen = numeroExamen;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 

        XMLActions xml = new XMLActions();
        
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.idGrupo);
        String ruta = grupo.getRutaXmlasignados();
        
        List examenes = xml.cargarXmlExamenesAsignados(ruta);
        List ejercicios = xml.cargarXmlEjerciciosAsignados(ruta);
        List preguntas = xml.cargarXmlPreguntasAsignadas(ruta);
        
        ArrayList<ExamenesAsignados> datosExamenes = xml.convierte2ArrayListExamenesAsignados(examenes);
        ArrayList<EjerciciosAsignados> datosEjercicios = xml.convierte2ArrayListEjerciciosAsignados(ejercicios);
        ArrayList<PreguntasAsignadas> datosPreguntas = xml.convierte2ArrayListPreguntasAsignadas(preguntas);
        
        String cachos[] = this.numerosAlumnos.split(",");
        
        for (String cacho : cachos) {
            ExamenesAsignados ea = new ExamenesAsignados();
            
            ea.setGrupoCompleto("no");
            ea.setIdGrupo(this.idGrupo);
            ea.setNumeroAlumno(Integer.parseInt(cacho));
            ea.setNumeroExamen(this.numeroExamen);
            
            datosExamenes.add(ea);
        }
        
        if(xml.guardarXmlAsignados(datosExamenes, datosEjercicios, datosPreguntas, ruta)){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
