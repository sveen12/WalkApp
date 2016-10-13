package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;

public class CreateAccountActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    EditText etUsername, etPassword1, etPassword2, etEmail, etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        etUsername = (EditText) findViewById(R.id.etUser);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword1 = (EditText)findViewById(R.id.etPassword1);
        etPassword2 = (EditText)findViewById(R.id.etPassword2);
        etAge = (EditText) findViewById(R.id.etAge);
    }

    public void onClik(View view) {
        confirmarCamposVacios();
        confirmacionContrasenas();

        if(dbHelper.registerValidate(
                etUsername.getText().toString(),
                etPassword1.getText().toString(),
                etEmail.getText().toString(),
                etAge.getText().toString(),
                "foto.jpg"))
        {
                Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_LONG).show();

        }else{
                etUsername.requestFocus();
                etUsername.setError("El nombre de usuario ya existe.");
        }
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
}
