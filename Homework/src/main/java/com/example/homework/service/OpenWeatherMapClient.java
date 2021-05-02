package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherMapClient {
    private static final String API_KEY = "5d85331459d67e53f53f17a67c0c64b1";
    @Autowired
    RestTemplate fetcher;
    @Autowired
    Logger logger;
    WeatherData getData(double lat, double lng) throws WrongCoordinatesException {
        String url = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat + "&lon=" + lng +"&appid=" + API_KEY;
        HttpEntity<String> entity = fetcher.getForEntity(url,String.class);
        String content = entity.getBody();
        String log = "Got "+content+" from OpenWeatherMap";
        logger.info(log);

        JSONObject json = new JSONObject(content);
        JSONObject coords = json.getJSONObject("coord");
        double querylat = coords.getDouble("lat");
        double querylon = coords.getDouble("lon");

        if (Math.abs(querylon-lng)>0.1 || Math.abs(querylat-lat)>0.1) throw new WrongCoordinatesException("Queried coordinates do not match");

        WeatherData data = new WeatherData();
        data.setLat(querylat);
        data.setLon(querylon);

        JSONObject apiData = json.getJSONArray("list").getJSONObject(0);
        data.setAqi(apiData.getJSONObject("main").getDouble("aqi"));
        apiData = apiData.getJSONObject("components");
        data.setCo(apiData.getDouble("co"));
        data.setO3(apiData.getDouble("o3"));
        data.setPm10(apiData.getDouble("pm10"));
        data.setPm25(apiData.getDouble("pm2_5"));
        return  data;
    }
}
