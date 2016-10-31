package co.edu.udea.compumovil.gr01.walkapp.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01.walkapp.CustomAdapter;
import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.RowItem;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.Route;

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

    public MyRoutesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_my_routes, container, false);


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


        routes = dbHelper.getFavoriteRoutes("root");
        adapter = convertir(routes);
        lvFavoriteRoutes.setAdapter(adapter);

        routes = dbHelper.getMyRoutes("root");
        adapter = convertir(routes);
        lvCreatedRoutes.setAdapter(adapter);


        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
}
