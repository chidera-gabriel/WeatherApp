package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.fragments.CurrentFragment;
import com.example.weatherapp.fragments.ForecastFragment;
import com.example.weatherapp.models.Location;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.retrofit.RetrofitClient;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    View view;

    private CurrentFragment currentFragment;
    private ForecastFragment forecastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavigationBarView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //
        // Retrofit client to get weather
        //
        String currentLocation = "44.6671142,-63.6075769";

        // Create and initialize the Api client
        Call<Weather> call = RetrofitClient.getInstance().getApi().getWeather(
                "f3f564190b7a4f268d3174022231110",
                currentLocation,
                "3",
                "no",
                "no");

        // Make the Api call
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();

                //
                // Setup Fragments
                //

                if(weather != null) {
                    
                    Log.i("TESTING", "Date: " + weather.getForecast().getForecastDays()[0].getDate());
                    Log.i("TESTING", "Max Temp: " + weather.getForecast().getForecastDays()[0].getDay().getMaxTemp());
                    Log.i("TESTING", "Hour epoch: " + weather.getForecast().getForecastDays()[0].getHours()[0].getEpoch());

                    // Update the Location in Activity layout
                    DisplayLocation(weather.getLocation());

                    // Add weather object to Bundle
                    Bundle bundle= new Bundle();
                    bundle.putSerializable("weather", weather);

                    // Create fragment and pass bundle as an argument
                    currentFragment = new CurrentFragment();
                    currentFragment.setArguments(bundle);

                    forecastFragment = new ForecastFragment();
                    forecastFragment.setArguments(bundle);

                    bottomNavigationView.setSelectedItemId(R.id.navigation_current);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.i("TESTING", "Error:" + t.toString());
            }
        });

        /*String json = getJsonFromFile();
        // Use GSON to parse the json string
        Gson gson = new Gson();
        Weather weather = gson.fromJson(json, Weather.class);*/

        //
        // Setup Bottom Navigation View
        //

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
    }

    private void DisplayLocation(Location location) {
        // Display the location
        String[] locationArray = getResources().getStringArray(R.array.provinces);
        HashMap<String, String> locationHash = getHashFromStringArray(locationArray);
        String region = location.getRegion();
        String abbrev = locationHash.get(region);

        //weather.getLocation().getRegion()
        TextView textViewLocation = binding.textViewLocation;
        String fullLocation = location.getName() + ", " + abbrev;
        textViewLocation.setText(fullLocation);
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