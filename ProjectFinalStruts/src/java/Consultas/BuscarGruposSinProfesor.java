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
import org.apache.struts2.ServletActionContext;
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
        
        JSONObject raiz = new JSONObject();
        JSONObject obj = new JSONObject();
        int contador=0;
        while(results.hasNext()){
            Grupo grupo = (Grupo)results.next();
            int idProfesor = grupo.getProfesor().getIdUsuario();
            
            if(idProfesor == 0){
                JSONObject innerObj = new JSONObject();
                innerObj.put("nombre", grupo.getNombre());
                innerObj.put("id",grupo.getIdGrupo());
                obj.put(contador, innerObj);
                raiz.put("idGrupoSinProfesor", obj);
                contador++;
            }
        }
        try{
            String hola=ServletActionContext.getServletContext().getRealPath("/json");
            System.out.println("***************************************************************");
            System.out.println(hola);
            FileWriter file = new FileWriter(ServletActionContext.getServletContext().getRealPath("json/resultadoConsultaGrupoSinProfesor.json"));
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
