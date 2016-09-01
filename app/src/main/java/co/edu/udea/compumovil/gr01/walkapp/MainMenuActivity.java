package co.edu.udea.compumovil.gr01.walkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.login.LoginManager;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);//SOlo para crearlo

        switch (view.getId()){
            case R.id.btnMyRoutes:
                intent = new Intent(this,MyRoutesActivity.class);
                break;

            case R.id.btnSearchRoutes:
                intent = new Intent(this,SearchRoutesActivity.class);
                break;
            case R.id.btnCreateRoutes:
                intent = new Intent(this,CreateRouteActivity.class);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        //Si es con facebook, entonces se cierra sesion con el LoginManager
        /*if(tipoLogin==1){
            LoginManager.getInstance().logOut();
        }else{
            //Codigo cierre de sesion usuario normal
        }*/
        goLoginScreen();
    }
}
