package Consultas;

import entitys.Usuario;
import entitys.HibernateUtil;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import static Complementos.Operaciones.*;
import java.util.Iterator;
import org.hibernate.Query;

/**
 *
 * @author RodrigoSalazar
 */
public class BuscarTodosLosUsuarios {
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        JSONObject obj = new JSONObject();
        
        String hql = "FROM Usuario";
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();        
        
        while(results.hasNext()){
            Usuario usuario = (Usuario)results.next();
            
            JSONObject innerObj = new JSONObject();
            innerObj.put("nombre", usuario.getNombres());
            
            obj.put(usuario.getIdUsuario(),innerObj);
        }
        
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
