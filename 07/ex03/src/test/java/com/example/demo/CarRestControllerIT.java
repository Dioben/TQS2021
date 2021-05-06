package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.hamcrest.CoreMatchers.is;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarRestControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private CarRepository repository;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres")
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @AfterEach
    public void resetDb() {
        //repository.deleteAll();
    }

    @SneakyThrows
    @Test
    void whenValidInput_thenCreateCar() {
        Car c1 = new Car("maker", "model");

        String url = "http://127.0.0.1:" + randomServerPort + "/api/cars";

        RestAssured.given()
                .body(JsonUtil.toJson(c1)).contentType(ContentType.JSON)
                .when().post(url)
                .then().assertThat()
                .statusCode(201)
                .and().body("maker", is("maker"));

        RestAssured.given()
                .when().get(url+"/1")
                .then().assertThat()
                .statusCode(200)
                .and().body("maker", is("maker"));
    }

    @Test
    public void givenCars_whenGetCars_thenStatus200()  {
        Car c1 = new Car("maker1", "model1");
        Car c2 = new Car("maker2", "model2");
        repository.save(c1);
        repository.save(c2);
        repository.flush();

        String url = "http://127.0.0.1:" + randomServerPort + "/api/cars";

        RestAssured.given()
                .when().get(url)
                .then().assertThat()
                .statusCode(200)
                .and().body("[0].maker", is("maker1"))
                .and().body("[1].maker", is("maker2"));
    }

}