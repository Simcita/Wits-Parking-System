package com.example.myparkingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

    public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {

        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
        private GoogleMap mMap;
        private FusedLocationProviderClient mFusedLocationProviderClient;
        private EditText editTextSource;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.maps);

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync((OnMapReadyCallback) this);
            }

            editTextSource = findViewById(R.id.source);
            EditText editTextDestination = findViewById(R.id.destination);
            Button b = findViewById(R.id.btnSubmit);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String source = editTextSource.getText().toString();
                    String destination = editTextDestination.getText().toString();
                    if (source.isEmpty() || destination.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter both source and destination", Toast.LENGTH_SHORT).show();
                    } else if (destination.equals("Mens res")) {
                        destination = processJSON(editTextDestination.getText().toString());


                    } else {
                        Uri uri = Uri.parse("https://www.google.com/maps/dir/" + source + "/" + destination);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setPackage("com.google.android.apps.maps");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            //LatLng Mens_res = new LatLng(-26.18906, 28.02999);

            //googleMap.addMarker(new MarkerOptions().position(Mens_res).title("Men's res"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(Mens_res));


            // Check for location permission
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // Request location updates
                mMap.setMyLocationEnabled(true);
                getDeviceLocation();
            } else {
                // Request location permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

        public String processJSON(Object json) {
            HashMap<String,String> output = new HashMap<>();
            try {
                JSONArray all = new JSONArray(json);
                for (int i = 0; i < all.length(); i++) {
                    JSONObject item = all.getJSONObject(i);
                    String name = item.getString("name");
                    String description = item.getString("code");
                    output.put(name, description);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return output.toString();

        }

        private void getDeviceLocation() {
            try {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(this, location -> {
                                if (location != null) {
                                    // Move camera to current location
                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                                    // Set the current location as the source input
                                    String currentLocation = location.getLatitude() + "," + location.getLongitude();
                                    editTextSource.setText(currentLocation);
                                }
                            });
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    onMapReady(mMap);
                } else {
                    // Permission denied
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

