
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import xml.Ejercicio;
import xml.XMLActions;

/**
 *
 * @author German Pons
 */
public class ModificaEjercicio extends ActionSupport {
    
    String numero,nombre,instrucciones,opcion1,opcion2,opcion3,opcion4,resultado;   
    
    public ModificaEjercicio() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    @Override
    public String execute(){
        Ejercicio ejercicioModificado=new Ejercicio();
        XMLActions xml=new XMLActions();
        List lista=xml.cargarXml();
        ArrayList<Ejercicio> ejercicios=xml.convierte2ArrayList(lista);
        ejercicioModificado.setNombre(nombre);
        ejercicioModificado.setNumero("100");
        ejercicioModificado.setPregunta(instrucciones);
        ejercicioModificado.setResultado(resultado);
        ejercicioModificado.setOpcion1(opcion1);
        ejercicioModificado.setOpcion2(opcion2);
        ejercicioModificado.setOpcion3(opcion3);
        ejercicioModificado.setOpcion4(opcion4);
        ejercicioModificado.setTipo("default");
        ejercicios=xml.modificaEjercicio(ejercicios,numero,ejercicioModificado);
        if(xml.guardarXml(ejercicios)==true){
            return SUCCESS;
        }else{
            return ERROR;
        }
    }
    
}
