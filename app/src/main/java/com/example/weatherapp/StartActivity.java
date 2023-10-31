package com.example.weatherapp;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class StartActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final static int REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Verify the permissions granted
        if(requestCode == REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    public void getLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission Granted - Get location from device

            fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }
                });
        }
        else {
            // Permission Denied - Ask the user for permission
            askPermission();
        }
    }

    private void askPermission() {
        // Display screen to request permission
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE);
    }
}
