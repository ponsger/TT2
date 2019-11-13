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
public class AsignarPreguntaAAlumnos {
    private String numerosAlumnos;
    private int idGrupo;
    private int numeroPregunta;

    public int getNumeroPregunta() {
        return numeroPregunta;
    }

    public void setNumeroPregunta(int numeroPregunta) {
        this.numeroPregunta = numeroPregunta;
    }

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
            PreguntasAsignadas pa = new PreguntasAsignadas();
            
            pa.setGrupoCompleto("no");
            pa.setIdGrupo(this.idGrupo);
            pa.setNumeroAlumno(Integer.parseInt(cacho));
            pa.setNumeroPregunta(this.numeroPregunta);
            
            datosPreguntas.add(pa);
        }
        
        if(xml.guardarXmlAsignados(datosExamenes, datosEjercicios, datosPreguntas, ruta)){
            return SUCCESS;
        }
        else{
            return ERROR;
        }
    }
}
