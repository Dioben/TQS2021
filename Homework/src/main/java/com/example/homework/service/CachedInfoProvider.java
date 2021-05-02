package com.example.homework.service;

import com.example.homework.data.WeatherData;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.awt.geom.Point2D;
import java.util.HashMap;

@Service
public class CachedInfoProvider {
    @Autowired
    OpenWeatherMapClient mainClient;
    @Autowired
    WeatherBitClient secondaryClient;
    @Autowired
    Logger logger;
    HashMap<Point2D,Long> lastQueried;
    HashMap<Point2D, WeatherData> cache;
    private static final long EXPIRE_LIMIT = 30000;//30 seconds

    public CachedInfoProvider(){lastQueried= new HashMap<>(); cache = new HashMap<>();}
    public WeatherData getData(double lat, double lon){
        Point2D query = new Point2D.Double(lat,lon);
        long now = System.currentTimeMillis();
        if (!(lastQueried.containsKey(query) && lastQueried.get(query)+EXPIRE_LIMIT> now)){
            refresh(lat, lon, query);
        }
        return cache.get(query);
    }

    private void refresh(double lat, double lon, Point2D query) {
        try{
            cache.put(query,mainClient.getData(lat, lon));
        }catch (Exception e){
            try{
                cache.put(query,secondaryClient.getData(lat, lon));
            }catch (Exception x){logger.error("Both APIs have failed");}
        }
    }

}
