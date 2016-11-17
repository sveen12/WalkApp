package co.edu.udea.compumovil.gr01.walkapp.fragments.createroute;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.activities.BuildRouteActivity;
import co.edu.udea.compumovil.gr01.walkapp.activities.LoginActivity;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;
import co.edu.udea.compumovil.gr01.walkapp.singleton.SessionSingleton;
import co.edu.udea.compumovil.gr01.walkapp.singleton.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateRouteDialogFragment extends DialogFragment {


    public EditText etNombre;
    public EditText etFoto;
    public EditText etDescripcion;
    public EditText etDificultad;
    public EditText etClima;
    public EditText etComoLlegar;
    RequestQueue queue;

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

        queue = VolleySingleton.getInstance(this.getActivity()).
                getRequestQueue();

        etNombre = (EditText) view.findViewById(R.id.etNombre);
        etFoto = (EditText) view.findViewById(R.id.etFoto);
        etDescripcion = (EditText) view.findViewById(R.id.etDescripcion);
        etDificultad = (EditText) view.findViewById(R.id.etDificultad);
        etClima = (EditText) view.findViewById(R.id.etClima);
        etComoLlegar = (EditText) view.findViewById(R.id.etComoLlegar);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        validateRoute(etNombre.getText().toString().replace(" ","%20"));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateRouteDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    public void validateRoute(String name) {
        String url = "http://" + LoginActivity.SERVERIP + "/walkappservices/v1/routes/validateRoute?name=" + name;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        response.length();
                        int tamano = -1;

                        try {
                            tamano = response.getJSONArray("datos").length();

                            if (tamano == 1) {
                                Toast.makeText(getContext(), "Hay otra ruta con este nombre.", Toast.LENGTH_SHORT).show();
                            } else {
                                addRoute(SessionSingleton.getInstance().getUsername(),
                                        etNombre.getText().toString(),
                                        etFoto.getText().toString(),
                                        etDescripcion.getText().toString(),
                                        Integer.parseInt(etDificultad.getText().toString()),
                                        etClima.getText().toString(),
                                        etComoLlegar.getText().toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("LoginActivity.java", error.toString());

                    }
                });
        queue.add(jsObjRequest);
    }

    public void addRoute(String username, final String name, String photo, String description,
                         int difficulty, String weather, String howarrive)  {
        String url = "http://" + LoginActivity.SERVERIP + "/walkappservices/v1/routes/addRoute";

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("name", name);
            jsonObject.put("photo", photo);
            jsonObject.put("description", description);
            jsonObject.put("difficulty", difficulty);
            jsonObject.put("weather", weather);
            jsonObject.put("howarrive", howarrive);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject1=null;
                        try {
                            jsonObject1 = new JSONObject(response);

                            if(jsonObject1.get("mensaje").toString().equals("¡Registro con éxito!")){
                                Toast.makeText(getActivity(), "¡Registro exitoso!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), BuildRouteActivity.class);
                                intent.putExtra("routeID", name);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getContext(),"Algo salió mal. Intentelo de nuevo.", Toast.LENGTH_SHORT).show();
                                CreateRouteDialogFragment.this.getDialog().cancel();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("CreateRouteDialog.java", error.toString());
                Toast.makeText(getContext(),"Algo salió mal. Intentelo de nuevo.", Toast.LENGTH_SHORT).show();
                CreateRouteDialogFragment.this.getDialog().cancel();
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonObject.toString().getBytes();
            }
        };
        queue.add(stringRequest);
    }
}
