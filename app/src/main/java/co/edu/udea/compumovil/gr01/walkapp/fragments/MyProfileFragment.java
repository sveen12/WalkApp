package co.edu.udea.compumovil.gr01.walkapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DBHelper dbHelper = new DBHelper(getContext());

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        TextView tvNombre = (TextView) view.findViewById(R.id.tvUsuarioPerfil);
        TextView tvCorreo = (TextView) view.findViewById(R.id.tvCorreoPerfil);
        TextView tvEdad = (TextView) view.findViewById(R.id.tvEdadPerfil);
        TextView tvFoto = (TextView) view.findViewById(R.id.tvFotoPerfil);

        String usuarioLogueado = getActivity().getIntent().getExtras().getString("user");

        User usuario = dbHelper.getUser(usuarioLogueado);

        tvNombre.setText(usuario.getUsername());
        tvCorreo.setText(usuario.getEmail());
        tvEdad.setText(usuario.getAge());
        tvFoto.setText(usuario.getProfilephoto());

        return view;
    }

}
