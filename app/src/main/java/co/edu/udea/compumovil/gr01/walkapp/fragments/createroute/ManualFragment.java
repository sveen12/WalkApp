package co.edu.udea.compumovil.gr01.walkapp.fragments.createroute;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManualFragment extends Fragment {


    public ManualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_manual, container, false);
        DBHelper dbHelper = new DBHelper(getContext());
        Spinner spnPointType = (Spinner) view.findViewById(R.id.spnPointType);
        ArrayList<String> list = dbHelper.getPointType();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPointType.setAdapter(adapter);
        return view;
    }

}
