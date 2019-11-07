package Actions.Administrador;

import entitys.Profesor;
import java.util.HashSet;
import java.util.Set;
import static Complementos.Operaciones.*;
import entitys.Grupo;
import entitys.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import xml.XMLActions;

/**
 *
 * @author RodrigoSalazar
 */
public class AgregarGrupo implements Serializable{
    
    private int idGrupo;
    private String nombre;
    private int ano;
    private String turno;
    
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Grupo grupo = new Grupo();
        
        grupo.setIdGrupo(0);
        grupo.setNombre(nombre);
        grupo.setAno(ano);
        grupo.setTurno(turno);
        
        Profesor profe = new Profesor();
        grupo.setProfesor(profe);
        
        Set alumnos = new HashSet(0);
        grupo.setAlumnos(alumnos);
        
        XMLActions xml = new XMLActions();
        
        if(xml.crearXMLAsignado(nombre)){
            grupo.setRutaXMLAsignados("xml/Grupo" + nombre + "/asignados.xml");
        }
        
        hibernateSession.save(grupo);
        t.commit();
        
        return SUCCESS;
    }
}
