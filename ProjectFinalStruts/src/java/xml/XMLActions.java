package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONObject;
import org.json.XML;

public class XMLActions {

    public XMLActions() {

    }
    
    public List cargarXmlExamen(){
        SAXBuilder builder = new SAXBuilder();
        String path = ServletActionContext.getServletContext().getRealPath("/");
        System.out.println(path);
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath("xml/examenes.xml"));
        List list = null;
        try {
            //Creo un documento atraves del archivo
            Document document = (Document) builder.build(xmlFile);
            //obtengo la raiz
            Element rootNode = document.getRootElement();
            //se obtiene la lista de los hijos
            list = rootNode.getChildren("examen");
            return list;
        } catch (IOException io) {
            System.out.println(io.getMessage());
            System.out.println("Error con el xml de examen");
        } catch (JDOMException jdomex) {
            System.out.println();
            System.out.println(jdomex.getMessage());
        } finally {
            return list;
        }
    }
    
    public ArrayList<Examen> convierteList2ArrayListExamen(List nodos){
        ArrayList<Examen> examenes=new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            Element examen_elemento=(Element) nodos.get(i);
            List ejercicios=examen_elemento.getChildren("ejercicio");
            ArrayList<Ejercicio> arrayEjercicios=this.convierte2ArrayList(ejercicios);
            Examen examen_objeto=new Examen();
            examen_objeto.setEjercicios(arrayEjercicios);
            examen_objeto.setFecha(examen_elemento.getAttributeValue("fecha"));
            examen_objeto.setNombre(examen_elemento.getAttributeValue("nombre"));
            //aqui va el codigo para agregar las preguntas
            examenes.add(examen_objeto);
        }
        System.out.println("Se regresan los examenes en array list con: "+examenes.size()+" elementos");
        return examenes;
    }
    
    //guardar XML Examen
    
    public boolean guardarXMLExamen(ArrayList<Examen> examenes){
        Element root=new Element("examenes");
        for(int i=0;i<examenes.size();i++){
            Examen examen_datos= examenes.get(i);
            Element examen_element=new Element("examen");
            examen_element.setAttribute("fecha", examen_datos.getFecha());
            examen_element.setAttribute("nombre", examen_datos.getNombre());
            ArrayList<Ejercicio> ejercicios=examen_datos.getEjercicios();
            Element ejercicio_element;
            for (int x = 0; x < ejercicios.size(); x++) {
                Ejercicio ejercicio=ejercicios.get(x);
                ejercicio_element=new Element("ejercicio");
                ejercicio_element.setAttribute("nombre", ejercicio.getNombre());
                ejercicio_element.setAttribute("tipo", ejercicio.getTipo());
                ejercicio_element.setAttribute("numero", ejercicio.getNumero());
                Element indicacion=new Element("indicaciones");
                indicacion.setText(ejercicio.getPregunta());
                Element opcion1=new Element("opcion");
                opcion1.setText(ejercicio.getOpcion1());
                Element opcion2=new Element("opcion");
                opcion2.setText(ejercicio.getOpcion2());
                Element opcion3=new Element("opcion");
                opcion3.setText(ejercicio.getOpcion3());
                Element opcion4=new Element("opcion");
                opcion4.setText(ejercicio.getOpcion4());
                Element res=new Element("resultado");
                res.setText(ejercicio.getResultado());
                ejercicio_element.addContent(indicacion);
                ejercicio_element.addContent(opcion1);
                ejercicio_element.addContent(opcion2);
                ejercicio_element.addContent(opcion3);
                ejercicio_element.addContent(opcion4);
                ejercicio_element.addContent(res);
                examen_element.addContent(ejercicio_element);
            }
            //Aqui va lo de las preguntas
            root.addContent(examen_element);
        }
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath("xml/examenes.xml")));
            System.out.println("Archivo xml, guardado");
            return true;
            //C:\Users\German Pons\Documents\NetBeansProjects\ProyectFinal\build\web\xml
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        
    }
    

    public String convierteStringToJson(String texto) {
        JSONObject xmlJSONObj = XML.toJSONObject(texto);
        String jsonPrettyPrintString = xmlJSONObj.toString();
        System.out.println("Obteniendo datos de Json" + jsonPrettyPrintString);
        JSONObject obj = new JSONObject();
        try {
            FileWriter file = new FileWriter("prueba.json");
            file.write(obj.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            //manejar error
            e.printStackTrace();
        }

        System.out.print(obj);
        return jsonPrettyPrintString;
    }

    public String XmlToString() {
        String cadena = null;
        String path = ServletActionContext.getServletContext().getRealPath("xml/ejercicios.xml");
        System.out.println("***************************************************************+");
        System.out.println(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            //System.out.println("String builder says: "+sb);
            cadena = sb.toString();
            //System.out.println("La cadena dice: "+cadena);
            return cadena;
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Error al cambiar de xml a string");
            return cadena;
        }
    }
    
    public ArrayList<Ejercicio> convierte2ArrayList(List nodos){
        ArrayList<Ejercicio> datos=new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            Element ejercicios= (Element) nodos.get(i);
            Ejercicio ejercicio=new Ejercicio();
            String dato=ejercicios.getAttributeValue("nombre");
            ejercicio.setNombre(dato);
            dato=ejercicios.getAttributeValue("tipo");
            ejercicio.setTipo(dato);
            dato=ejercicios.getAttributeValue("numero");
            ejercicio.setNumero(dato);
            String pregunta=ejercicios.getChildText("indicaciones");
            ejercicio.setPregunta(pregunta);
            String resultado=ejercicios.getChildText("resultado");
            ejercicio.setResultado(resultado);
            List opciones=ejercicios.getChildren("opcion");
            Element opcion1=(Element) opciones.get(0);
            Element opcion2=(Element) opciones.get(1);
            Element opcion3=(Element) opciones.get(2);
            Element opcion4=(Element) opciones.get(3);
            ejercicio.setOpcion1(opcion1.getText());
            ejercicio.setOpcion2(opcion2.getText());
            ejercicio.setOpcion3(opcion3.getText());
            ejercicio.setOpcion4(opcion4.getText());
            
            datos.add(ejercicio);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public List cargarXml() {
        SAXBuilder builder = new SAXBuilder();
        String path = ServletActionContext.getServletContext().getRealPath("/");
        System.out.println(path);
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath("xml/ejercicios.xml"));
        List list = null;
        try {
            //Creo un documento atraves del archivo
            Document document = (Document) builder.build(xmlFile);
            //obtengo la raiz
            Element rootNode = document.getRootElement();
            //se obtiene la lista de los hijos
            list = rootNode.getChildren("ejercicio");
            return list;
        } catch (IOException io) {
            System.out.println(io.getMessage());
            System.out.println("Error con el xml");
        } catch (JDOMException jdomex) {
            System.out.println();
            System.out.println(jdomex.getMessage());
        } finally {
            return list;
        }
    }

    public boolean guardarXml(ArrayList<Ejercicio> lista) {
        Element root = new Element("ejercicios");
        Ejercicio ej;
        for (int i = 0; i < lista.size(); i++) {
            ej = lista.get(i);
            Element ejercicio = new Element("ejercicio");
            ejercicio.setAttribute("numero", ej.getNumero());
            ejercicio.setAttribute("tipo", ej.getTipo());
            ejercicio.setAttribute("nombre", ej.getNombre());
            Element pregunta = new Element("indicaciones");
            pregunta.setText(ej.getPregunta());
            Element opcion1=new Element("opcion");
            opcion1.setText(ej.getOpcion1());
            Element opcion2=new Element("opcion");
            opcion2.setText(ej.getOpcion2());
            Element opcion3=new Element("opcion");
            opcion3.setText(ej.getOpcion3());
            Element opcion4=new Element("opcion");
            opcion4.setText(ej.getOpcion4());
            Element resultado = new Element("resultado");
            resultado.setText(ej.getResultado());
            ejercicio.addContent(pregunta);
            ejercicio.addContent(opcion1);
            ejercicio.addContent(opcion2);
            ejercicio.addContent(opcion3);
            ejercicio.addContent(opcion4);
            ejercicio.addContent(resultado);
            root.addContent(ejercicio);
        }
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath("xml/ejercicios.xml")));
            System.out.println("Archivo xml, guardado");
            return true;
            //C:\Users\German Pons\Documents\NetBeansProjects\ProyectFinal\build\web\xml
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    
    public boolean crearXMLExamen(String Nombre){
        String nombreRuta = "xml/Profesor" + Nombre + "/examenes.xml";
        Element root = new Element("examenes");

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(nombreRuta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
    
    public boolean crearXMLAsignado(String Nombre){
        String nombreRuta = "xml/Grupo" + Nombre + "/asignados.xml";
        Element root = new Element("asignados");

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(nombreRuta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
    
    public boolean crearXMLEjercicio(String Nombre){
        String nombreRuta = "xml/Profesor" + Nombre + "/ejercicios.xml";
        Element root = new Element("ejercicios");

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(nombreRuta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
    
    public boolean crearXMLPregunta(String Nombre){
        String nombreRuta = "xml/Profesor" + Nombre + "/preguntas.xml";
        Element root = new Element("preguntas");

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(nombreRuta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
    
    public boolean crearXMLRespuestas(String Nombre){
        String nombreRuta = "xml/Alumno" + Nombre + "/respuestas.xml";
        Element root = new Element("respuestas");

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(nombreRuta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
   
    public ArrayList<Ejercicio> eliminaEjercicio(ArrayList<Ejercicio> ejercicios,int numero){
        ejercicios.remove(numero-1);
        return ejercicios;
    }

    public ArrayList<Ejercicio> modificaEjercicio(ArrayList<Ejercicio> ejercicios, String numero, Ejercicio ejercicioModificado) {
        int num=Integer.parseInt(numero);
        ejercicios.remove(num-1);
        ejercicios.add(num-1, ejercicioModificado);
        return ejercicios;
    }

    public ArrayList<Examen> eliminaExamen(ArrayList<Examen> examenes, String numero){        
        int num=Integer.parseInt(numero);
        examenes.remove(num-1);
        return examenes;
    }
    
    public ArrayList<Ejercicio> regresaEjercicios(String[] numeros,ArrayList<Ejercicio> datos){
        ArrayList<Ejercicio> ejercicios=new ArrayList<>();
        for(int i=0;i<numeros.length;i++){
            int numero=Integer.parseInt(numeros[i]);
            Ejercicio ej=datos.get(numero-1);
            ejercicios.add(ej);
        }
        return ejercicios;
    }

}
