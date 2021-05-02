package com.example.homework.controller;

import com.example.homework.data.WeatherData;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    Logger logger;

    @GetMapping("")
    String index(){ return "redirect:/index.html";}


    @ResponseBody
    @GetMapping("/airQuality")
    WeatherData airStats(@RequestParam(value = "lat") Double latitude, @RequestParam(value = "lon") Double longitude){
        logger.info("we've been hit");
        return new WeatherData();
    }
}
