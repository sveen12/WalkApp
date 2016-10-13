package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText et_user;
    private EditText et_password;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Campos de texto para logueo normal
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    //En caso de loguearse correctamente se abre la actividad principal
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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

        boolean login = dbHelper.loginValidate(et_user.getText().toString(), et_password.getText().toString());
        if (login) {
            goMainActivity();
        }
        else{
            Toast.makeText(this, R.string.wrongUserPass, Toast.LENGTH_LONG).show();
            et_user.setText("");
            et_password.setText("");
        }
    }

    //Se muestra cuando hay algun campo vacio.
    public void errorMessage(){
        Toast.makeText(this, R.string.emptyFields, Toast.LENGTH_LONG).show();
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
