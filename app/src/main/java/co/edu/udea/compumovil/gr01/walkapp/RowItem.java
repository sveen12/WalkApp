package co.edu.udea.compumovil.gr01.walkapp;

/**
 * Created by Pick on 30/10/2016.
 */

public class RowItem {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String routeName;
    String userName;
    String dificultty;
    String weather;
    int rating;

    public RowItem(String routeName, String userName, String dificultty, String weather, int rating) {
        this.routeName = routeName;
        this.userName = userName;
        this.dificultty = dificultty;
        this.weather = weather;
        this.rating = rating;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDificultty() {
        return dificultty;
    }

    public void setDificultty(String dificultty) {
        this.dificultty = dificultty;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
