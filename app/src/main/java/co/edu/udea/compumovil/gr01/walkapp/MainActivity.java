package co.edu.udea.compumovil.gr01.walkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    //esta variable guarda el tipo de login que se produjo
    private int tipoLogin;//0 login normal, 1 login facebook
    private double longitud, latitud;
    String nombre;
    private ImageView image1, image2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipoLogin = getIntent().getExtras().getInt("tipoLogin");

        image1 = (ImageView) findViewById(R.id.image1);
        image1.setImageResource(R.drawable.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image2.setImageResource(R.drawable.image2);

        FacebookSdk.sdkInitialize(getApplicationContext());

        //Si se logueo con facebook pero no existe un token, entonces fue un logueo incorrecto con facebok
        if(tipoLogin==1 && AccessToken.getCurrentAccessToken()== null){
            goLoginScreen();
        }

    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        //Si es con facebook, entonces se cierra sesion con el LoginManager
        if(tipoLogin==1){
            LoginManager.getInstance().logOut();
        }else{
            //Codigo cierre de sesion usuario normal
        }
        goLoginScreen();
    }

    public void openMap(View view) {

    }

    public void abrirMapa(View view) {
        Intent intent = new Intent(this,SavedRoutesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);


        switch (view.getId()){
            //Represa Riogrande
            case R.id.option1:
                longitud =6.5142779;
                latitud=-75.4604029;
                nombre = "Represa Riogrande";
                break;
            //San Félix
            case R.id.option2:
                longitud =6.3225976;
                latitud=-75.5967479;
                nombre = "San Félix";
                break;
        }
        intent.putExtra("longitud", longitud);
        intent.putExtra("latitud", latitud);
        intent.putExtra("nombre", nombre);
        startActivity(intent);
    }
}
