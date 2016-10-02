package co.edu.udea.compumovil.gr01.walkapp.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.udea.compumovil.gr01.walkapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRouteFragment extends Fragment {


    public CreateRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_route, container, false);

        return view;
    }

}
