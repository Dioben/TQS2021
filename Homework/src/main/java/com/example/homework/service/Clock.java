package com.example.homework.service;

import org.springframework.stereotype.Service;

@Service
public class Clock {
    public long currentTimeMillis(){return System.currentTimeMillis();}
}
