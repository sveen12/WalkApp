package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 12/10/2016.
 */
public class Rating {
    private int id;
    private int idroute;
    private String username;
    private int stars;
    private String comment;
    private int security;

    public Rating(int id, int idroute, String username, int stars, String comment, int security) {
        this.id = id;
        this.idroute = idroute;
        this.username = username;
        this.stars = stars;
        this.comment = comment;
        this.security = security;
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

    public String getComment() {
        return comment;
    }

    public int getSecurity() {
        return security;
    }
}
