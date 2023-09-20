package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.models.Weather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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

        // Display the temperature
        TextView textViewTemperature = binding.textViewTemperature;
        String temperature = String.valueOf(weather.getCurrent().getTemperature()) + "°C";
        textViewTemperature.setText(temperature);

        // Display the condition description
        TextView textViewDescription = binding.textViewDescription;
        textViewDescription.setText((weather.getCurrent().getCondition().getText()));

        // Display the weather icon
        ImageView imageView = binding.imageViewIcon;
        String imageUrl = "https://" + weather.getCurrent().getCondition().getIcon();
        imageUrl = imageUrl.replace("64x64", "128x128");
        Glide.with(view).load(imageUrl).into(imageView);

        // Display the location
        //String[] provinceCodes = getResources().getStringArray(R.array.canada);
        //List<String> listCodes = Arrays.asList(provinceCodes);
        //int index = listCodes.indexOf(weather.getLocation().getRegion());

        //String code = listCodes.get(index);

        //weather.getLocation().getRegion()
        TextView textViewLocation = binding.textViewLocation;
        String fullLocation = weather.getLocation().getName() + ", " + weather.getLocation().getRegion();
        textViewLocation.setText(fullLocation);

        // Displaying the wind direction and the wind speed
        TextView textViewWind = binding.textViewWind;
        String wind_dir = String.valueOf(weather.getCurrent().getWind_dir());
        String wind_kph = String.valueOf(weather.getCurrent().getWind_kph());
        String wind = "Wind: " + wind_dir + " " + wind_kph + " kph";
        textViewWind.setText(wind);

        //Displaying the Feels Like

        TextView textViewFeelsLike = binding.textViewFeelsLike;
        String feelsLike = "Feel like: " + String.valueOf(weather.getCurrent().getFeelsLike() + "°C");
        textViewFeelsLike.setText(feelsLike);
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