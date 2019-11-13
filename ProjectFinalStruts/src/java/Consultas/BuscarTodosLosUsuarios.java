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
import org.apache.struts2.ServletActionContext;
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
        
        JSONObject raiz = new JSONObject();
        JSONObject obj = new JSONObject();
        
        String hql = "FROM Usuario";
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();        
        int contador=0;
        while(results.hasNext()){
            Usuario usuario = (Usuario)results.next();
            
            JSONObject innerObj = new JSONObject();
            innerObj.put("nombre", usuario.getNombre());
            innerObj.put("id",usuario.getIdUsuario());
            innerObj.put("apPat",usuario.getApPaterno());
            innerObj.put("apMat", usuario.getApMat());
            innerObj.put("username", usuario.getNombreUsuario());
            obj.put(contador,innerObj);
            raiz.put("idUsuario", obj);
            contador++;
            System.out.println("Contador: "+contador);
        }
        
        try{
            String hola=ServletActionContext.getServletContext().getRealPath("/json/resultadoConsultaUsuarios.json");
            System.out.println("***************************************************************");
            System.out.println(hola);
            
            FileWriter file = new FileWriter(ServletActionContext.getServletContext().getRealPath("/json/resultadoConsultaUsuarios.json"));
            file.write(raiz.toJSONString());
            file.flush();
            file.close();
            System.out.println("***************************************************************");
            System.out.println("***************************Guardado******************************");
            return SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        
        
    }
}
