package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class Car {
    @Id
    private
    Long carId;
    private String maker;
    private String model;
    public Car(){}
    public Car(String x,String y){
        setMaker(x);
        setModel(y);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(getCarId(), car.getCarId()) && Objects.equals(getMaker(), car.getMaker()) && Objects.equals(getModel(), car.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarId(), getMaker(), getModel());
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
