package com.example.interim;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    ImageView appLogo2;
    TextView locationTxt, locationTxt2;
    Button acceptBtn, refuseBtn;

    // Variables used for the permission (I imported 2 packages + the permission package)
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Declaring the views
        appLogo2 = findViewById(R.id.app_logo2);
        locationTxt = findViewById(R.id.location_txt);
        locationTxt2 = findViewById(R.id.location_txt2);
        acceptBtn = findViewById(R.id.accept_button);
        refuseBtn = findViewById(R.id.refuse_button);

        // Adding functionalities
        appLogo2.setImageResource(R.drawable.logo);

        // Location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        getAndShowCity(); // Permission granted, now fetch location
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // the accept button
        acceptBtn.setOnClickListener(v -> {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        });

        // The refuse button
        refuseBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Location access refused", Toast.LENGTH_SHORT).show();
        });
    }

    // the method that displays the city as a toast
    void getAndShowCity() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        String city = getCityFromLocation(this, location);
                        if (city != null) {
                            Toast.makeText(this, "City: " + city, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "City information not available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error getting location", Toast.LENGTH_SHORT).show();
                });
    }

    // a method that extracts the name of the city out of the GPS location
    private String getCityFromLocation(Context context, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getLocality(); // This is the city name
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
