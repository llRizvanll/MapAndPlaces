package com.co.nightowl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.co.nightowl.Model.PlacesDataModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private PlacesDataModel dataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = MapsActivity.this;

        if (getIntent().getBundleExtra("bundle_data")!=null && getIntent().getBundleExtra("bundle_data").getSerializable("places_data") instanceof PlacesDataModel){
            this.dataModel = (PlacesDataModel) getIntent().getBundleExtra("bundle_data").getSerializable("places_data");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

       for(int i = 0 ; i < dataModel.getResults().size();i++){
           Double lat = dataModel.getResults().get(i).getLocation().getLat();
           Double lng = dataModel.getResults().get(i).getLocation().getLng();
           String s1 = dataModel.getResults().get(i).getName()+" ";
           String s2 = " RENT ";
           Log.d("MAPS"," lat "+lat+" lng "+lng);
           // Add a marker in Sydney and move the camera
           /*LatLng sydney = new LatLng(lat, lng);
           mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
           mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
           createMarker(lat,lng, s1, s2 );

           if (i >= (dataModel.getResults().size() - 1) ) {
               LatLng points = new LatLng(lat,lng);
               CameraPosition cameraPosition = new CameraPosition.Builder()
                       .target(points)      // Sets the center of the map to Mountain View
                       .zoom(10)                   // Sets the zoom
                       .bearing(90)                // Sets the orientation of the camera to east
                       .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                       .build();
               mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

           }
       }

    }

    /**
     * Add Multiple Marker on the go
     * @param latitude
     * @param longitude
     * @param title
     * @param snippet
     */
    protected void createMarker(double latitude, double longitude, String title, String snippet) {
        LatLng points = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(points)
                .anchor(1f, 1f)
                .title(title)
                .snippet(snippet));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent startCardFormPage = new Intent(activity,CardInfoActivity.class);
                startActivity(startCardFormPage);
            }
        });
        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.

    }
}
