package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 19/10/2016.
 */
public class Favorite {
    private int idroute;
    private String username;

    public Favorite(int idroute, String username) {
        this.idroute = idroute;
        this.username = username;
    }

    public int getIdroute() {
        return idroute;
    }

    public String getUsername() {
        return username;
    }
}
