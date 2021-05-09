package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static com.example.homework.service.WrongCoordinatesException.checkCoordinateBounds;

public abstract class CoordRestClient {

    @Autowired
    RestTemplate fetcher;
    @Autowired
    Logger logger;

    public WeatherData getData(double lat, double lng) throws WrongCoordinatesException, JSONException {
        checkCoordinateBounds(lat,lng);
        String url = generateURL(lat,lng);

        HttpEntity<String> entity = fetcher.getForEntity(url,String.class);
        String content = entity.getBody();
        JSONObject json = new JSONObject(content);
        return parseJson(json,lat,lng);
    }
    protected abstract String generateURL(double lat,double lon);
    protected abstract WeatherData parseJson(JSONObject json,double lat,double lng) throws WrongCoordinatesException;
}
