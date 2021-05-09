package com.example.homework;

import com.example.homework.config.BeanConfigs;
import com.example.homework.controller.Controller;
import com.example.homework.data.WeatherData;
import com.example.homework.service.CachedInfoProvider;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.*;

@WebMvcTest(Controller.class)
class ApiTest {

    @TestConfiguration
    static class AdditionalConfig {
        @Bean
        public Logger logger() {
            return LoggerFactory.getLogger(HomeworkApplication.class);
        }

        @Bean
        public CachedInfoProvider cachedInfoProvider(){return new CachedInfoProvider();}
    }

    @Autowired
    MockMvc mvc;
    @MockBean
    Logger logger;
    @MockBean
    CachedInfoProvider cachedInfoProvider;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void noDataTest(){
        Mockito.when(cachedInfoProvider.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(null);
        when().get("/airQuality?lat=0&lon=0").then().assertThat().statusCode(404);
    }

    @Test
    void unboundQueryTest(){
        Mockito.when(cachedInfoProvider.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(null);
        when().get("/airQuality?lat=200&lon=0").then().assertThat().statusCode(404);
    }

    @Test
    void normalQueryTest(){
        WeatherData data = new WeatherData();
        data.setAqi(10);
        data.setCo(25);
        data.setPm25(2.3);
        data.setLon(12);
        Mockito.when(cachedInfoProvider.getData(Mockito.anyDouble(),Mockito.anyDouble())).thenReturn(data);
        when().get("/airQuality?lat=0&lon=12").then().assertThat().statusCode(200)
        .and().body("lat",is(0.0f))
        .and().body("lon",is(12.0f))
        .and().body("aqi",is(10.0f))
                .and().body("co",is(25.0f))
                .and().body("pm25",is(2.3f));
    }
    @Test
    void cacheStatFormatting(){
        String expected = "5000 calls to the main API ,0 successful calls\n5000 calls to the backup API , 0 successful calls\nCache has been used in 5000 out of 5000 calls";
        Mockito.when(cachedInfoProvider.fullStats()).thenReturn(expected);
        when().get("/cacheReport").then().assertThat().statusCode(200)
                .and().body(equalTo(expected));
    }

    @Test
    void cacheStatJson(){
        String expected = "{}";
        Mockito.when(cachedInfoProvider.statsAsJson()).thenReturn(expected);
        when().get("/cache").then().assertThat().statusCode(200)
                .and().body(equalTo(expected));
    }

}
