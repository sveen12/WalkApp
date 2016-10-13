package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 12/10/2016.
 */
public class Point {
    private int idroute;
    private int order;
    private double longitud;
    private double latitud;

    public Point(int idroute, int order, double longitud, double latitud) {
        this.idroute = idroute;
        this.order = order;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public int getIdroute() {
        return idroute;
    }

    public int getOrder() {
        return order;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }
}
