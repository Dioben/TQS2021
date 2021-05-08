package com.example.homework.controller;

import com.example.homework.data.WeatherData;
import com.example.homework.service.CachedInfoProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    Logger logger;
    @Autowired
    CachedInfoProvider infoProvider;


    @ResponseBody
    @GetMapping("/airQuality")
    public WeatherData airStats(@RequestParam(value = "lat") Double latitude, @RequestParam(value = "lon") Double longitude){
        String log = "we've been hit for " + latitude +","+longitude;
        logger.info(log);
        return infoProvider.getData(latitude,longitude);
    }
    @ResponseBody
    @GetMapping("/cache")
    public String cacheInfo(){
        logger.info("Cache stats have been requested");
        return infoProvider.fullStats();
    }
}
