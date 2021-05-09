package com.example.homework.service;

import com.example.homework.data.CachedWeatherData;
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
    @Autowired
    Clock clock;
    HashMap<Point2D, CachedWeatherData> cache;
    private static final long EXPIRE_LIMIT = 30000;//30 seconds

    private  long mainApiRequests=0;
    private  long mainApiRequestsSuccess=0;
    private  long backupApiRequests=0;
    private  long backupApiRequestsSuccess=0;
    private  long cacheUsages=0;

    public CachedInfoProvider(){ cache = new HashMap<>();}

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
        if (!cache.containsKey(query) || isExpired(query) ){
            refresh(lat, lon, query);
        }
        else{logger.info("Did not require refresh");cacheUsages++;}
        if (!cache.containsKey(query)){return null;}
        return cache.get(query).getData();
    }

    private void refresh(double lat, double lon, Point2D query) {
        logger.info("Refreshing...");
        try{
            mainApiRequests++;
            cache.put(query,new CachedWeatherData(mainClient.getData(lat, lon),clock.currentTimeMillis()));
            mainApiRequestsSuccess++;
        }catch (Exception e){
            try{
                backupApiRequests++;
                cache.put(query,new CachedWeatherData(secondaryClient.getData(lat, lon),clock.currentTimeMillis()));
                backupApiRequestsSuccess++;
            }catch (Exception x){logger.error("Both APIs have failed");
                                if (cache.containsKey(query)){cacheUsages++;logger.info("Using outdated value");}
            }
        }
    }

    public  String mainApiStats(){return mainApiRequests +" calls to the main API , "+ mainApiRequestsSuccess +" successful calls";}
    public  String backupApiStats(){return backupApiRequests +" calls to the backup API , "+ backupApiRequestsSuccess +" successful calls";}
    public  String apiStats(){return mainApiStats()+"\n"+backupApiStats();}
    public  String cacheStats(){return "Cache has been used in "+cacheUsages+" out of "
            +(cacheUsages+mainApiRequestsSuccess+backupApiRequestsSuccess)+ " calls";}
    public  String fullStats(){return  cacheStats()+"\n"+apiStats();}

    public String statsAsJson(){return "{\"cacheCalls\":"+cacheUsages+
            ", \"totalCalls\":"+(cacheUsages+mainApiRequestsSuccess+backupApiRequestsSuccess)
            +", \"mainApiCalls\":"+mainApiRequests
            +", \"mainApiCallsSuccess\":"+mainApiRequestsSuccess
            +", \"secondaryApiCalls\":"+backupApiRequests
            +", \"secondaryApiCallsSuccess\":"+backupApiRequestsSuccess+
            "}";}

    public void clear(){cache = new HashMap<>();logger.info("Cleared cache"); }
    private boolean isExpired(Point2D query){
        return cache.get(query).getTimestamp()+EXPIRE_LIMIT< clock.currentTimeMillis();
    }
}
