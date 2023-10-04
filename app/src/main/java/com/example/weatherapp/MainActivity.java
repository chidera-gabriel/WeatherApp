package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.fragments.CurrentFragment;
import com.example.weatherapp.fragments.ForecastFragment;
import com.example.weatherapp.models.Weather;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private CurrentFragment currentFragment;
    private ForecastFragment forecastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_main);

        String json = getJsonFromFile();

        // Use GSON to parse the json string
        Gson gson = new Gson();
        Weather weather = gson.fromJson(json, Weather.class);



        // Display the location
        String[] locationArray = getResources().getStringArray(R.array.provinces);
        HashMap<String, String> locationHash = getHashFromStringArray(locationArray);

        String region = weather.getLocation().getRegion();
        String abbrev = locationHash.get(region);

        //weather.getLocation().getRegion()
        TextView textViewLocation = binding.textViewLocation;
        String fullLocation = weather.getLocation().getName() + ", " + weather.getLocation().getRegion();
        textViewLocation.setText(fullLocation);

        //
        // Setup Fragment
        //

       NavigationBarView bottomNavigationView = findViewById(R.id.bottomNavigationView);
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if(itemId == R.id.navigation_current) {
                    getSupportFragmentManager()
                        .beginTransaction()
                        //replace(R.id.FrameLayout, currentFragment)
                        .replace(R.id.frameLayout, currentFragment)
                        .commit();
                    return true;
                }

                if(itemId == R.id.navigation_forecast) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            //replace(R.id.FrameLayout, currentFragment)
                            .replace(R.id.frameLayout, forecastFragment)
                            .commit();
                    return true;

                }
                return false;


            }
        });

        Bundle bundle = new Bundle();
        bundle.putSerializable("weather", weather);

        currentFragment = new CurrentFragment();
        currentFragment.setArguments(bundle);

        forecastFragment = new ForecastFragment();

        bottomNavigationView.setSelectedItemId(R.id.navigation_current);
    }

    // Convert province string array into map<k,v>.
    private HashMap<String, String> getHashFromStringArray(String[] array) {
        HashMap<String, String> result = new HashMap<>();
        for (String str : array) {
            // e.g. ON,Ontario
            String[] splitItem = str.split(",");
            result.put(splitItem[1], splitItem[0]);
        }
        return result;
    }

    // Get JSON string from .json file
    private String getJsonFromFile() {
        String json = "";

        InputStream inputStream = this.getResources().openRawResource(R.raw.weather_api);

        // Create InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(inputStream);

        // Create a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);

        // Read the buffer and save to string
        json = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        return json;
    }
}