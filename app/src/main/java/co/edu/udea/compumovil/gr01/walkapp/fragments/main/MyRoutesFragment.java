package co.edu.udea.compumovil.gr01.walkapp.fragments.main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.udea.compumovil.gr01.walkapp.CustomAdapter;
import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.RowItem;
import co.edu.udea.compumovil.gr01.walkapp.activities.LoginActivity;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.Route;
import co.edu.udea.compumovil.gr01.walkapp.fragments.DetailsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRoutesFragment extends Fragment implements AdapterView.OnItemClickListener {

    //esta variable guarda el tipo de login que se produjo
    //0 login normal, 1 login facebook

    private ImageView image1, image2;

    DBHelper dbHelper;
    List<RowItem> rowItems;
    ListView lvFavoriteRoutes, lvCreatedRoutes;
    CustomAdapter adapter;
    ArrayList<Route> routes;

    public static int selectedItem;

    RequestQueue queue;

    public MyRoutesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_my_routes, container, false);

        queue = Volley.newRequestQueue(getActivity());


        lvFavoriteRoutes = (ListView) view.findViewById(R.id.lvMyFavoriteRoutes);
        lvFavoriteRoutes.setOnItemClickListener(this);
        lvFavoriteRoutes.setClickable(true);
        lvFavoriteRoutes.setEnabled(true);
        registerForContextMenu(lvFavoriteRoutes);

        lvCreatedRoutes = (ListView) view.findViewById(R.id.lvMyCreatedRoutes);
        lvCreatedRoutes.setOnItemClickListener(this);
        lvCreatedRoutes.setClickable(true);
        lvCreatedRoutes.setEnabled(true);
        registerForContextMenu(lvCreatedRoutes);

        post();


        routes = dbHelper.getFavoriteRoutes("root");
        if(routes.size()!=0){
            adapter = convertir(routes);
            lvFavoriteRoutes.setAdapter(adapter);
        }

        routes = dbHelper.getMyRoutes("root");

        if(routes.size()!=0){
            adapter = convertir(routes);
            lvCreatedRoutes.setAdapter(adapter);
        }
        return view;
    }


    public CustomAdapter convertir(ArrayList <Route> rutas){
        if(rutas.size()!=0){

            rowItems = new ArrayList<RowItem>();

            for(int i =0; i<rutas.size();i++){
                RowItem item = new RowItem( rutas.get(i).getNombre(),
                        rutas.get(i).getUsername(),
                        getTextDificultty(rutas.get(i).getDifficulty()),
                        rutas.get(i).getWeather(),
                        3) ;
                rowItems.add(item);
            }
        }

        else{

        }
        return new CustomAdapter(getActivity(), rowItems);
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
            if(dbHelper.addFavoriteRoute(routes.get(selectedItem).getIdroute(),  "root")){
                Toast.makeText(getActivity(), "Ruta añadida a favoritos.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "No se añadio la ruta", Toast.LENGTH_SHORT).show();
            }

        }else if(item.getTitle().toString().equals("Ver detalles")){
            DialogFragment dialog = new DetailsDialog();
            dialog.show(this.getFragmentManager(), "Detalles");
        }


        return super.onContextItemSelected(item);
    }



    public void post(){
        String url = "http://"+ LoginActivity.SERVERIP+"/walkappservices/v1/points/addPoints";
        JSONArray puntos = new JSONArray();
        JSONObject punto1 = new JSONObject();
        JSONObject punto2 = new JSONObject();
        JSONObject arrayPuntos = new JSONObject();

        try {
            punto1.put("idRoute","1");
            punto1.put("orden","longitud");
            punto1.put("longitud","5");
            punto1.put("latitud","1");
            punto1.put("tipo","Principal");

            punto2.put("idRoute","1");
            punto2.put("orden","longitud");
            punto2.put("longitud","5");
            punto2.put("latitud","1");
            punto2.put("tipo","Principal");

            puntos.put(punto1);
            puntos.put(punto2);
            arrayPuntos.put("arrayPuntos",puntos);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("MyRoutesFragment.java",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("MyRoutesFragment.java",error.getMessage());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(request);
    }


}
