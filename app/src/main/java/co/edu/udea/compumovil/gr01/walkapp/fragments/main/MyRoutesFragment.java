package co.edu.udea.compumovil.gr01.walkapp.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.Route;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRoutesFragment extends Fragment {

    //esta variable guarda el tipo de login que se produjo
    //0 login normal, 1 login facebook

    private ImageView image1, image2;

    public MyRoutesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DBHelper dbHelper = new DBHelper(getContext());
        ArrayList<Route> routes = dbHelper.getMyRoutes("root");
        Toast.makeText(getContext(), String.valueOf(routes.size()),Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_routes, container, false);

        image1 = (ImageView) view.findViewById(R.id.image1);
        image1.setImageResource(R.drawable.image1);
        image2 = (ImageView) view.findViewById(R.id.image2);
        image2.setImageResource(R.drawable.image2);


        return view;
    }



}
