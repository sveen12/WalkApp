package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Iterator;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.data.DBHelper;

public class BuildRouteActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener,
        AdapterView.OnItemSelectedListener {

    private static final String TAG = BuildRouteActivity.class.getSimpleName();
    private static final int USER_LOCATION_PERMISSION = 1;
    private GoogleMap googleMap;

    private ArrayList<Marker> markersMain = new ArrayList<>();
    private ArrayList<Marker> markersWater = new ArrayList<>();
    private ArrayList<Marker> markersAtractivos = new ArrayList<>();

    private Polyline rutaMain;
    private Polyline rutaWater;
    private Polyline rutaAtractivos;

    private int markerDraggedPos;
    public Spinner spnPointType;

    public int routeID;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_route);
        //Toma el fragment del xml
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.drawmap);

        mapFragment.getMapAsync(this);

        dbHelper = new DBHelper(this);
        spnPointType = (Spinner) findViewById(R.id.spnrRouteType);
        ArrayList<String> list = dbHelper.getPointType();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPointType.setAdapter(adapter);
        spnPointType.setOnItemSelectedListener(this);

        routeID = getIntent().getIntExtra("routeID", -1);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        //TODO: Quitar action bar

        googleMap = map; //take an instance of current map
        //permiso para acceder a la ubicacion del usuario
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    USER_LOCATION_PERMISSION);
        } else {
            googleMap.setMyLocationEnabled(true);//To detect location of user
        }
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowLongClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerDragListener(this);

        //inicializa polilinea sin puntos, que luego sera modificada
        rutaMain = googleMap.addPolyline(new PolylineOptions()
                .geodesic(true)
                .width(5)
                .color(Color.YELLOW));

        rutaWater = googleMap.addPolyline(new PolylineOptions()
                .geodesic(true)
                .width(5)
                .color(Color.CYAN));
        rutaAtractivos = googleMap.addPolyline(new PolylineOptions()
                .geodesic(true)
                .width(5)
                .color(Color.RED ));
    }

    public ArrayList<LatLng> getCoordinatesOfMarkers(ArrayList<Marker> a) {
        ArrayList<LatLng> points = new ArrayList<>();
        Iterator i = a.iterator(); //set iterator to arraylist of markersMain
        Marker m;
        //while exists markersMain
        while (i.hasNext()) {
            m = (Marker) i.next();//take marker
            points.add(m.getPosition());//add pos of marker into points list
        }
        return points;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == USER_LOCATION_PERMISSION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Can't detect your position!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

        int selectedItem = spnPointType.getSelectedItemPosition();


        Log.d(TAG, "Punto tipo " + selectedItem + "saved in latitud: " + latLng.latitude + " longitud: " + latLng.longitude);
        //Agrega un marcador en el punto donde se dio clic
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng)
                .draggable(true)
                .title("Deje presionado aquí para eliminar el punto")
                .draggable(true));

        switch(selectedItem) {
            case 0:
                //agrega el marker a un array de markersMain
                markersMain.add(marker);
                //actualiza los puntos del polilyne
                rutaMain.setPoints(getCoordinatesOfMarkers(markersMain));
                break;

            case 1:
                //agrega el marker a un array de markersMain
                markersWater.add(marker);
                //actualiza los puntos del polilyne
                rutaWater.setPoints(getCoordinatesOfMarkers(markersWater));
                break;

            case 2:
                //agrega el marker a un array de markersMain
                markersAtractivos.add(marker);
                //actualiza los puntos del polilyne
                rutaAtractivos.setPoints(getCoordinatesOfMarkers(markersAtractivos));
                break;
        }

    }

    //Se llama cuando se presiona un marker
    @Override
    public boolean onMarkerClick(Marker marker) {
        //Cuando se presiona un marker, se abre la ventana de info (default)
        marker.showInfoWindow();
        return true;
    }

    //Elimina un marker
    @Override
    public void onInfoWindowLongClick(Marker marker) {
        //Eliminar un marker
        int selectedItem = spnPointType.getSelectedItemPosition();
        int i;
        switch (selectedItem){
            case 0:
                i = markersMain.indexOf(marker);
                Log.d(TAG, "delete marker type" +selectedItem+" at pos " + i);
                markersMain.remove(i);
                rutaMain.setPoints(getCoordinatesOfMarkers(markersMain));
                //elimina el marcador cuando se presiona largo el info window
                break;
            case 1:
                i = markersWater.indexOf(marker);
                Log.d(TAG, "delete marker type" +selectedItem+" at pos " + i);
                markersWater.remove(i);
                rutaWater.setPoints(getCoordinatesOfMarkers(markersWater));
                //elimina el marcador cuando se presiona largo el info window
                break;
            case 2:
                i = markersAtractivos.indexOf(marker);
                Log.d(TAG, "delete marker type" +selectedItem+" at pos " + i);
                markersAtractivos.remove(i);
                rutaAtractivos.setPoints(getCoordinatesOfMarkers(markersAtractivos));
                //elimina el marcador cuando se presiona largo el info window
                break;
        }
        marker.remove();

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //take the pos in the array of the marker dragged. later the position lanlng will be modified

        int selectedItem = spnPointType.getSelectedItemPosition();

        switch (selectedItem){
            case 0:
                markerDraggedPos = markersMain.indexOf(marker);
                if (markerDraggedPos != -1) Log.d(TAG, "Dragged marker at pos " + markerDraggedPos);
                //remove the marker of the array.
                markersMain.remove(markerDraggedPos);
                break;
            case 1:
                markerDraggedPos = markersWater.indexOf(marker);
                if (markerDraggedPos != -1) Log.d(TAG, "Dragged marker at pos " + markerDraggedPos);
                //remove the marker of the array.
                markersWater.remove(markerDraggedPos);
                break;
            case 2:
                markerDraggedPos = markersAtractivos.indexOf(marker);
                if (markerDraggedPos != -1) Log.d(TAG, "Dragged marker at pos " + markerDraggedPos);
                //remove the marker of the array.
                markersAtractivos.remove(markerDraggedPos);
                break;

        }

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //add markersMain in the pos of the original dragged marker
        int selectedItem = spnPointType.getSelectedItemPosition();

        switch (selectedItem) {
            case 0:
                markersMain.add(markerDraggedPos, marker);
                //update polilyme
                rutaMain.setPoints(getCoordinatesOfMarkers(markersMain));
                break;
            case 1:
                markersWater.add(markerDraggedPos, marker);
                //update polilyme
                rutaWater.setPoints(getCoordinatesOfMarkers(markersWater));
                break;
            case 2:
                markersAtractivos.add(markerDraggedPos, marker);
                //update polilyme
                rutaAtractivos.setPoints(getCoordinatesOfMarkers(markersAtractivos));
                break;
        }

    }

    //
    private void removeRoute() {
        Iterator i = markersMain.iterator();
        while (i.hasNext()) {
            Marker m = (Marker) i.next();
            m.remove(); //remove marker of gmap
        }
        markersMain.clear(); //clear markersMain array
        rutaMain.setPoints(getCoordinatesOfMarkers(markersMain));//remove polilyne
    }

    public void saveRoute(View view) {
        dbHelper.deletePoints(String.valueOf(routeID));

        ArrayList<LatLng> puntos ;

        puntos = getCoordinatesOfMarkers(markersMain);
        for(int i =0; i<markersMain.size();i++){
            dbHelper.addPoint(routeID, 1 , i ,puntos.get(i).longitude,puntos.get(i).latitude);
        }

        puntos = getCoordinatesOfMarkers(markersWater);
        for(int i =0; i<markersWater.size();i++){
            dbHelper.addPoint(routeID, 2 , i ,puntos.get(i).longitude,puntos.get(i).latitude);
        }

        puntos = getCoordinatesOfMarkers(markersAtractivos);
        for(int i =0; i<markersAtractivos.size();i++){
            dbHelper.addPoint(routeID, 3 , i ,puntos.get(i).longitude,puntos.get(i).latitude);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Se selecciono la opcion:" + position);
        /*switch (position){
            case 0:
                rutaMain.setVisible(true);
                rutaWater.setVisible(false);
                rutaAtractivos.setVisible(false);
                break;
            case 1:
                rutaMain.setVisible(false);
                rutaWater.setVisible(true);
                rutaAtractivos.setVisible(false);
                break;
            case 2:
                rutaMain.setVisible(false);
                rutaWater.setVisible(false);
                rutaAtractivos.setVisible(true);
                break;
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}