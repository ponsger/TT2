package Consultas;

import entitys.HibernateUtil;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import static Complementos.Operaciones.*;
import entitys.Usuario;

/**
 *
 * @author RodrigoSalazar
 */
public class BuscarUnUsuario {
    private int idUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Usuario usuario = (Usuario)hibernateSession.load(Usuario.class, this.idUsuario);
        
        JSONObject obj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        
        innerObj.put("nombre", usuario.getNombres());
        innerObj.put("apellidoPat", usuario.getApellidoPat());
        innerObj.put("apellidoMat", usuario.getApellidoMat());
        innerObj.put("nombreUsuario", usuario.getNombreUsuario());
        innerObj.put("contrasena", usuario.getContrasena());
        
        obj.put(usuario.getIdUsuario(), innerObj);

        try{
            FileWriter file = new FileWriter("C:\\jars\\json\\resultadoConsulta.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return SUCCESS;
    }
}
