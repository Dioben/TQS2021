package com.example.homework.data;

import lombok.Data;

@Data
public class CachedWeatherData {
    private WeatherData data;
    private long timestamp;
    public CachedWeatherData(WeatherData data, long ts){this.data=data;timestamp=ts;}
}
