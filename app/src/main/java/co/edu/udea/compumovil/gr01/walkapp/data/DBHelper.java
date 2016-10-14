package co.edu.udea.compumovil.gr01.walkapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.ArrayList;
/**
 * Created by Pick on 12/10/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "walkapp.db";
    public static final String CREATE_USER = "CREATE TABLE IF NOT EXISTS User "+
            "(username TEXT PRIMARY KEY, email TEXT, pass TEXT, edad TEXT, photo TEXT) ";
    public static final String CREATE_ROUTE = "CREATE TABLE IF NOT EXISTS Route "+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, name TEXT not null, photo TEXT, description TEXT," +
            " dificultty TEXT, weather TEXT, howarrive TEXT, " +
            "FOREIGN KEY (username) REFERENCES User(username))";
    public static final String CREATE_POINT = "CREATE TABLE IF NOT EXISTS Point "+
            "(idroute INTEGER, position INTEGER, longitud REAL, latitud REAL, " +
            "FOREIGN KEY (idroute) REFERENCES Route(id))";
    public static final String CREATE_RATING = "CREATE TABLE IF NOT EXISTS Rating "+
            "(id INTEGER, idroute INTEGER, username TEXT, stars INTEGER, comentario TEXT, seguridad INTEGER," +
            " FOREIGN KEY (idroute) REFERENCES Route(id)," +
            " FOREIGN KEY (username) REFERENCES User(username))";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON ");
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_ROUTE);
        db.execSQL(CREATE_POINT);
        db.execSQL(CREATE_RATING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Valida si el usuario ya esta registrado y si no, lo registra
    public boolean registerValidate(String user, String passwd, String email, String age, String photo){
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM User WHERE username='"+user+"'";
        Cursor c = db.rawQuery(x, null);
        if (c.getCount()!=0){
            return false;
        }
        db.execSQL("INSERT INTO User (username, email, pass, edad, photo) values('"+user+"','"+email+"','"+passwd+"','"+age+"','"+photo+"')");
        c.close();
        return true;
    }

    //Valida el login
    public boolean loginValidate(String user, String passwd){
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM User WHERE username = '"+user+"'";
        Cursor c = db.rawQuery(x, null);
        if (c.moveToFirst()){
            String pass = c.getString(2);
            c.close();
            return pass.equals(passwd);
        }
        else{
            c.close();
            return false;
        }
    }

    public User getUser(String user){
        User useraux= null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor ss = db.rawQuery("SELECT * FROM User where username='"+user+"'", null);
        if(ss.moveToFirst()){
            useraux = new User(ss.getString(0),ss.getString(1), ss.getString(2),ss.getString(3),ss.getString(4));
        }
        return useraux;
    }

    // Añade una ruta a la tabla Route
    public boolean routeAdd(String username, String name, String photo, int description,
                            String dificultty, String weather, String howarrive){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues valores = new ContentValues();
            valores.put("username", username);
            valores.put("name", name);
            valores.put("photo", photo);
            valores.put("description", description);
            valores.put("dificultty", dificultty);
            valores.put("weather", weather);
            valores.put("howarrive", howarrive);
            db.insert("Route", null, valores);
            db.close();
            return true;
        }
        return false;
    }

    //Devuelve la lista de rutas creadas por un usuario especifico
    public ArrayList getMyRoutes(String username){
        ArrayList <Route> routes = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM Route WHERE username='"+username+"'";
        Cursor ss = db.rawQuery(x, null);
        Route aux = null;
        if(ss.moveToFirst()){
            do{
                aux = new Route(ss.getInt(0),ss.getString(1),ss.getString(2),ss.getString(3),ss.getString(4),
                        ss.getInt(5),ss.getString(6),ss.getString(7));
                routes.add(aux);
            }while(ss.moveToNext());
        }
        return routes;
    }

    //Devuelve la lista de rutas creadas por un usuario especifico
    public ArrayList searchRoutes(String word){
        ArrayList <Route> routes = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM Route WHERE name LIKE'%"+word+"%' " +
                "OR description LIKE'%"+word+"%'";
        Cursor ss = db.rawQuery(x, null);
        Route aux = null;
        if(ss.moveToFirst()){
            do{
                aux = new Route(ss.getInt(0),ss.getString(1),ss.getString(2),ss.getString(3),ss.getString(4),
                        ss.getInt(5),ss.getString(6),ss.getString(7));
                routes.add(aux);
            }while(ss.moveToNext());
        }
        return routes;
    }


    // Agrega un punto de una ruta
    public boolean addPoint(int idroute, int position, double longitud, double latitud){
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM Route WHERE id='"+idroute+"'";
        Cursor c = db.rawQuery(x, null);
        if (c.getCount()==0){
            return false;
        }
        db.execSQL("INSERT INTO Point (idroute, position, longitud, latitud) values('"+idroute+"','"+position+"','"+longitud+"','"+latitud+"')");
        c.close();
        return true;
    }

    //Devuelve la lista de los puntos de una ruta
    public ArrayList getPoints(String idroute){
        ArrayList <Point> points = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM Point WHERE idroute='"+idroute+"'";
        Cursor ss = db.rawQuery(x, null);
        Point aux = null;
        if(ss.moveToFirst()){
            do{
                aux = new Point(ss.getInt(0),ss.getInt(1), ss.getDouble(2), ss.getDouble(3));
                points.add(aux);
            }while(ss.moveToNext());
        }
        return points;
    }

    //Agrega una calificación a una ruta
    public boolean addRating(int idroute, String username, int stars, String comentario, int seguridad){
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM Route WHERE id='"+idroute+"'";
        Cursor c = db.rawQuery(x, null);
        if (c.getCount()==0){
            return false;
        }
        db.execSQL("INSERT INTO Rating (idroute, username, stars, comentario, seguridad)" +
                " values('"+idroute+"','"+username+"','"+stars+"','"+comentario+"','"+seguridad+"')");
        c.close();
        return true;
    }

    //Obtiene las calificaciones de una ruta especifica
    public ArrayList getRatings(String idroute){
        ArrayList <Rating> ratings = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String x = "SELECT * FROM Rating WHERE idroute='"+idroute+"'";
        Cursor ss = db.rawQuery(x, null);
        Rating aux = null;
        if(ss.moveToFirst()){
            do{
                aux = new Rating(ss.getInt(0),ss.getInt(1), ss.getString(2),ss.getInt(3),ss.getString(4),ss.getInt(5));
                ratings.add(aux);
            }while(ss.moveToNext());
        }
        return ratings;
    }

}
