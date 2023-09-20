package com.example.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("temp_c")
    private float temperature;
    private Condition condition;
    @SerializedName("wind_kph")
    private float wind_kph;
    private String wind_dir;
    @SerializedName("feelslike_c")
    private float feelsLike;

    public Current(float temperature, Condition condition, float wind_kph, String wind_dir, float feelsLike) {
        this.temperature = temperature;
        this.condition = condition;
        this.wind_kph = wind_kph;
        this.wind_dir = wind_dir;
        this.feelsLike = feelsLike;
    }

    public  float getTemperature() { return temperature; }
    public Condition getCondition() { return condition; }
    public float getWind_kph() { return wind_kph; }
    public String getWind_dir() { return wind_dir; }
    public float getFeelsLike() { return feelsLike; }
}
