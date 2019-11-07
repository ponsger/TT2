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
        
        JSONObject obj = new JSONObject();
        
        while(results.hasNext()){
            Alumno alumno = (Alumno)results.next();
            int idGrupo = alumno.getGrupo().getIdGrupo();
            
            if(idGrupo == 0){
                Usuario usuario = (Usuario)hibernateSession.load(Usuario.class, alumno.getIdUsuario());
                JSONObject innerObj = new JSONObject();
                innerObj.put("nombre", usuario.getNombres());
                obj.put(alumno.getIdUsuario(), innerObj);
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
