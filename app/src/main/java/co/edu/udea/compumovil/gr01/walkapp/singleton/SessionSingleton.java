package co.edu.udea.compumovil.gr01.walkapp.singleton;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;

/**
 * Created by Pick on 05/09/2016.
 */
public class SessionSingleton {
    private static SessionSingleton instancia = null;
    private String username;
    private String email;
    private String age;
    private RoundedBitmapDrawable photo;
    private boolean logueado;


    private SessionSingleton() {

    }

    public static SessionSingleton getInstance() {
        if (instancia == null) {
            instancia = new SessionSingleton();
        }
        return instancia;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    public boolean getLogueado() {
        return logueado;
    }

    public RoundedBitmapDrawable getPhoto() {
        return photo;
    }

    public void setPhoto(RoundedBitmapDrawable photo) {
        this.photo = photo;
    }
}