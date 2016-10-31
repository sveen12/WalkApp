package co.edu.udea.compumovil.gr01.walkapp.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.activities.SearchRoutesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsDialog extends DialogFragment {

    ImageView ivDialogImage;
    TextView tvDialogNombre,
            tvDialogUsername,
            tvDialogDescripcion,
            tvDialogDificultad,
            tvDialogClima,
            tvDialogComoLlegar;
    RatingBar rbDialogRating;
    int selectedItem = SearchRoutesActivity.selectedItem;

    public DetailsDialog() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_details_dialog, container, false);

        getDialog().setTitle("Detalles");
        tvDialogNombre = (TextView) view.findViewById(R.id.tvDialogNombre);
                tvDialogUsername = (TextView) view.findViewById(R.id.tvDialogUsername);
                tvDialogDescripcion = (TextView) view.findViewById(R.id.tvDialogDescripcion);
                tvDialogDificultad = (TextView) view.findViewById(R.id.tvDialogDificultad);
                tvDialogClima = (TextView) view.findViewById(R.id.tvDialogClima);
                tvDialogComoLlegar = (TextView) view.findViewById(R.id.tvDialogComoLlegar);

        tvDialogNombre.setText(SearchRoutesActivity.routes.get(selectedItem).getName());
        tvDialogUsername.setText("Por: " + SearchRoutesActivity.routes.get(selectedItem).getUsername());
        tvDialogDescripcion.setText("Descripción: " + SearchRoutesActivity.routes.get(selectedItem).getDescription());
        tvDialogDificultad.setText("Dificultad: " + SearchRoutesActivity.getTextDificultty(
                                            SearchRoutesActivity.routes.get(selectedItem).getDificultty()));
        tvDialogClima.setText("Clima: " + SearchRoutesActivity.routes.get(selectedItem).getWeather());
        tvDialogComoLlegar.setText("Cómo llegar: " + SearchRoutesActivity.routes.get(selectedItem).getHowarrive());


        return view;
    }
}
