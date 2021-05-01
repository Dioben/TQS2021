package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock(lenient=true)
    CarRepository carRepository;
    @InjectMocks
    CarManagerService carManagerService;

    @Test
    void saveCarTest(){
        Car car1 = new Car("sample text 1", "sample text 2");
        car1.setCarId(10l);
        when(carRepository.findByCarId(Mockito.any())).thenReturn(car1);
        carManagerService.save(car1);
        assertThat(carManagerService.getCarDetails(10l).get(),equalTo(car1));
    }

    @Test
    void getCarNXTest(){
        when(carRepository.findByCarId(Mockito.any())).thenReturn(null);
        assertThat(carManagerService.getCarDetails(1l).isEmpty(),equalTo(true));
    }
}
