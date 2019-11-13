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
    
    public boolean guardarXMLExamenAgregado(ArrayList<Examen> examenes){
        Element root=new Element("examenes");
        
        for(int i = 0;i < examenes.size();i++){
            Examen examen_datos= examenes.get(i);
            
            Element examen_element=new Element("examen");
            
            examen_element.setAttribute("fecha", examen_datos.getFecha());
            examen_element.setAttribute("nombre", examen_datos.getNombre());
            
            ArrayList<Ejercicio> ejercicios = examen_datos.getEjercicios();
            ArrayList<Pregunta> preguntas = examen_datos.getPreguntas();
            
            Element ejercicio_element;
            Element pregunta_element;
            
            for (int x = 0; x < ejercicios.size(); x++) {
                Ejercicio ejercicio = ejercicios.get(x);
                ejercicio_element=new Element("ejercicio");
                ejercicio_element.setAttribute("numero", ejercicio.getNumero());
                examen_element.addContent(ejercicio_element);
            }
            
            for(int j = 0; j < preguntas.size(); j++){
                Pregunta pregunta = preguntas.get(j);
                pregunta_element = new Element("pregunta");
                pregunta_element.setAttribute("numero", pregunta.getNumero());
                examen_element.addContent(pregunta_element);
                
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
            ejercicio.setCopiaEjercicio(Integer.parseInt(ejercicios.getAttributeValue("copiaEjercicio")));
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
    
    public ArrayList<Pregunta> convierte2ArrayListPreguntas(List nodos){
        ArrayList<Pregunta> datos=new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            //String nombre,tipo,indicaciones,opcion1,opcion2,respuesta; int id;
            Element preguntas= (Element) nodos.get(i);
            
            Pregunta pregunta = new Pregunta();
            
            //obtenemos los atributos
            pregunta.setNombre(preguntas.getAttributeValue("nombre"));
            pregunta.setNumero(preguntas.getAttributeValue("numero"));
            pregunta.setTipo(preguntas.getAttributeValue("tipo"));
            pregunta.setCopiaPregunta(Integer.parseInt(preguntas.getAttributeValue("copiaPregunta")));
            
            //obtenemos los elementos
            pregunta.setIndicaciones(preguntas.getChildText("cuestionamiento"));
            pregunta.setOpcion1(preguntas.getChildText("opcion1"));
            pregunta.setOpcion2(preguntas.getChildText("opcion2"));
            pregunta.setRespuesta(preguntas.getChildText("respuesta"));
            
            datos.add(pregunta);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public ArrayList<Ejercicio> convierte2ArrayListEjercicios(List nodos){
        ArrayList<Ejercicio> datos=new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            //private String numero, nombre, tipo - pregunta, resultado, opcion1, opcion2, opcion3, opcion4;
            Element ejercicios = (Element) nodos.get(i);
            
            Ejercicio ejercicio = new Ejercicio();
            
            //obtenemos los atributos
            ejercicio.setNumero(ejercicios.getAttributeValue("numero"));
            ejercicio.setNombre(ejercicios.getAttributeValue("numero"));
            ejercicio.setTipo(ejercicios.getAttributeValue("tipo"));
            
            //Obtenemos los elementos
            ejercicio.setPregunta(ejercicios.getChildText("indicaciones"));
            ejercicio.setOpcion1(ejercicios.getChildText("opcion1"));
            ejercicio.setOpcion2(ejercicios.getChildText("opcion2"));
            ejercicio.setOpcion3(ejercicios.getChildText("opcion3"));
            ejercicio.setOpcion4(ejercicios.getChildText("opcion4"));
            ejercicio.setResultado(ejercicios.getChildText("resultado"));

            datos.add(ejercicio);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public ArrayList<RespuestaPregunta> convierte2ArrayListRespuestasPreguntas(List nodos){
        ArrayList<RespuestaPregunta> datos = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            //String nombre,tipo,indicaciones,opcion1,opcion2,respuesta; int id;
            Element respuestas = (Element) nodos.get(i);
            
            RespuestaPregunta respuesta = new RespuestaPregunta();
            
            //Obtenemos los atributos
            respuesta.setNumeroPregunta(Integer.parseInt(respuestas.getAttributeValue("numero")));
            
            //Obtenemos la respuesta
            respuesta.setRespuestaPregunta(respuestas.getText());
            
            datos.add(respuesta);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public ArrayList<RespuestaEjercicio> convierte2ArrayListRespuestasEjercicios(List nodos){
        ArrayList<RespuestaEjercicio> datos = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            //String nombre,tipo,indicaciones,opcion1,opcion2,respuesta; int id;
            Element respuestas = (Element) nodos.get(i);
            
            RespuestaEjercicio respuesta = new RespuestaEjercicio();
            
            //Obtenemos los atributos
            respuesta.setNumeroEjercicio(Integer.parseInt(respuestas.getAttributeValue("numero")));
            
            //Obtenemos la respuesta
            respuesta.setRespuestaEjercicio(respuestas.getText());
            
            datos.add(respuesta);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public ArrayList<ExamenesAsignados> convierte2ArrayListExamenesAsignados(List nodos){
        ArrayList<ExamenesAsignados> datos = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            // numero, grupoCompleto, numeroAlumno
            
            Element examenes = (Element) nodos.get(i);
            
            ExamenesAsignados examen = new ExamenesAsignados();
            
            //Obtenemos los atributos
            examen.setNumeroExamen(Integer.parseInt(examenes.getAttributeValue("numeroExamen")));
            examen.setGrupoCompleto(examenes.getAttributeValue("grupoCompleto"));
            examen.setNumeroAlumno(Integer.parseInt(examenes.getAttributeValue("numeroAlumno")));
            examen.setIdGrupo(Integer.parseInt(examenes.getAttributeValue("grupo")));
            
            datos.add(examen);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public ArrayList<EjerciciosAsignados> convierte2ArrayListEjerciciosAsignados(List nodos){
        ArrayList<EjerciciosAsignados> datos = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            // numero, grupoCompleto, numeroAlumno
            
            Element ejercicios = (Element) nodos.get(i);
            
            EjerciciosAsignados ejercicio = new EjerciciosAsignados();
            
            //Obtenemos los atributos
            ejercicio.setNumeroEjercicio(Integer.parseInt(ejercicios.getAttributeValue("numeroEjercicio")));
            ejercicio.setGrupoCompleto(ejercicios.getAttributeValue("grupoCompleto"));
            ejercicio.setNumeroAlumno(Integer.parseInt(ejercicios.getAttributeValue("numeroAlumno")));
            ejercicio.setIdGrupo(Integer.parseInt(ejercicios.getAttributeValue("grupo")));
            
            datos.add(ejercicio);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    
    public ArrayList<PreguntasAsignadas> convierte2ArrayListPreguntasAsignadas(List nodos){
        ArrayList<PreguntasAsignadas> datos = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            // numero, grupoCompleto, numeroAlumno
            
            Element preguntas = (Element) nodos.get(i);
            
            PreguntasAsignadas pregunta = new PreguntasAsignadas();
            
            //Obtenemos los atributos
            pregunta.setNumeroPregunta(Integer.parseInt(preguntas.getAttributeValue("numeroPregunta")));
            pregunta.setGrupoCompleto(preguntas.getAttributeValue("grupoCompleto"));
            pregunta.setNumeroAlumno(Integer.parseInt(preguntas.getAttributeValue("numeroAlumno")));
            pregunta.setIdGrupo(Integer.parseInt(preguntas.getAttributeValue("grupo")));
            
            datos.add(pregunta);
        }
        System.out.println("El arraylist tiene "+datos.size()+" elementos");
        return datos;
    }
    public List cargarXml(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
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
    
    public List cargarXmlPreguntas(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            //Creo un documento atraves del archivo
            Document document = (Document) builder.build(xmlFile);
            //obtengo la raiz
            Element rootNode = document.getRootElement();
            //se obtiene la lista de los hijos
            list = rootNode.getChildren("pregunta");
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
    
    public List cargarXmlEjercicios(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
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
    
    public List cargarXmlRespuestasEjercicios(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element ejercicios = rootNode.getChild("ejercicios");
            list = ejercicios.getChildren();
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
    
    public List cargarXmlRespuestasPreguntas(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element preguntas = rootNode.getChild("preguntas");
            list = preguntas.getChildren();
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
    
    public List cargarXmlExamenesAsignados(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element preguntas = rootNode.getChild("examenes");
            list = preguntas.getChildren();
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
    
    public List cargarXmlExamenes(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            list = rootNode.getChildren();
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
    
    public List cargarXmlEjerciciosAgregados(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element preguntas = rootNode.getChild("examenes");
            list = preguntas.getChildren("ejercicio");
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
    
    public List cargarXmlPreguntasAgregadas(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element preguntas = rootNode.getChild("examenes");
            list = preguntas.getChildren("pregunta");
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
    
    public ArrayList<Examen> convierteList2ArrayListExamenAgregado(List nodos){
        ArrayList<Examen> examenes = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            Element examen_elemento=(Element) nodos.get(i);
            List ejercicios = examen_elemento.getChildren("ejercicio");
            List preguntas = examen_elemento.getChildren("pregunta");
            ArrayList<Ejercicio> arrayEjercicios = this.convierteList2ArrayListEjercicioAgregado(ejercicios);
            ArrayList<Pregunta> arrayPregunta = this.convierteList2ArrayListPreguntaAgregada(preguntas);
            Examen examen_objeto=new Examen();
            examen_objeto.setEjercicios(arrayEjercicios);
            examen_objeto.setPreguntas(arrayPregunta);
            examen_objeto.setFecha(examen_elemento.getAttributeValue("fecha"));
            examen_objeto.setNombre(examen_elemento.getAttributeValue("nombre"));
            //aqui va el codigo para agregar las preguntas
            examenes.add(examen_objeto);
        }
        System.out.println("Se regresan los examenes en array list con: "+examenes.size()+" elementos");
        return examenes;
    }
    public ArrayList<Ejercicio> convierteList2ArrayListEjercicioAgregado(List nodos){
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            Element ejercicio_elemento = (Element) nodos.get(i);
            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setNumero(ejercicio_elemento.getAttributeValue("numero"));
            ejercicios.add(ejercicio);
        }
        return ejercicios;
    }
    
    public ArrayList<Pregunta> convierteList2ArrayListPreguntaAgregada(List nodos){
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        for(int i=0;i<nodos.size();i++){
            Element pregunta_elemento = (Element)nodos.get(i);
            Pregunta pregunta = new Pregunta();
            pregunta.setNumero(pregunta_elemento.getAttributeValue("numero"));
            preguntas.add(pregunta);
        }
        return preguntas;
    }
    
    public List cargarXmlEjerciciosAsignados(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element preguntas = rootNode.getChild("ejercicios");
            list = preguntas.getChildren();
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
    
    public List cargarXmlPreguntasAsignadas(String ruta) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(ServletActionContext.getServletContext().getRealPath(ruta));
        List list = null;
        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element preguntas = rootNode.getChild("preguntas");
            list = preguntas.getChildren();
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

    public boolean guardarXml(ArrayList<Ejercicio> lista, String ruta) {
        Element root = new Element("ejercicios");
        Ejercicio ej;
        for (int i = 0; i < lista.size(); i++) {
            ej = lista.get(i);
            Element ejercicio = new Element("ejercicio");
            ejercicio.setAttribute("numero", ej.getNumero());
            ejercicio.setAttribute("tipo", ej.getTipo());
            ejercicio.setAttribute("nombre", ej.getNombre());
            ejercicio.setAttribute("copiaEjercicio", Integer.toString(ej.getCopiaEjercicio()));
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
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(ruta)));
            System.out.println("Archivo xml, guardado");
            return true;
            //C:\Users\German Pons\Documents\NetBeansProjects\ProyectFinal\build\web\xml
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    
    public boolean guardarXmlPregunta(ArrayList<Pregunta> lista, String ruta) {
        Element root = new Element("preguntas");
        Pregunta pg;
        
        for (int i = 0; i < lista.size(); i++) {
            pg = lista.get(i);
            Element pregunta = new Element("pregunta");
            
            //establecemos atributos
            pregunta.setAttribute("numero", pg.getNumero());
            pregunta.setAttribute("nombre", pg.getNombre());
            pregunta.setAttribute("tipo", pg.getTipo());
            pregunta.setAttribute("copiaPregunta", Integer.toString(pg.getCopiaPregunta()));
            
            //establecemos elementos
            Element cuestionamiento = new Element("cuestionamiento");
            cuestionamiento.setText(pg.getIndicaciones());
            
            Element opcion1 = new Element("opcion1");
            opcion1.setText(pg.getOpcion1());
            
            Element opcion2 = new Element("opcion2");
            opcion2.setText(pg.getOpcion2());

            Element respuesta = new Element("respuesta");
            respuesta.setText(pg.getRespuesta());
            
            pregunta.addContent(cuestionamiento);
            pregunta.addContent(opcion1);
            pregunta.addContent(opcion2);
            pregunta.addContent(respuesta);
            
            root.addContent(pregunta);
        }
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(ruta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    
    public boolean guardarXmlEjercicio(ArrayList<Ejercicio> lista, String ruta) {
        Element root = new Element("ejercicios");
        Ejercicio ej;
        
        for (int i = 0; i < lista.size(); i++) {
            ej = lista.get(i);
            Element ejercicio = new Element("ejercicio");
            
            //establecemos atributos numero tipo nombre
            ejercicio.setAttribute("numero", ej.getNumero());
            ejercicio.setAttribute("nombre", ej.getNombre());
            ejercicio.setAttribute("tipo", ej.getTipo());
            
            //establecemos elementos indicaciones opcion1-4 resultado
            Element cuestionamiento = new Element("indicaciones");
            cuestionamiento.setText(ej.getPregunta());
            
            Element opcion1 = new Element("opcion1");
            opcion1.setText(ej.getOpcion1());
            
            Element opcion2 = new Element("opcion2");
            opcion2.setText(ej.getOpcion2());
            
            Element opcion3 = new Element("opcion3");
            opcion3.setText(ej.getOpcion3());
            
            Element opcion4 = new Element("opcion4");
            opcion4.setText(ej.getOpcion4());

            Element resultado = new Element("resultado");
            resultado.setText(ej.getResultado());
            
            ejercicio.addContent(cuestionamiento);
            ejercicio.addContent(opcion1);
            ejercicio.addContent(opcion2);
            ejercicio.addContent(opcion3);
            ejercicio.addContent(opcion4);
            ejercicio.addContent(resultado);
            
            root.addContent(ejercicio);
        }
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(ruta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    public boolean guardarXmlRespuestas(ArrayList<RespuestaPregunta> listaPreguntas, ArrayList<RespuestaEjercicio> listaEjercicios,String ruta) {
        Element root = new Element("respuestas");
        Element preguntas = new Element("preguntas");
        Element ejercicios = new Element("ejercicios");
        
        RespuestaPregunta rp;
        RespuestaEjercicio re;
        
        for(int i = 0; i < listaEjercicios.size(); i++){
           re = listaEjercicios.get(i);
           Element respuestaEjercicio = new Element("ejercicio");
           
           respuestaEjercicio.setAttribute("numero", Integer.toString(re.getNumeroEjercicio()));
           respuestaEjercicio.setText(re.getRespuestaEjercicio());
           
           ejercicios.addContent(respuestaEjercicio);
        }
        
        for(int i = 0; i < listaPreguntas.size(); i++){
            rp = listaPreguntas.get(i);
            Element respuestaPregunta = new Element("pregunta");
            
            respuestaPregunta.setAttribute("numero", Integer.toString(rp.getNumeroPregunta()));
            respuestaPregunta.setText(rp.getRespuestaPregunta());
            
            preguntas.addContent(respuestaPregunta);
        }
        
        root.addContent(ejercicios);
        root.addContent(preguntas);
        
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(ruta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    
    public boolean guardarXmlAsignados(ArrayList<ExamenesAsignados> listaExamenes, ArrayList<EjerciciosAsignados> listaEjercicios, ArrayList<PreguntasAsignadas> listaPreguntas, String ruta) {
        Element root = new Element("asignados");
        Element examenes = new Element("examenes");
        Element preguntas = new Element("preguntas");
        Element ejercicios = new Element("ejercicios");
        
        ExamenesAsignados ex;
        EjerciciosAsignados ej;
        PreguntasAsignadas pr;
        
        for(int i = 0; i < listaExamenes.size(); i++){
            ex = listaExamenes.get(i);
            Element examen = new Element("examen");
            
            examen.setAttribute("numeroExamen", Integer.toString(ex.getNumeroExamen()));
            examen.setAttribute("grupoCompleto", ex.getGrupoCompleto());
            examen.setAttribute("numeroAlumno", Integer.toString(ex.getNumeroAlumno()));
            examen.setAttribute("grupo", Integer.toString(ex.getIdGrupo()));
            
            examenes.addContent(examen);
        }
        
        for(int i = 0; i < listaEjercicios.size(); i++){
            ej = listaEjercicios.get(i);
            Element ejercicio = new Element("ejercicio");
            
            ejercicio.setAttribute("numeroEjercicio", Integer.toString(ej.getNumeroEjercicio()));
            ejercicio.setAttribute("grupoCompleto", ej.getGrupoCompleto());
            ejercicio.setAttribute("numeroAlumno", Integer.toString(ej.getNumeroAlumno()));
            ejercicio.setAttribute("grupo", Integer.toString(ej.getIdGrupo()));
            
            ejercicios.addContent(ejercicio);
        }
        
        for(int i = 0; i < listaPreguntas.size(); i++){
            pr = listaPreguntas.get(i);
            Element pregunta = new Element("pregunta");
            
            pregunta.setAttribute("numeroPregunta", Integer.toString(pr.getNumeroPregunta()));
            pregunta.setAttribute("grupoCompleto", pr.getGrupoCompleto());
            pregunta.setAttribute("numeroAlumno", Integer.toString(pr.getNumeroAlumno()));
            pregunta.setAttribute("grupo", Integer.toString(pr.getIdGrupo()));
            
            preguntas.addContent(pregunta);
        }
        
        root.addContent(examenes);
        root.addContent(ejercicios);
        root.addContent(preguntas);
        
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(new Document(root), new FileOutputStream(ServletActionContext.getServletContext().getRealPath(ruta)));
            System.out.println("Archivo xml, guardado");
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    
    public boolean crearXMLExamen(String Nombre){
        String nombreRuta = "xml/Profesor" + Nombre + "/examenes.xml";
        Element root = new Element("examenes");
        
        Element examen = new Element("examen");
        
        examen.setAttribute("fecha", "prueba");
        examen.setAttribute("nombre", "prueba");
        
        Element ejercicio = new Element("ejercicio");
        ejercicio.setAttribute("numero", "0");
        
        Element pregunta = new Element("Pregunta");
        pregunta.setAttribute("numero", "0");
        
        examen.addContent(ejercicio);
        examen.addContent(pregunta);
        
        root.addContent(examen);
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
        
        Element examenes = new Element("examenes");
        Element ejercicios = new Element("ejercicios");
        Element preguntas = new Element("preguntas");
        
        //<examen numero="3"  grupoCompleto="si" alumno="0" grupo=""/>

        Element examen = new Element("examen");
        examen.setAttribute("numeroExamen", "0");
        examen.setAttribute("grupoCompleto", "no");
        examen.setAttribute("numeroAlumno", "0");
        examen.setAttribute("grupo", "0");
        
        examenes.addContent(examen);
        
        Element ejercicio = new Element("ejercicio");
        ejercicio.setAttribute("numeroEjercicio", "0");
        ejercicio.setAttribute("grupoCompleto", "no");
        ejercicio.setAttribute("numeroAlumno", "0");
        ejercicio.setAttribute("grupo", "0");
        
        ejercicios.addContent(ejercicio);
        
        Element pregunta = new Element("pregunta");
        pregunta.setAttribute("numeroPregunta", "0");
        pregunta.setAttribute("grupoCompleto", "no");
        pregunta.setAttribute("numeroAlumno", "0");
        pregunta.setAttribute("grupo", "0");
        
        preguntas.addContent(pregunta);

        root.addContent(examenes);
        root.addContent(ejercicios);
        root.addContent(preguntas);
        
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
        //private String nombre,tipo,pregunta,resultado,numero,opcion1,opcion2,opcion3,opcion4;
        String nombreRuta = "xml/Profesor" + Nombre + "/ejercicios.xml";
        Element root = new Element("ejercicios");
        
        Element ejercicio = new Element("ejercicio");
        
        ejercicio.setAttribute("numero", "prueba");
        ejercicio.setAttribute("nombre", "prueba");
        ejercicio.setAttribute("tipo", "prueba");
        ejercicio.setAttribute("numeroEjercicio","0");
        
        Element indicaciones = new Element("indicaciones");
        indicaciones.setText(" ");
        Element opcion1 = new Element("opcion1");
        opcion1.setText(" ");
        Element opcion2 = new Element("opcion2");
        opcion2.setText(" ");
        Element opcion3 = new Element("opcion3");
        opcion3.setText(" ");
        Element opcion4 = new Element("opcion4");
        opcion4.setText(" ");
        Element resultado = new Element("resultado");
        resultado.setText(" ");
        
        ejercicio.addContent(indicaciones);
        ejercicio.addContent(opcion1);
        ejercicio.addContent(opcion2);
        ejercicio.addContent(opcion3);
        ejercicio.addContent(opcion4);
        ejercicio.addContent(resultado);
        
        root.addContent(ejercicio);
        

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
        
        Element pregunta = new Element("pregunta");
        pregunta.setAttribute("numero", "prueba");
        pregunta.setAttribute("nombre", "prueba");
        pregunta.setAttribute("tipo", "prueba");
        pregunta.setAttribute("copiaPregunta", "0");
        
        Element cuestionamiento = new Element("cuestionamiento");
        cuestionamiento.setText(" ");
        Element opcion1 = new Element("opcion1");
        opcion1.setText(" ");
        Element opcion2 = new Element("opcion2");
        opcion1.setText(" ");
        Element respuesta = new Element("respuesta");
        respuesta.setText(" ");
        
        pregunta.addContent(cuestionamiento);
        pregunta.addContent(opcion1);
        pregunta.addContent(opcion2);
        pregunta.addContent(respuesta);
        
        root.addContent(pregunta);

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
        Element ejercicios = new Element("ejercicios");
        Element preguntas = new Element("preguntas");
        
        Element ejercicio = new Element("ejercicio");
        ejercicio.setAttribute("numero", "0");
        ejercicio.setText(" ");
        
        ejercicios.addContent(ejercicio);
        
        Element pregunta = new Element("pregunta");
        pregunta.setAttribute("numero", "0");
        pregunta.setText(" ");
        
        preguntas.addContent(pregunta);
        
        root.addContent(ejercicios);
        root.addContent(preguntas);

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
        ejercicios.remove(numero - 1);
        return ejercicios;
    }
    
    public ArrayList<Pregunta> eliminaPreguntas(ArrayList<Pregunta> preguntas,int numero){
        preguntas.remove(numero - 1);
        return preguntas;
    }

    public ArrayList<Ejercicio> modificaEjercicio(ArrayList<Ejercicio> ejercicios, String numero, Ejercicio ejercicioModificado) {
        int num=Integer.parseInt(numero);
        ejercicios.remove(num-1);
        ejercicios.add(num-1, ejercicioModificado);
        return ejercicios;
    }
    
    public ArrayList<Pregunta> modificaPregunta(ArrayList<Pregunta> preguntas, String numero, Pregunta preguntaModificada) {
        int num=Integer.parseInt(numero);
        preguntas.remove(num-1);
        preguntas.add(num-1, preguntaModificada);
        return preguntas;
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
    
    public ArrayList<Ejercicio> regresaEjerciciosA(String[] numeros){
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();
        for(int i = 0;i < numeros.length;i++){
            Ejercicio eje = new Ejercicio();
            eje.setNumero(numeros[i]);
            ejercicios.add(eje);
        }
        return ejercicios;
    }
    
    public ArrayList<Pregunta> regresaPreguntasA(String[] numeros){
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        for(int i = 0;i < numeros.length;i++){
            Pregunta pre = new Pregunta();
            pre.setNumero(numeros[i]);
            preguntas.add(pre);
        }
        return preguntas;
    }

}
