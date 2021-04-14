package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ApiVerificationIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;


    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }
    
    @Test
    public void whenValidInput_thenCreateCar() throws Exception {
        Car car = new Car("car", "this is a car");
        car.setCarId(100l);
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", car, Car.class);



        repository.flush();
        List<Car> found = repository.findAll();
        Assertions.assertThat(found).extracting(Car::getMaker).containsOnly("car");
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()  {
        Car car = new Car("car", "this is a car");
        Car car2 = new Car("car2", "this is also a car");
        car.setCarId(1l);
        car2.setCarId(2l);
        repository.saveAndFlush(car);
        repository.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("car", "car2");

    }




}
