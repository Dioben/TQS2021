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

    private  long mainApiRequests=0;
    private  long mainApiRequestsSuccess=0;
    private  long backupApiRequests=0;
    private  long backupApiRequestsSuccess=0;
    private  long cacheUsages=0;

    public CachedInfoProvider(){lastQueried= new HashMap<>(); cache = new HashMap<>();}

    public  long getMainApiRequests() {
        return mainApiRequests;
    }

    public  long getMainApiRequestsSuccess() {
        return mainApiRequestsSuccess;
    }

    public  long getBackupApiRequests() {
        return backupApiRequests;
    }

    public  long getBackupApiRequestsSuccess() {
        return backupApiRequestsSuccess;
    }

    public  long getCacheUsages() {
        return cacheUsages;
    }

    public WeatherData getData(double lat, double lon){
        Point2D query = new Point2D.Double(lat,lon);
        long now = System.currentTimeMillis();
        if (!lastQueried.containsKey(query) || lastQueried.get(query)+EXPIRE_LIMIT< now){
            refresh(lat, lon, query);
        }
        else{logger.info("Did not require refresh");cacheUsages++;}
        return cache.get(query);
    }

    private void refresh(double lat, double lon, Point2D query) {
        logger.info("Refreshing...");
        try{
            mainApiRequests++;
            cache.put(query,mainClient.getData(lat, lon));
            lastQueried.put(query,System.currentTimeMillis());
            mainApiRequestsSuccess++;
        }catch (Exception e){
            try{
                backupApiRequests++;
                cache.put(query,secondaryClient.getData(lat, lon));
                lastQueried.put(query,System.currentTimeMillis());
                backupApiRequestsSuccess++;
            }catch (Exception x){logger.error("Both APIs have failed");}
        }
    }

    public  String mainApiStats(){return mainApiRequests +" calls to the main API , "+ mainApiRequestsSuccess +" sucessful calls";}
    public  String backupApiStats(){return backupApiRequests +" calls to the backup API , "+ backupApiRequestsSuccess +" sucessful calls";}
    public  String apiStats(){return mainApiStats()+"\n"+backupApiStats();}
    public  String cacheStats(){return "Cache has been used in "+cacheUsages+" out of "
            +(cacheUsages+mainApiRequestsSuccess+backupApiRequestsSuccess)+ " calls";}
    public  String fullStats(){return  cacheStats()+"\n"+apiStats();}

    public void clear(){lastQueried= new HashMap<>(); cache = new HashMap<>();logger.info("Cleared cache");

    }

}
