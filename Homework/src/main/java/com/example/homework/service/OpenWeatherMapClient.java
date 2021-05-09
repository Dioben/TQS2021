package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OpenWeatherMapClient extends CoordRestClient{
    private static final String API_KEY = "5d85331459d67e53f53f17a67c0c64b1";

    @Override
    protected String generateURL(double lat, double lon) {
        return "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat + "&lon=" + lon +"&appid=" + API_KEY;
    }

    @Override
    protected WeatherData parseJson(JSONObject json,double lat,double lng) throws WrongCoordinatesException {
        String log = "Got "+json+" from OpenWeatherMap";
        logger.info(log);
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
