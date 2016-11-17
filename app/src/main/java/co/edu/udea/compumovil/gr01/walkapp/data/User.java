package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 11/10/2016.
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String age;
    private String photourl;

    public String getUsername() {        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}