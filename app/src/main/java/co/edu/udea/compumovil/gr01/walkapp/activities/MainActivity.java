package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.User;
import co.edu.udea.compumovil.gr01.walkapp.fragments.MyProfileFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.createroute.CreateRouteDialogFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.MyRoutesFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.SearchRoutesFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.myroutes.ShowRouteFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragmentoGenerico;
    private FragmentManager fragmentManager;
    private double longitud, latitud;
    private String nombre;
    private DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentoGenerico = new MyProfileFragment();
        fragmentManager = getSupportFragmentManager();



        updateFragment();
        /*//Inserciones para pruebas
        dbHelper.addPointType(1,"Main");
        dbHelper.addPointType(2,"Wather");
        dbHelper.addPointType(3,"River");
        dbHelper.addPointType(4,"Animals");

        dbHelper.addRoute("andres",
                "Belmira",
                "sz.png",
                "Sitio caliente.",
                3,
                "Templadin",
                "Bus desde Medellín");

        dbHelper.addRoute("root",
                "Donmatias",
                "dm.png",
                "Sitio frio e ideal para nadar.",
                4,
                "Tropical",
               "Bus desde Medellín");*/

        //Para la implementación del NavDraw
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        TextView tvNombreNavHeader = (TextView) findViewById(R.id.tvNombreNavHeader);
        TextView tvCorreoNavHeader = (TextView) findViewById(R.id.tvCorreoNavHeader);
        //Agregar imagen

        User usuario = dbHelper.getUser("root");
        tvNombreNavHeader.setText(usuario.getUsername());
        tvCorreoNavHeader.setText(usuario.getEmail());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.btnmenu_profile:
                fragmentoGenerico = new MyProfileFragment();
                updateFragment();
                return true;
            case R.id.btnmenu_logout:
                goLoginScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.it_my_profile) {
            fragmentoGenerico = new MyProfileFragment();

        } else if (id == R.id.it_my_routes) {
            fragmentoGenerico = new MyRoutesFragment();
        } else if (id == R.id.it_create_routes) {
            DialogFragment dialog = new CreateRouteDialogFragment();
            dialog.show(getSupportFragmentManager(), "Detalles de la ruta");

        } else if (id == R.id.it_search_routes) {
            Intent intent = new Intent(this,SearchRoutesActivity.class);
            startActivity(intent);
        } else if (id == R.id.it_sign_up) {
            goLoginScreen();
        }
        updateFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateFragment(){
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoGenerico)
                    .commit();
        }
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

    public void myProfile(MenuItem item) {
        fragmentoGenerico = new MyProfileFragment();

        updateFragment();
    }


    public void abrirMapa(View view) {
        Intent intent = new Intent(this,ShowRouteFragment.class);

        switch (view.getId()){
            //Represa Riogrande
            case R.id.option1:
                latitud =6.5142779;
                longitud=-75.4604029;
                nombre = "Represa Riogrande";
                break;
            //San Félix
            case R.id.option2:
                latitud =6.3225976;
                longitud=-75.5967479;
                nombre = "San Félix";
                break;
        }
        intent.putExtra("longitud", longitud);
        intent.putExtra("latitud", latitud);
        intent.putExtra("nombre", nombre);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
