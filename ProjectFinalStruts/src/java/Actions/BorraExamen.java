package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import xml.XMLActions;

public class BorraExamen extends ActionSupport {
    
    String numero;
    
    public BorraExamen() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    @Override
    public String execute(){
        XMLActions xml=new XMLActions();
        List lista=xml.cargarXmlExamen();
        ArrayList examenes=xml.convierteList2ArrayListExamen(lista);
        xml.eliminaExamen(examenes,numero);
        if(xml.guardarXMLExamen(examenes)){
            return SUCCESS;
        }else{
            return ERROR;
        }
        
    }
    
}
