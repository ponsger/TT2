package xml;

/**
 *
 * @author RodrigoSalazar
 */
public class ExamenesAsignados {
    int numeroExamen;
    String grupoCompleto;
    int numeroAlumno;
    int idGrupo;

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public ExamenesAsignados() {
    }

    public int getNumeroExamen() {
        return numeroExamen;
    }

    public void setNumeroExamen(int numeroExamen) {
        this.numeroExamen = numeroExamen;
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