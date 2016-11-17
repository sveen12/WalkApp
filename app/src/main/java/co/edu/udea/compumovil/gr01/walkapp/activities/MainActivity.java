package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.User;
import co.edu.udea.compumovil.gr01.walkapp.fragments.MyProfileFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.createroute.CreateRouteDialogFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.MyRoutesFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.main.SearchRoutesFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.myroutes.ShowRouteFragment;
import co.edu.udea.compumovil.gr01.walkapp.singleton.SessionSingleton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public BroadcastReceiver receiver;
    private Fragment fragmentoGenerico;
    private FragmentManager fragmentManager;
    private ImageView mImageView;
    private RequestQueue queue;
    public static RoundedBitmapDrawable roundedDrawable;
    TextView tvNombreNavHeader;
    TextView tvCorreoNavHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentoGenerico = new MyProfileFragment();
        fragmentManager = getSupportFragmentManager();


        queue = Volley.newRequestQueue(this);

        updateFragment();
        /*//Inserciones para pruebas
        dbHelper.addPointType(1,"Principal");
        dbHelper.addPointType(2,"Agua");
        dbHelper.addPointType(3,"Atractivos");

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

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean respuestaDatos = intent.getBooleanExtra(LoginActivity.DATA_RESULT,false);

                if(respuestaDatos){
                    tvNombreNavHeader.setText(SessionSingleton.getInstance().getUsername());
                    tvCorreoNavHeader.setText(SessionSingleton.getInstance().getEmail());
                    mImageView.setImageDrawable(SessionSingleton.getInstance().getPhoto());
                }
            }
        };



    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(LoginActivity.DATA_RESULT)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        tvNombreNavHeader = (TextView) findViewById(R.id.tvNombreNavHeader);
        tvCorreoNavHeader = (TextView) findViewById(R.id.tvCorreoNavHeader);
        mImageView = (ImageView) findViewById(R.id.ivProfilePicture);

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
            /*Intent intent = new Intent(this,BuildRouteActivity.class);
            startActivity(intent)*/
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
