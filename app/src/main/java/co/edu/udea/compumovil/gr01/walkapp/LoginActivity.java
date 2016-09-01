package co.edu.udea.compumovil.gr01.walkapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    /*private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;*/
    private EditText et_user;
    private EditText et_password;
    private int tipoLogin=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Campos de texto para logueo normal
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);

        /*
        callbackManager =  CallbackManager.Factory.create();

        //Acciones a ejecutar cuando se presiona el boton de Facebook
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            //En caso de que el logueo sea exitoso
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                tipoLogin = 1;

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Procesando datos...");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Se consiguen los datos de facebook de quien se logueo
                        Bundle bFacebookData = getFacebookData(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


                //requestUserProfile(loginResult);
                //Se redirige a la pantalla principal
                goMainActivity();
            }
            //En caso de que el logueo se cancele por fallos en la conexion a internet o cerrar la aplicacion
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }
            //En caso de que haya algun error en el proceso logueo
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    //Pide los permisos para acceso a los datos si se logueo con facebook

    /*public void loginFacebook(View view){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    //Extrae los datos del usuario Facebook que ingreso a la aplicación
    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

    //En caso de loguearse correctamente se abre la actividad principal
    private void goMainActivity() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("tipoLogin", tipoLogin);
        startActivity(intent);
    }

    //Login con usuario y cuenta normales
    public void normalLogin(View view) {
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
        if(et_user.getText().toString().equals("admin") && et_password.getText().toString().equals("admin")){
            tipoLogin = 0;
            goMainActivity();
        }else{
            Toast.makeText(this, R.string.wrongUserPass, Toast.LENGTH_LONG).show();
        }

    }

    //Se muestra cuando hay algun campo vacio.
    public void errorMessage(){
        Toast.makeText(this, R.string.emptyFields, Toast.LENGTH_LONG).show();
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*//-Para extraer los datos
    public void requestUserProfile(LoginResult loginResult){
        GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override

                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            try {
                                String email = response.getJSONObject().get("email").toString();
                                Log.e("Result", email);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String id = me.optString("id");

                            // send email and id to your web server
                            Log.e("Result1", response.getRawResponse());
                            Log.e("Result", me.toString());
                        }
                    }
                }).executeAsync();
    }
    */


}
