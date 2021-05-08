package com.example.homework.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class WrongCoordinatesException extends Exception{
    @Autowired
    transient Logger logger;
    public WrongCoordinatesException(String error){
        super(error);
        logger.error(error);
    }

}
