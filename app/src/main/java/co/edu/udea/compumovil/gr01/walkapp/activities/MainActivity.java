package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.fragments.MyProfileFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.CreateRouteFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.MyRoutesFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.SearchRoutesFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.myroutes.ShowRouteFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragmentoGenerico;
    private FragmentManager fragmentManager;
    private double longitud, latitud;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentoGenerico = new MyProfileFragment();
        fragmentManager = getSupportFragmentManager();

        updateFragment();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.btnmenu_profile:
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
            Intent intent = new Intent(this,CreateRouteActivity.class);
            startActivity(intent);
        } else if (id == R.id.it_search_routes) {
            fragmentoGenerico = new SearchRoutesFragment();
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
