package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherBitClient {
    private static final String API_KEY = "d883c6de35eb46fd87316a36fba54376";
    @Autowired
    RestTemplate fetcher;

    WeatherData getData(double lat, double lng){
        String url = "https://api.weatherbit.io/v2.0/current/airquality?lat=" + lat + "&lon=" + lng +"&key=" + API_KEY;
        return  fetcher.getForObject(url,WeatherData.class);
    }

}
