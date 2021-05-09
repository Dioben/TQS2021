package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class WeatherBitClient extends  CoordRestClient{
    private static final String API_KEY = "d883c6de35eb46fd87316a36fba54376";

    @Override
    protected String generateURL(double lat, double lon) {
        return "https://api.weatherbit.io/v2.0/current/airquality?lat=" + lat + "&lon=" + lon +"&key=" + API_KEY;
    }

    @Override
    protected WeatherData parseJson(JSONObject json, double lat, double lng) throws WrongCoordinatesException {
        String log = "Got "+json+" from WeatherBit";
        logger.info(log);

        double querylat = json.getDouble("lat");
        double querylon = json.getDouble("lon");

        if (Math.abs(querylon-lng)>0.1 || Math.abs(querylat-lat)>0.1) throw new WrongCoordinatesException("Queried coordinates do not match");

        WeatherData data = new WeatherData();
        data.setLat(querylat);
        data.setLon(querylon);
        JSONObject apiData = json.getJSONArray("data").getJSONObject(0);
        data.setAqi(apiData.getDouble("aqi"));
        data.setCo(apiData.getDouble("co"));
        data.setO3(apiData.getDouble("o3"));
        data.setPm10(apiData.getDouble("pm10"));
        data.setPm25(apiData.getDouble("pm25"));
        return  data;
    }

}
