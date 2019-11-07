package Actions;

import org.apache.struts2.dispatcher.SessionMap;
import static Complementos.Operaciones.LOGOUT;
/**
 *
 * @author RodrigoSalazar
 */
public class Logout {
    private SessionMap<String, Object> sessionMap;
    
    public String execute(){
        Login lg = new Login();
        
        sessionMap = lg.getSessionMap();
        sessionMap.remove("idUsuario");
        sessionMap.invalidate();
        
        return LOGOUT;
    }
}
