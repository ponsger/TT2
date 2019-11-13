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
import org.apache.struts2.ServletActionContext;
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
        
        JSONObject raiz = new JSONObject();
        JSONObject obj = new JSONObject();
        
        String hql = "FROM Grupo";
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();        
        int contador=0;
        while(results.hasNext()){
            Grupo grupo = (Grupo)results.next();
            
            JSONObject innerObj = new JSONObject();
            innerObj.put("nombre", grupo.getNombre());
            innerObj.put("id", grupo.getIdGrupo());
            innerObj.put("ano", grupo.getAno());
            innerObj.put("turno", grupo.getTurno());
                    
            obj.put(contador,innerObj);
            raiz.put("idGrupo", obj);
            contador++;
        }
        try{
            String hola=ServletActionContext.getServletContext().getRealPath("/json");
            System.out.println("***************************************************************");
            System.out.println(hola);
            
            FileWriter file = new FileWriter(ServletActionContext.getServletContext().getRealPath("json/resultadoConsultaGrupos.json"));
            file.write(raiz.toJSONString());
            file.flush();
            file.close();
            System.out.println("Exitoso");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return SUCCESS;
    }
}
