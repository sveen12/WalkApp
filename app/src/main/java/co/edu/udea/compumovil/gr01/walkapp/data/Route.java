package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by saito on 28/08/16.
 */
public class Route {
    private int idroute;
    private String username;
    private String nombre;
    private String photourl;
    private String description;
    private int difficulty;
    private String weather;
    private String howarrive;

    public int getIdroute() {
        return idroute;
    }

    public void setIdroute(int idroute) {
        this.idroute = idroute;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHowarrive() {
        return howarrive;
    }

    public void setHowarrive(String howarrive) {
        this.howarrive = howarrive;
    }
}
