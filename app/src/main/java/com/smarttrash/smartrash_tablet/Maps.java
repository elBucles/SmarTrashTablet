package com.smarttrash.smartrash_tablet;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());


        if (status == ConnectionResult.SUCCESS) {

            SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();

        }

        db = FirebaseDatabase.getInstance().getReference().child("tarjetas");

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Tarjeta tarjeta = dataSnapshot.getValue(Tarjeta.class);
                validarTarjeta(tarjeta);

            }
            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override public void onCancelled(DatabaseError databaseError) { }
        });
    }

    GoogleMap map;
    Boolean actualPosition = true;
    Double longitudOrigen, latitudOrigen;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {

                if (actualPosition){
                    latitudOrigen = location.getLatitude();
                    longitudOrigen = location.getLongitude();
                    actualPosition=false;

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitudOrigen,longitudOrigen))       //Sets the center
                            .zoom(14)       //Sets the zoom
                            .build();       //Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });

        UiSettings uiSettings =mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(false);

        // Add a marker
        LatLng ut = new LatLng(32.437667, -114.716000);
        mMap.addMarker(new MarkerOptions().position(ut).title("UTSLRC"));

        LatLng flex = new LatLng(32.460777, -114.712233);
        mMap.addMarker(new MarkerOptions().position(flex).title("Flex LTD"));

        LatLng vet = new LatLng(32.469521, -114.792080);
        mMap.addMarker(new MarkerOptions().position(vet).title("Veterinaria My Rancherita"));
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void validarTarjeta(Tarjeta tarjeta){
        vibrar();
        db.child(String.valueOf(tarjeta.id)).removeValue();
        if(tarjeta.tipo.trim().equals("contenedor")){
            sonar();
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_container);
            TextView peso = dialog.findViewById(R.id.dialogo_peso);
            ImageView image = dialog.findViewById(R.id.dialogo_imagen);
            TextView tipo = dialog.findViewById(R.id.dialogo_tipo);
            if(tarjeta.id == 763476783489L) {
                image.setImageDrawable(getDrawable(R.drawable.metal));
                tipo.setText("Tipo: Metales");
            } else if(tarjeta.id == 858154394899L) {
                image.setImageDrawable(getDrawable(R.drawable.vidrio));
                tipo.setText("Tipo: Vidrios");
            }
            peso.setText("Peso: " + tarjeta.peso.trim() + " kilogramos");
            dialog.show();
        }
    }

    public void vibrar(){
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
    }

    public void sonar(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    }
}
