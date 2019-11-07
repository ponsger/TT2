package Actions.Administrador;

import java.io.Serializable;
import static Complementos.Operaciones.*;
import entitys.Alumno;
import entitys.Grupo;
import entitys.HibernateUtil;
import entitys.Profesor;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author RodrigoSalazar
 */
public class EliminarGrupo implements Serializable{
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String execute(){
        
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.id);
        
        Grupo nuevoGrupo = new Grupo();
        
        //Para borrar los registros de este grupo en la tabla alumnos
        String hql = "FROM Alumno WHERE Grupo = " + grupo;
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();
                
        while(results.hasNext()){
            Alumno alumno = (Alumno)results.next();
            alumno.setGrupo(nuevoGrupo);
            hibernateSession.update(alumno);
            t.commit();
        }
        
        //Para borrar los registros de este grupo en la tabla profesor
        Profesor profe = grupo.getProfesor();
        Set set = profe.getGrupos();
        set.clear();
        hibernateSession.update(profe);
        t.commit();
        
        //Borramos el grupo
        hibernateSession.delete(grupo);
        t.commit();
        return SUCCESS;
    }
}
