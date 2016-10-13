package co.edu.udea.compumovil.gr01.walkapp.data;

/**
 * Created by Pick on 11/10/2016.
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String age;
    private String profilephoto;

    public User(String username, String password, String email, String age, String profilephoto) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.profilephoto = profilephoto;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getProfilephoto() {
        return profilephoto;
    }
}