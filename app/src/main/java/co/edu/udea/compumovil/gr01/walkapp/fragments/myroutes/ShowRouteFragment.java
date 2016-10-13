package co.edu.udea.compumovil.gr01.walkapp.fragments.myroutes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import co.edu.udea.compumovil.gr01.walkapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowRouteFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double longitud, latitud;
    private String nombre;


    public ShowRouteFragment() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_show_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        longitud = getIntent().getExtras().getDouble("longitud");
        latitud = getIntent().getExtras().getDouble("latitud");
        nombre = getIntent().getExtras().getString("nombre");
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sitio = new LatLng(latitud, longitud);//Latitud y longitud del lugar
        mMap.addMarker(new MarkerOptions().position(sitio).title("Marca en " + nombre + "."));//Agrega una marca
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sitio));//Centrar el sitio en la camara
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);

        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(latitud, longitud))  // Sydney
                .add(new LatLng(latitud+1, longitud+1))  // Fiji
                .add(new LatLng(latitud+2, longitud+2))  // Hawaii
                .add(new LatLng(latitud+3, longitud+3))  // Mountain View
        );
    }


}
