package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.User;
import co.edu.udea.compumovil.gr01.walkapp.singleton.SessionSingleton;
import co.edu.udea.compumovil.gr01.walkapp.singleton.VolleySingleton;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user;
    private EditText et_password;
    private LocalBroadcastManager broadcaster;
    public static String SERVERIP = "192.168.1.2";
    static final public String DATA_RESULT = "co.edu.udea.compumovil.gr10.walkapp.LoginActivity.REQUEST_PROCESSED";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = VolleySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        //Campos de texto para logueo normal
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        broadcaster = LocalBroadcastManager.getInstance(this);

    }

    //En caso de loguearse correctamente se abre la actividad principal
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        getUser(et_user.getText().toString());
        startActivity(intent);
    }

    public void verificarCampos(){
        if(et_user.getText().toString().equals("")){
            if(et_password.getText().toString().equals("")){
                et_user.requestFocus();
                et_user.setError(getString(R.string.errorUser));
                et_password.setError(getString(R.string.errorPassword));
                errorMessage();
                return;
            }else{
                et_user.requestFocus();
                et_user.setError(getString(R.string.errorUser));
                errorMessage();
                return;
            }
        }else if(et_password.getText().toString().equals("")){
            et_password.requestFocus();
            et_password.setError(getString(R.string.errorPassword));
            errorMessage();
            return;
        }

        //Logueo exitoso

    }

    //Login con usuario y cuenta normales
    public void normalLogin(View view) {
        verificarCampos();

        loginValidate(et_user.getText().toString(), et_password.getText().toString());
    }

    //Se muestra cuando hay algun campo vacio.
    public void errorMessage(){
        Toast.makeText(this, R.string.emptyFields, Toast.LENGTH_LONG).show();
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void loginValidate(String username, String password) {
        String url = "http://" + SERVERIP + "/walkappservices/v1/users/loginValidate?username=" + username + "&password=" + password;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        response.length();
                        int tamano = -1;

                        try {
                            tamano = response.getJSONArray("datos").length();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("LoginActivity.java", response.toString() + tamano);

                        if (tamano == 1) {
                            goMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.wrongUserPass, Toast.LENGTH_LONG).show();
                            et_user.setText("");
                            et_password.setText("");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("LoginActivity.java", error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }

    public void getUser(String username) {
        String url = "http://" + LoginActivity.SERVERIP + "/walkappservices/v1/users/getUser?username=" + username;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        int tamano = -1;

                        Gson gson = new Gson();
                        try {
                            String json = response.getJSONArray("datos").toString();
                            json = json.substring(1, json.length() - 1);
                            Log.i("LoginActivity.java", json);
                            User user = gson.fromJson(json, User.class);

                            SessionSingleton sessionSingleton = SessionSingleton.getInstance();
                            sessionSingleton.setUsername(user.getUsername());
                            sessionSingleton.setEmail(user.getEmail());
                            sessionSingleton.setAge(user.getAge());
                            sessionSingleton.setLogueado(true);
                            getImage();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("LoginActivity.java", error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }

    public void getImage() {
        String url = "http://" + LoginActivity.SERVERIP + "/walkappservices/v1/images/users/" +
                SessionSingleton.getInstance().getUsername() + ".png";
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //creamos el drawable redondeado
                        RoundedBitmapDrawable roundedDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), bitmap);

                        //asignamos el CornerRadius
                        roundedDrawable.setCornerRadius(bitmap.getHeight());
                        SessionSingleton.getInstance().setPhoto(roundedDrawable);
                        Intent intent = new Intent(DATA_RESULT);
                        broadcaster.sendBroadcast(intent);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);

    }
}
