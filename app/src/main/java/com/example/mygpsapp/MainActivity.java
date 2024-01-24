package com.example.mygpsapp;

import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends AppCompatActivity {

    private TextView txt_time, txt_Lat, txt_Lon, txt_Acc, txt_Alt, txt_Spd;
    private Button button;
    public static Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();
        getPermissions();
        button.setOnClickListener(view -> {
            Intent myIntent = new Intent(MainActivity.this, OsmActivity.class);
            myIntent.putExtra("Location", loc);
            startActivity(myIntent);
        });

        Thread object = new Thread(new MultithreadingDemo());
        object.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getPermissions();
            return;
        }
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build();
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    loc = location;
                    updateView(loc);

                    if (button.getVisibility() == View.GONE) {
                        button.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, Looper.getMainLooper());
    }

    private void updateView(Location location) {
        txt_time.setText(String.valueOf(location.getTime()));
        txt_time.setText(String.valueOf(new java.util.Date(location.getTime())));
        txt_Lat.setText(String.valueOf(location.getLatitude()));
        txt_Lon.setText(String.valueOf(location.getLongitude()));
        if (location.hasAccuracy())
            txt_Acc.setText(String.valueOf(location.getAccuracy()));
        if (location.hasAltitude())
            txt_Alt.setText(String.valueOf(location.getAltitude()));
        if (location.hasSpeed())
            txt_Spd.setText(String.valueOf(Math.round(location.getSpeed() * 3.6)));

    }

    private void initGui() {
        txt_time = findViewById(R.id.tv_timestamp);
        txt_Lat = findViewById(R.id.tv_latitude);
        txt_Lon = findViewById(R.id.tv_longitude);
        txt_Acc = findViewById(R.id.tv_accuracy);
        txt_Alt = findViewById(R.id.tv_altitude);
        txt_Spd = findViewById(R.id.tv_speed);
        button = findViewById(R.id.btn_update);
    }

    public void getPermissions() {
        ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if ((fineLocationGranted != null && fineLocationGranted) ||
                            (coarseLocationGranted != null && coarseLocationGranted)) {
                    } else {
                        // No location access granted.
                        Toast.makeText(this, "F... U. Det er altså en GPS app, dummy.",
                                Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                });
        locationPermissionRequest.launch(new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
    }
}

class MultithreadingDemo implements Runnable {
    public void run() {
        try {
            while (true) {
                //I am immortal!!!
                if (MainActivity.loc != null) Log.d("Main", String.valueOf(MainActivity.loc.getTime() ));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}