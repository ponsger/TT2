package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import xml.Ejercicio;
import xml.XMLActions;

public class AgregaEjercicio extends ActionSupport {
    private String inst,op1,op2,op3,op4,res;

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
    
    public AgregaEjercicio() {
    }
    
    @Override
    public String execute(){ 
        System.out.println("***************************************Invocado y todo correcto :)"); // jajajaja te mamas
        Ejercicio nuevo=new Ejercicio();
        XMLActions xml=new XMLActions();
        nuevo.setOpcion1(op1);
        nuevo.setOpcion2(op2);
        nuevo.setOpcion3(op3);
        nuevo.setOpcion4(op4);
        nuevo.setPregunta(this.inst);
        nuevo.setResultado(res);
        nuevo.setNumero("100");
        nuevo.setNombre("Prueba");
        nuevo.setTipo("default");
        List listas=xml.cargarXml();
        System.out.println("Hay "+listas.size()+"elementos en la lista");
        ArrayList<Ejercicio> datos=xml.convierte2ArrayList(listas);
        System.out.println("Hay "+datos.size()+"elementos en el array list");
        datos.add(nuevo);
        
        System.out.println("se agrego un elemento al arraylist y ahora hay "+datos.size());
        if(xml.guardarXml(datos)==true){
            System.out.println("*****************************************");
            System.out.println("Archivo xml guardado ultimo");
            return SUCCESS;
        }else{
            System.out.println("*****************************************");
            System.out.println("Error al guardar xml");
            return ERROR;
        }
        
        
    }
    
}
