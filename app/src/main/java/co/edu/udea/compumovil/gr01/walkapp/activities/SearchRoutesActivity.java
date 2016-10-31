package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01.walkapp.CustomAdapter;
import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.RowItem;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.Route;
import co.edu.udea.compumovil.gr01.walkapp.fragments.DetailsDialog;

public class SearchRoutesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DBHelper dbHelper;
    TextView textView;

    List<RowItem> rowItems;
    ListView mylistview;
    CustomAdapter adapter;
    public static ArrayList <Route> routes;
    public static int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_routes);

        mylistview = (ListView) findViewById(R.id.lvEncontrados);
        mylistview.setOnItemClickListener(this);
        mylistview.setClickable(true);
        mylistview.setEnabled(true);
        registerForContextMenu(mylistview);

        textView = (TextView) findViewById(R.id.textView10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_routes_activity_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("Buscar ruta");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_SHORT).show();
                //se oculta el EditText
                searchView.setQuery("", false);
                searchView.setIconified(true);

                routes  = dbHelper.searchRoutes(query);
                Toast.makeText(getApplicationContext(),
                        String.valueOf(routes.size()) + " resultados encontrados", Toast.LENGTH_SHORT).show();

                adapter = convertir(routes);
                mylistview.setAdapter(adapter);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                textView.setText(newText);
                return true;
            }

        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toLogout:
                Intent intent = new Intent(this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            case android.R.id.home:
                super.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        parent.showContextMenuForChild(view);
        selectedItem = position;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Opciones");
        menu.add("A favoritos");
        menu.add("Ver detalles");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().toString().equals("A favoritos")) {
            if(dbHelper.addFavoriteRoute(routes.get(selectedItem).getId(),  "root")){
                Toast.makeText(getApplicationContext(), "Ruta añadida a favoritos.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "No se añadio la ruta", Toast.LENGTH_SHORT).show();
            }

        }else if(item.getTitle().toString().equals("Ver detalles")){
            DialogFragment dialog = new DetailsDialog();
            dialog.show(getSupportFragmentManager(), "Detalles");
        }


        return super.onContextItemSelected(item);
    }

    public CustomAdapter convertir(ArrayList <Route> rutas){
        if(rutas.size()!=0){

            rowItems = new ArrayList<RowItem>();

            for(int i =0; i<rutas.size();i++){
                RowItem item = new RowItem( rutas.get(i).getName(),
                        rutas.get(i).getUsername(),
                        getTextDificultty(rutas.get(i).getDificultty()),
                        rutas.get(i).getWeather(),
                        3) ;
                rowItems.add(item);
            }
        }
        else{
            textView.setText("Resultados no encontrados.");
        }
        return new CustomAdapter(getApplicationContext(), rowItems);
    }

    public static String getTextDificultty(int number){
        switch(number){
            case 1:
                return "Muy fácil";
            case 2:
                return "Fácil";
            case 3:
                return "Normal";
            case 4:
                return "Difícil";
            case 5:
                return "Experto";
            default:
                return "Otro";

        }
    }


}
