package Consultas;

import entitys.Grupo;
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
public class BuscarTodosLosGrupos {
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        JSONObject obj = new JSONObject();
        
        String hql = "FROM Grupo";
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();        
        
        while(results.hasNext()){
            Grupo grupo = (Grupo)results.next();
            
            JSONObject innerObj = new JSONObject();
            innerObj.put("nombre", grupo.getNombre());
            
            obj.put(grupo.getIdGrupo(),innerObj);
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
