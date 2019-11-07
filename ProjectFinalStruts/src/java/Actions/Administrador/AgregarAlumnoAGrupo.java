package Actions.Administrador;

import java.io.Serializable;
import static Complementos.Operaciones.*;
import entitys.Alumno;
import entitys.Grupo;
import entitys.HibernateUtil;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author RodrigoSalazar
 */
public class AgregarAlumnoAGrupo implements Serializable{
    private int idAlumno;
    private int idGrupo;

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
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
        Transaction t = hibernateSession.beginTransaction();     
        
        Alumno alumno = (Alumno)hibernateSession.load(Alumno.class, this.idAlumno);
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.idGrupo);
        
        alumno.setGrupo(grupo);
        hibernateSession.update(alumno);
        t.commit();
        
        Set setGrupo = grupo.getAlumnos();
        setGrupo.add(this.idAlumno);
        hibernateSession.update(grupo);
        t.commit();
        return SUCCESS;
    }
    
}
