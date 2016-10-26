package co.edu.udea.compumovil.gr01.walkapp.fragments.createroute;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.activities.CreateRouteActivity;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRouteDialogFragment extends DialogFragment {



    public CreateRouteDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DBHelper dbHelper = new DBHelper(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_route_dialog, null);

        final EditText etNombre = (EditText)view.findViewById(R.id.etNombre);
        final EditText etFoto = (EditText) view.findViewById(R.id.etFoto);
        final EditText etDescripcion = (EditText) view.findViewById(R.id.etDescripcion);
        final EditText etDificultad = (EditText) view.findViewById(R.id.etDificultad);
        final EditText etClima = (EditText) view.findViewById(R.id.etClima);
        final EditText etComoLlegar = (EditText) view.findViewById(R.id.etComoLlegar);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(!dbHelper.validateRoute(etNombre.getText().toString())){
                            Toast.makeText(getContext(),"Hay otra ruta con este nombre.", Toast.LENGTH_SHORT).show();
                        }else{
                            if(dbHelper.addRoute("root",
                                                etNombre.getText().toString(),
                                                etFoto.getText().toString(),
                                                etDescripcion.getText().toString(),
                                                Integer.parseInt(etDificultad.getText().toString()),
                                                etClima.getText().toString(),
                                                etComoLlegar.getText().toString())){
                                Toast.makeText(getContext(),"Ruta creada exitosamente.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), CreateRouteActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getContext(),"Algo salió mal. Intentelo de nuevo.", Toast.LENGTH_SHORT).show();
                                CreateRouteDialogFragment.this.getDialog().cancel();
                            }


                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateRouteDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
