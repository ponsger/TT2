package xml;

import java.io.Serializable;
import java.util.ArrayList;

public class Examen implements Serializable{
    String nombre,fecha;
    ArrayList<Ejercicio> ejercicios;
    //ArrayList<Pregunta> preguntas;
    public Examen(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
    
}
