package com.example.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CachedInfoProvider {
    @Autowired
    OpenWeatherMapClient mainClient;
    @Autowired
    WeatherBitClient secondaryClient;
}
