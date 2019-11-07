
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import xml.Ejercicio;
import xml.XMLActions;


public class EliminaEjercicio extends ActionSupport {
    
    private int numero;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public EliminaEjercicio() {
    }
    
    @Override
    public String execute() {
        XMLActions xml=new XMLActions();
        List listaEjercicios=xml.cargarXml();
        ArrayList<Ejercicio> ejercicios=xml.convierte2ArrayList(listaEjercicios);
        xml.eliminaEjercicio(ejercicios, getNumero());
        if(xml.guardarXml(ejercicios)==true){
            return SUCCESS;
        }else{
            return ERROR;
        }
        
    }
    
}
