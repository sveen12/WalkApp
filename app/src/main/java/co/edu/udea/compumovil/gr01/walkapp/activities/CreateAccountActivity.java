package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.singleton.VolleySingleton;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etUsername, etPassword1, etPassword2, etEmail, etAge;
    private RequestQueue queue;  //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        etUsername = (EditText) findViewById(R.id.etUser);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword1 = (EditText)findViewById(R.id.etPassword1);
        etPassword2 = (EditText)findViewById(R.id.etPassword2);
        etAge = (EditText) findViewById(R.id.etAge);
        queue = VolleySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
    }

    public void onClik(View view) {
        confirmarCamposVacios();
        confirmacionContrasenas();

        registerValidate(etUsername.getText().toString());
    }

    public void errorMessage(){
        Toast.makeText(this,R.string.emptyFields, Toast.LENGTH_LONG).show();
    }

    public void confirmarCamposVacios(){
        if(etUsername.getText().toString().equals("")){
            etUsername.requestFocus();
            etUsername.setError("Ingrese un nombre de usuario");
            errorMessage();
            return;
        }
        if(etPassword1.getText().toString().equals("")){
            etPassword1.requestFocus();
            etPassword1.setError("Ingrese una contraseña");
            errorMessage();
            return;
        }
        if(etPassword2.getText().toString().equals("")){
            etPassword2.requestFocus();
            etPassword2.setError("Confirme su contraseña");
            errorMessage();
            return;
        }
        if(etEmail.getText().toString().equals("")){
            etEmail.requestFocus();
            etEmail.setError("Ingrese su correo");
            errorMessage();
            return;
        }
        if(etAge.getText().toString().equals("")){
            etAge.requestFocus();
            etAge.setError("Ingrese su edad");
            errorMessage();
            return;
        }
    }

    public void confirmacionContrasenas(){
        if(!etPassword2.getText().toString().equals(etPassword1.getText().toString())){
            etPassword2.requestFocus();
            etPassword2.setError("Las contraseñas deben ser iguales");
            return;
        }
    }


    public void registerValidate(String username){
        String url = "http://"+LoginActivity.SERVERIP+"/walkappservices/v1/users/registerValidate?username="+username;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        response.length();
                        int tamano=-1;

                        try {
                            tamano = response.getJSONArray("datos").length();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("LoginActivity.java",response.toString()+ tamano);

                        if (tamano==1) {//Existe un usuario con ese nombre
                            etUsername.requestFocus();
                            etUsername.setError("El nombre de usuario ya existe.");
                        }
                        else{
                            createUser(
                                    etUsername.getText().toString(),
                                    etPassword1.getText().toString(),
                                    etEmail.getText().toString(),
                                    etAge.getText().toString(),
                                    "foto.jpg");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("LoginActivity.java",error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }

    public void createUser(final String username,
                                 final String password,
                                 final String email,
                                 final String age,
                                 final String profilephoto)  {
        String url = "http://" + LoginActivity.SERVERIP + "/walkappservices/v1/users/createUser";

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", email);
            jsonObject.put("age", age);
            jsonObject.put("profilephoto", profilephoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject1=null;
                        try {
                            jsonObject1 = new JSONObject(response);

                        if(jsonObject1.get("mensaje").toString().equals("¡Registro con éxito!")){
                            Toast.makeText(getApplicationContext(), "¡Registro exitoso!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("CreateAccountAct.java", error.toString());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonObject.toString().getBytes();
            }
        };
        queue.add(stringRequest);
    }
}
