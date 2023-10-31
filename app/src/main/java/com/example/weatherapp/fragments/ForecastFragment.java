package com.example.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.models.ForecastDay;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.recyclerview.ForecastAdapter;

public class ForecastFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_forecast, container, false);

        Weather weather = (Weather)getArguments().getSerializable("weather");

        // Setup the Forecast RecyclerView
        if(weather != null) {
            ForecastDay[] forecastDays = weather.getForecast().getForecastDays();

            RecyclerView recyclerView = view.findViewById(R.id.recycleViewForcaset);
            ForecastAdapter forecastAdapter = new ForecastAdapter(forecastDays, view, getActivity().getApplicationContext());
            recyclerView.setAdapter(forecastAdapter);
        }

        return view;
    }
}