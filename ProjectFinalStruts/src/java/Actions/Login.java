package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import entitys.Usuario;
import entitys.Tipousuario;
import java.util.Map;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Login extends ActionSupport implements SessionAware{
    private final String PROFESOR="profesor";
    private final String ALUMNO="alumno";
    private final String ADMINISTRADOR="administrador";
    String usuario, contra;
    int id;
    private Usuario dato;
    private Tipousuario tipo;

    //Para añadir a la sesión
    private SessionMap<String, Object> sessionMap;

    public SessionMap<String, Object> getSessionMap() {
        return sessionMap;
    }
    
    @Override
    public void setSession(Map<String, Object> map) {
        sessionMap = (SessionMap<String, Object>) map;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public Login() {
    }

    @Override
    public String execute() {
        if(this.checkUser()){
            switch(tipo.getIdTipoUsuario()){
                case 1:
                    return ADMINISTRADOR;
                case 2:
                    return PROFESOR;
                case 3:
                    return ALUMNO;
                default:
                    return NONE;
            }
        }
        return NONE;
    }

    private boolean checkUser() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();    //recupero la session factory de hibernate
        Session session = sessionFactory.openSession();            //creo la session y la abro de hibernate
        session.beginTransaction();                              //comienzo la transaccion de hibernate
        Query query = session.createQuery("from Usuario where NombreUsuario=:username and contrasena=:password");  //query en hql
        query.setString("username", usuario);  //meto los parametros
        query.setString("password", contra);
        System.out.println("Se obtuvieron "+query.list().size());
        List list = query.list();    //creo la lista de los objetos obtenidos
        if (list.size() == 1) { //si solo es uno sucess
            dato = (Usuario) query.uniqueResult();  //obtengo ese usuario que obtuve
            id = dato.getIdUsuario();
            tipo = dato.getTipousuario();           //otengo el tipo de usuario            
            sessionMap.put("idUsuario", id);        //Para agregar el usuario a la sesión
            return true;

        } else {
            return false;
        }
    }

}
