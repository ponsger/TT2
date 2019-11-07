package Actions.Administrador;

import java.io.Serializable;
import static Complementos.Operaciones.*;
import entitys.Grupo;
import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author RodrigoSalazar
 */
public class AgregarProfesorAGrupo implements Serializable{
    private int idProfesor;
    private int idGrupo;

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
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
        
        Profesor profesor = (Profesor)hibernateSession.load(Profesor.class, this.idProfesor);
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.idGrupo);
        
        grupo.setProfesor(profesor);
        hibernateSession.update(grupo);
        t.commit();
        
        Set setProfesor = profesor.getGrupos();
        setProfesor.add(this.idGrupo);
        hibernateSession.update(profesor);
        t.commit();
        
        return SUCCESS;
    }
    
}
