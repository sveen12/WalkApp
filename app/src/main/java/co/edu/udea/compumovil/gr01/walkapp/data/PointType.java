package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 25/10/2016.
 */
public class PointType {
    private int idtype;
    private String tipo;

    public PointType(int idtype, String tipo) {
        this.idtype = idtype;
        this.tipo = tipo;
    }

    public int getIdtype() {
        return idtype;
    }

    public String getTipo() {
        return tipo;
    }
}


