package Actions.Administrador;

import java.io.Serializable;
import static Complementos.Operaciones.*;
import entitys.Grupo;
import entitys.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author RodrigoSalazar
 */
public class ModificarGrupo implements Serializable{
    private int idGrupo;
    private String nombre;
    private int ano;
    private String turno;

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    public String execute(){
        Session hibernateSession;
        hibernateSession = HibernateUtil.getSessionFactory().openSession(); 
        Transaction t = hibernateSession.beginTransaction();
        
        Grupo grupo = (Grupo)hibernateSession.load(Grupo.class, this.idGrupo);
        
        grupo.setNombre(nombre);
        grupo.setAno(ano);
        grupo.setTurno(turno);

        hibernateSession.update(grupo);
        t.commit();
        return SUCCESS;
    }
}
