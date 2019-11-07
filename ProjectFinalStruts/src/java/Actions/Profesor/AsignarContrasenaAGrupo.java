package Actions.Profesor;

import java.io.Serializable;
import static Complementos.Operaciones.*;
import entitys.Alumno;
import entitys.Grupo;
import entitys.HibernateUtil;
import entitys.Usuario;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author RodrigoSalazar
 */
public class AsignarContrasenaAGrupo implements Serializable{
    private int idGrupo;
    private String nuevaContrasena;

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
    
    public String execute(){
        Session session;
        session = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = session.beginTransaction();
        
        Grupo grupo = (Grupo)session.load(Grupo.class, this.idGrupo);
        
        String hql = "from Alumno where Grupo = " + grupo;
        Query query = session.createQuery(hql);
        Iterator resultados = query.iterate();
        
        while(resultados.hasNext()){
            Alumno alumno = (Alumno)resultados.next();
            int id = alumno.getIdUsuario();
            Usuario usuario = (Usuario)session.load(Usuario.class, id);
            usuario.setContrasena(nuevaContrasena);
            session.update(usuario);
            t.commit();
        }
        return SUCCESS;
    }
}
