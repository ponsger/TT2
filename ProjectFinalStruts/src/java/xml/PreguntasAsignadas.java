package xml;

/**
 *
 * @author RodrigoSalazar
 */
public class PreguntasAsignadas {
    int numeroPregunta;
    String grupoCompleto;
    int numeroAlumno;
    int idGrupo;

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public PreguntasAsignadas() {
    }

    public int getNumeroPregunta() {
        return numeroPregunta;
    }

    public void setNumeroPregunta(int numeroPregunta) {
        this.numeroPregunta = numeroPregunta;
    }

    public String getGrupoCompleto() {
        return grupoCompleto;
    }

    public void setGrupoCompleto(String grupoCompleto) {
        this.grupoCompleto = grupoCompleto;
    }

    public int getNumeroAlumno() {
        return numeroAlumno;
    }

    public void setNumeroAlumno(int numeroAlumno) {
        this.numeroAlumno = numeroAlumno;
    }
}
