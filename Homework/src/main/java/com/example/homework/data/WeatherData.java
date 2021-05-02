package com.example.homework.data;

import lombok.Data;

@Data
public class WeatherData {
    double lat;
    double lon;
    double aqi;
    double co;
    double pm10;
    double pm25;
    double o3;
    public WeatherData(){
        //for json conversion purposes, this is so sonar won't complain
    }
}
