package Actions.Administrador;

import java.io.Serializable;
import static Complementos.Operaciones.*;
import entitys.HibernateUtil;
import entitys.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author RodrigoSalazar
 */
public class ModificarUsuario implements Serializable{
    
    private int id;
    private String nombres;
    private String apellidoPat;
    private String apellidoMat;
    private String nombreUsuario;
    private String contrasena;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Usuario user = (Usuario)hibernateSession.load(Usuario.class, this.id);
        
        user.setNombreUsuario(nombreUsuario);
        user.setNombres(nombres);
        user.setApellidoPat(apellidoPat);
        user.setApellidoMat(apellidoMat);
        user.setContrasena(contrasena);
        
        hibernateSession.update(user);
        t.commit();
        
        return SUCCESS;
    }
    
    
    
}
