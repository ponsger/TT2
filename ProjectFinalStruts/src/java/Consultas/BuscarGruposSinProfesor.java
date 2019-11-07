package Consultas;

import entitys.Alumno;
import entitys.HibernateUtil;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import static Complementos.Operaciones.*;
import entitys.Grupo;
import entitys.Usuario;
import java.util.Iterator;
import org.hibernate.Query;

/**
 *
 * @author RodrigoSalazar
 */
public class BuscarGruposSinProfesor {
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        String hql = "FROM Grupo";
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();
        
        JSONObject obj = new JSONObject();
        
        while(results.hasNext()){
            Grupo grupo = (Grupo)results.next();
            int idProfesor = grupo.getProfesor().getIdUsuario();
            
            if(idProfesor == 0){
                JSONObject innerObj = new JSONObject();
                innerObj.put("nombre", grupo.getNombre());
                obj.put(grupo.getIdGrupo(), innerObj);
            }
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
