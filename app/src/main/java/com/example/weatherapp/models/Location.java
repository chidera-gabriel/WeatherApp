package com.example.weatherapp.models;

public class Location {
    private String name;
    private String region;
    private String country;

    public Location(String name, String region, String country) {
        this.name = name;
        this.region = region;
        this.country = country;
    }

    public String getName() { return getName(); }
    public String getRegion() { return getRegion(); }
}
