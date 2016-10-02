package co.edu.udea.compumovil.gr01.walkapp.fragments.myroutes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.udea.compumovil.gr01.walkapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditRouteFragment extends Fragment {


    public EditRouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_route, container, false);
    }

}
