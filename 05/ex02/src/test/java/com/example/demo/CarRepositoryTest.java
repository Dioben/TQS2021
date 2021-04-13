package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;


@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CarRepository carRepository;

    @Test
    public void whenFindCarById_thenReturnCar() {
        Car car1 = new Car("sample text 1", "sampletext 2");
        car1.setCarId(1l);
        testEntityManager.persistAndFlush(car1); //ensure data is persisted at this point

        Car found = carRepository.findByCarId(1l);
        Assertions.assertThat( found ).isEqualTo(car1);
    }

    @Test
    public void whenInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-1l);
        Assertions.assertThat(fromDb).isNull();
    }



    @Test
    public void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car c1 = new Car("c1", "c1-2");
        Car c2 = new Car("c2", "c2-2");
        Car c3 = new Car("c3", "c3-2");

        c1.setCarId(1l);
        c2.setCarId(2l);
        c3.setCarId(3l);

        testEntityManager.persist(c1);
        testEntityManager.persist(c2);
        testEntityManager.persist(c3);
        testEntityManager.flush();

        List<Car> allCars = carRepository.findAll();

        Assertions.assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(c1.getMaker(), c2.getMaker(), c3.getMaker());
    }

}
