package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by saito on 28/08/16.
 */
public class Route {
    private int id;



    private String username;
    private String name;
    private String photourl;
    private String description;
    private int dificultty;
    private String weather;
    private String howarrive;

    public Route(int id, String username, String name, String photourl, String description, int dificultty, String weather, String howarrive) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.photourl = photourl;
        this.description = description;
        this.dificultty = dificultty;
        this.weather = weather;
        this.howarrive = howarrive;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhotourl() {
        return photourl;
    }

    public String getDescription() {
        return description;
    }

    public int getDificultty() {
        return dificultty;
    }

    public String getWeather() {
        return weather;
    }

    public String getHowarrive() {
        return howarrive;
    }
}
