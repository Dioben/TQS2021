package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {
    @Autowired
    CarRepository carRepository;

    public Car save(Car x){carRepository.save(x);
        return x;
    }
    public List<Car> getAllCars(){return carRepository.findAll();}
    public Optional<Car> getCarDetails(Long id){
        return Optional.ofNullable(carRepository.findByCarId(id));
        }
}
