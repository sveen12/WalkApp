package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 12/10/2016.
 */
public class Point {
    private int idroute;
    private int tipo;
    private int position;
    private double longitud;
    private double latitud;


    public Point(int idroute, int tipo, int position, double longitud, double latitud) {
        this.idroute = idroute;
        this.tipo = tipo;
        this.position = position;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public int getIdroute() {
        return idroute;
    }

    public int getTipo() {
        return tipo;
    }

    public int getPosition() {
        return position;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }
}