package co.edu.udea.compumovil.gr01.walkapp.fragments;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.activities.MainActivity;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.data.User;
import co.edu.udea.compumovil.gr01.walkapp.singleton.SessionSingleton;

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

        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivProfilePhotoFragment);
        TextView tvNombre = (TextView) view.findViewById(R.id.tvUsuarioPerfil);
        TextView tvCorreo = (TextView) view.findViewById(R.id.tvCorreoPerfil);
        TextView tvEdad = (TextView) view.findViewById(R.id.tvEdadPerfil);

        imageView.setImageDrawable(SessionSingleton.getInstance().getPhoto());
        tvNombre.setText(SessionSingleton.getInstance().getUsername());
        tvCorreo.setText(SessionSingleton.getInstance().getEmail());
        tvEdad.setText(SessionSingleton.getInstance().getAge());

        return view;
    }

}
