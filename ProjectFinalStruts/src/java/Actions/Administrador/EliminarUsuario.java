package Actions.Administrador;

import entitys.HibernateUtil;
import entitys.Usuario;
import entitys.Grupo;
import entitys.Profesor;
import entitys.Alumno;
import static Complementos.Operaciones.SUCCESS;
import java.io.Serializable;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Set;

/**
 *
 * @author RodrigoSalazar
 */

public class EliminarUsuario implements Serializable{
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
        System.out.println("Id: "+this.id);
        Usuario usuario = (Usuario)hibernateSession.createQuery("from Usuario where idUsuario=" + this.id).uniqueResult();
        
        switch(usuario.getTipo().getIdTipo()){
            case 2: //profesor
                
                Profesor profe = (Profesor)hibernateSession.createQuery("from Profesor where idUsuario="+ this.id); 
                Profesor profe2 = new Profesor();
                
                String hql = "FROM Grupo WHERE Profesor = " + profe;
                Query query = hibernateSession.createQuery(hql);
                Iterator results = query.iterate();
                
                while(results.hasNext()){
                    Grupo group = (Grupo)results.next();
                    group.setProfesor(profe2);
                    hibernateSession.update(group);
                    t.commit();
                }
                
                hibernateSession.delete(profe);
                t.commit();
                
                break;
                
            case 3: //alumno, tengo que borrar el id del set de alumnos.
                Alumno alum = (Alumno)hibernateSession.load(Alumno.class, this.id);
                Grupo group = alum.getGrupo();
                Set set = group.getAlumnos();
                set.remove(this.id);
                hibernateSession.update(group);
                
                hibernateSession.delete(alum);
                t.commit();
                break;
        }
                
        hibernateSession.delete(usuario);
        t.commit();
        return SUCCESS;
    }
    
}
