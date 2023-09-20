package com.example.weatherapp.models;

public class Condition {
    private String text;
    private String icon;

    private Condition(String text, String icon, String wind_dir, String wind_kph) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() { return text; }
    public String getIcon() { return icon; }
}
