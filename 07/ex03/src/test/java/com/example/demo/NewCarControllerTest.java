package com.example.demo;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest
public class NewCarControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CarManagerService service;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenPostCar_thenReturnCar() throws Exception {
        Car c1 = new Car("maker", "model"); c1.setCarId(2L);

        when(service.save(Mockito.any())).thenReturn(c1);

        RestAssuredMockMvc.given()
                .auth().none()
                .body(JsonUtil.toJson(c1)).contentType(ContentType.JSON)
                .when().post("/api/cars")
                .then().assertThat()
                .statusCode(201)
                .and().body("maker", is("maker"));

        /*mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(c1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("maker")));*/

        verify(service, times(1)).save(c1);
    }

    @Test
    void whenGetCarByValidId_thenReturnCar() throws Exception {
        Car c1 = new Car("maker", "model"); c1.setCarId(2L);

        when(service.getCarDetails(c1.getCarId())).thenReturn(Optional.of(c1));

        RestAssuredMockMvc.given()
                .auth().none()
                .when().get("/api/cars/2").then()
                .assertThat()
                .statusCode(200)
                .and().body("maker", is("maker"))
                .and().body("model", is("model"));

        /*mvc.perform(get("/api/cars/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("maker").value("maker"))
                .andExpect(jsonPath("model").value("model"));*/

        verify(service, times(1)).getCarDetails(c1.getCarId());
    }

    @Test
    void whenGetAllCars_thenReturnCarList() throws Exception {
        Car c1 = new Car("maker", "model"); c1.setCarId(2L);
        Car c2 = new Car("maker2", "model2"); c1.setCarId(3L);
        List<Car> allCars = Arrays.asList(c1, c2);

        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.given()
                .auth().none()
                .when().get("/api/cars")
                .then()
                .statusCode(200)
                .and().body("$", hasSize(2))
                .and().body("[0].maker", is(c1.getMaker()))
                .and().body("[1].maker", is(c2.getMaker()));

        /*mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker", is(c1.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(c2.getMaker())));*/

        verify(service, times(1)).getAllCars();
    }

    @Test
    void whenGetCarByInvalidId_thenReturnNothing() {
        Car c1 = new Car("maker", "model"); c1.setCarId(2L);

        when(service.getCarDetails(c1.getCarId())).thenReturn(Optional.of(c1));

        try {
            RestAssuredMockMvc.given()
                    .auth().none()
                    .when().get("/api/cars/1")
                    .then().assertThat()
                    .statusCode(200);

        } catch (Exception e) {
            return;
        }
        fail();
    }
}