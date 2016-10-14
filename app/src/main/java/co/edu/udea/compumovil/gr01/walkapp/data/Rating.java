package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 12/10/2016.
 */
public class Rating {
    private int id;
    private int idroute;
    private String username;
    private int stars;
    private String comentario;
    private int seguridad;

    public Rating(int id, int idroute, String username, int stars, String comentario, int seguridad) {
        this.id = id;
        this.idroute = idroute;
        this.username = username;
        this.stars = stars;
        this.comentario = comentario;
        this.seguridad = seguridad;
    }

    public int getIdroute() {
        return idroute;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public String getComentario() {
        return comentario;
    }

    public int getSeguridad() {
        return seguridad;
    }
}
