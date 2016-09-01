package co.edu.udea.compumovil.gr01.walkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    private int tipoLogin=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //tipoLogin = getIntent().getExtras().getInt("tipoLogin");



/*
        //Si se logueo con facebook pero no existe un token, entonces fue un logueo incorrecto con facebok
        if(tipoLogin==0){
            System.out.println("Entro a login 0");
        }else if(tipoLogin==-1){
            System.out.println("Aun no se ha abierto el LoginActivity");
            goLoginScreen();
        }else if(tipoLogin==1 && AccessToken.getCurrentAccessToken()== null){
            System.out.println("Entro a login 1");
            goLoginScreen();
        }*/
    }




}
