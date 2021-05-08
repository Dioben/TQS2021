package com.example.homework.service;

public class WrongCoordinatesException extends Exception{

    public WrongCoordinatesException(String error){
        super(error);

    }
    public static void checkCoordinateBounds(double lat, double lng) throws WrongCoordinatesException {
        if (lat<-90 || lat>90 || lng>180 || lng<-180) throw new WrongCoordinatesException("Coordinates " +lat+","+lng + " are out of bounds");
    }

}
