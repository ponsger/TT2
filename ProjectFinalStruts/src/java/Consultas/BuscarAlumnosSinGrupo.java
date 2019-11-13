package Consultas;

import entitys.Alumno;
import entitys.HibernateUtil;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import static Complementos.Operaciones.*;
import entitys.Usuario;
import java.util.Iterator;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;

/**
 *
 * @author RodrigoSalazar
 */
public class BuscarAlumnosSinGrupo {
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        String hql = "FROM Alumno";
        Query query = hibernateSession.createQuery(hql);
        Iterator results = query.iterate();
        
        JSONObject raiz = new JSONObject();
        JSONObject obj = new JSONObject();
        int contador=0;
        while(results.hasNext()){
            Alumno alumno = (Alumno)results.next();
            int idGrupo = alumno.getGrupo().getIdGrupo();
            
            if(idGrupo == 0){
                Usuario usuario = (Usuario)hibernateSession.load(Usuario.class, alumno.getIdUsuario());
                JSONObject innerObj = new JSONObject();
                innerObj.put("nombre", usuario.getNombre());
                innerObj.put("id", usuario.getIdUsuario());
                innerObj.put("apPat",usuario.getApPaterno());
                innerObj.put("apMat",usuario.getApMat());
                obj.put(contador, innerObj);
                raiz.put("idAlumno", obj);
                contador++;
            }
        }
        try{
            String hola=ServletActionContext.getServletContext().getRealPath("/json");
            System.out.println("***************************************************************");
            System.out.println(hola);
           
            FileWriter file = new FileWriter(ServletActionContext.getServletContext().getRealPath("/json/resultadoConsultaAlumnoSinGrupo.json"));
            file.write(raiz.toJSONString());
            file.flush();
            file.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return SUCCESS;
    }
}
