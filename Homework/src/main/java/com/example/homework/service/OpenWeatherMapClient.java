package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherMapClient {
    private static final String API_KEY = "5d85331459d67e53f53f17a67c0c64b1";
    @Autowired
    RestTemplate fetcher;

    WeatherData getData(double lat, double lng){
        String url = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat + "&lon=" + lng +"&appid=" + API_KEY;
        return  fetcher.getForObject(url,WeatherData.class);
    }
}
