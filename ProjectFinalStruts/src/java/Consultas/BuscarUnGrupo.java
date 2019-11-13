package Consultas;

import entitys.Grupo;
import entitys.HibernateUtil;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import static Complementos.Operaciones.*;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author RodrigoSalazar
 */
public class BuscarUnGrupo {
    private int idGrupo;

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.idGrupo);
        
        //Para crear el json
        JSONObject raiz = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        
        innerObj.put("nombre", grupo.getNombre());
        innerObj.put("ano", grupo.getAno());
        innerObj.put("turno", grupo.getTurno());
        innerObj.put("id", grupo.getIdGrupo());
        
        obj.put(0, innerObj);
        raiz.put("idGrupo", obj);

        try{
            String hola=ServletActionContext.getServletContext().getRealPath("/json");
            System.out.println("***************************************************************");
            System.out.println(hola);
            
            FileWriter file = new FileWriter(ServletActionContext.getServletContext().getRealPath("/json/respuestaConsultaUnGrupo.json"));
            file.write(raiz.toJSONString());
            file.flush();
            file.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return SUCCESS;
    }
}